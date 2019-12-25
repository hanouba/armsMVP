package com.hansen.fullvideo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.TemplateBean;
import com.hansen.fullvideo.dao.DBHelper;
import com.hansen.fullvideo.ui.BigScreenControlView;
import com.hansen.fullvideo.ui.CommonDialog;
import com.hansen.fullvideo.utils.LogUtils;
import com.hansen.fullvideo.utils.Utils;
import com.hansen.socket.SocketActivity;
import com.hansen.socket.TcpClient;

import java.lang.ref.WeakReference;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GreenActivity extends AppCompatActivity implements View.OnClickListener {
    protected int aveWidth;//课程格子平均宽度
    protected int screenWidth;//屏幕宽度
    protected int gridHeight = 80;//格子高度

    // 行数数量
    private final int rowNum = 6;
    //列数
    private final int columnNum = 10;
    //空格显示区域
    private RelativeLayout rlContent;
    //空的用于定位
    private TextView tvEmpty;
    //模板列表
    private ListView lvTemplate;
    //
    private LinearLayout llContent;
    private LinearLayout llBigScreen;
    private RelativeLayout rlContentTitle;
    private Button btEdit, btClean;

    //课程信息，key为星期几，value是这一天的课程信息
    //key 是第几列，value是这一列的信息
    private Map<String, List<BigScreenBean>> courseInfoMap;

    private Map<Integer, List<BigScreenBean>> textviewCourseInfoMap;//保存每个textview对应的课程信息 map,key为哪一天（如星期一则key为1）
    /**
     * 数据存储变量 一个预案信息
     */

    private List<BigScreenBean> bigScreenBeans;

    private DBHelper mDBHelper;

    private int hasShow = 0;
    //空白小块被选择的次数
    private int clickTimes = 0;
    //编辑状态
    private int editClick = 0;


    private List<Integer> typeLits = new ArrayList<>();
    private BigScreenControlView bscView;
    private int contentLeft;
    private int contentRight;
    private int contentTop;
    private int startRow;
    private int startColumn;
    private int endRow;
    private int endColumn;
    private boolean isEditAble;

    //默认预案编号 1
    private int currentTemp = 0;
    private String currentTempName = "";
    private TemplateAdapter templateAdapter;
    private CommonDialog mDialog;
    private Button btCommit;
    private static TcpClient tcpClient = null;
    ExecutorService exec = Executors.newCachedThreadPool();

    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private List<TemplateBean> allTemp;//数据库中的预案名称
    private List<String> tempNames; //存储获取到预案名称

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i("initTable", "onWindowFocusChanged" + hasFocus);
        hasShow++;
        if (hasShow == 1) {
            if (hasFocus) {

                initTable();

                updataTelep();
                updateEditState(false);
            }
        } else {

        }
    }

    /**
     * 更新界面
     */
    private void updataTelep() {
        bigScreenBeans.clear();
        bigScreenBeans = mDBHelper.searchAllByTempIndex(currentTemp);
        initTable();
        initCourse();
        //显示课表内容
        initCourseTableBody();
        typeLits.clear();
        showEditIcon();


    }

    /**
     * 更新编辑状态
     *
     * @param b
     */
    private void updateEditState(boolean b) {
        isEditAble = b;
        if (isEditAble) {
            btEdit.setText("保存");
        } else {
            btEdit.setText("编辑");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);
        mDBHelper = DBHelper.getInstance(GreenActivity.this);
        initView();
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver, intentFilter);
        initListener();
        initTCP();
        initData();
        mDialog = new CommonDialog(GreenActivity.this);
    }

    /**
     * 链接tcp服务端
     */
    private void initTCP() {
        tcpClient = new TcpClient(this);
        exec.execute(tcpClient);


    }

    private void initView() {
        rlContent = findViewById(R.id.rl_video_content);
        llBigScreen = findViewById(R.id.ll_big_screen);
        tvEmpty = findViewById(R.id.tv_empty);
        lvTemplate = findViewById(R.id.lv_template);
        llContent = findViewById(R.id.ll_content);
        rlContentTitle = findViewById(R.id.rl_content_title);
        btEdit = findViewById(R.id.bt_edit);
        btClean = findViewById(R.id.bt_clean);
        bscView = findViewById(R.id.bscv_view);
        btCommit = findViewById(R.id.bt_commit);
    }

    private void initListener() {
        btEdit.setOnClickListener(this);
        btClean.setOnClickListener(this);
        btCommit.setOnClickListener(this);
    }

    private void initData() {
        Log.i("initTable", "initData");
        textviewCourseInfoMap = new HashMap<Integer, List<BigScreenBean>>();

        tempNames = new ArrayList<>();
        allTemp = mDBHelper.searchAllTemp();
        for (int i = 0; i < allTemp.size(); i++) {
            String tempName = allTemp.get(i).getTempName();
            tempNames.add(tempName);
        }


        currentTempName = tempNames.get(0);
        templateAdapter = new TemplateAdapter(this,R.layout.item_template, tempNames);

        lvTemplate.setAdapter(templateAdapter);
        templateAdapter.setSelected(0);
        bigScreenBeans = mDBHelper.searchAllByTempIndex(currentTemp);


        lvTemplate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当前选择的预案编号
                currentTemp = position;
                //清空预案
                bigScreenBeans.clear();
                //查询当前元内的数据
                bigScreenBeans = mDBHelper.searchAllByTempIndex(currentTemp);
                //得到当前选择的预案
                currentTempName = allTemp.get(position).getTempName();
                updataTelep();
                updateEditState(false);
                templateAdapter.setSelected(position);

            }
        });

        lvTemplate.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                mDialog.setMessage("")
                        .setEditorText(true)
                        .setTitle("编辑预案名称")
                        .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick(String name) {
                        mDialog.dismiss();

                        mDBHelper.setTempName(position,name);
                        allTemp.clear();
                        allTemp = mDBHelper.searchAllTemp();
                        tempNames.clear();
                        for (int i = 0; i < allTemp.size(); i++) {
                            String tempName = allTemp.get(i).getTempName();
                            tempNames.add(tempName);
                        }
                        templateAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNegtiveClick() {
                        mDialog.dismiss();

                    }
                }).show();
                return false;
            }


        });
    }

    public List repeatListWayThird(List<String> list) {

        TreeSet set = new TreeSet(list);
        list.clear();

        list.addAll(set);
        return list;
    }

    /**
     * 划分表格
     */
    @SuppressLint("ResourceAsColor")
    private void initTable() {
        rlContent.removeAllViews();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        contentLeft = rlContent.getLeft() + llBigScreen.getLeft() + lvTemplate.getLeft() + llContent.getLeft();
        contentRight = lvTemplate.getRight() + llBigScreen.getRight();

        contentTop = rlContent.getTop() + rlContentTitle.getTop() + llBigScreen.getTop();
        int bottom = rlContent.getTop() + rlContentTitle.getTop() + llBigScreen.getBottom();


        //屏幕宽度
        int width = contentRight - contentLeft;


        //平均宽度
        int aveWidth = width / columnNum;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = bottom - contentTop;
        gridHeight = height / rowNum;


        //循环遍历没行
        for (int i = 1; i <= rowNum; i++) {
            // 遍历每列
            for (int j = 1; j <= columnNum; j++) {
                BorderTextView tx = new BorderTextView(this);
                tx.setId((i - 1) * columnNum + j);
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 32 / 32,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                tx.setBackgroundColor(R.color.color_f1f3f5);
                //字体样式
                tx.setTextAppearance(this, R.style.courseTableText);
                //如果是第一列
                if (j == 1) {
                    //设置他们的相对位置
                    if (i == 1)
                        //如果实在第一行 位置位于 空textview下方
                        rp.addRule(RelativeLayout.BELOW, tvEmpty.getId());
                    else
                        //
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * columnNum);
                } else {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * columnNum + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * columnNum + j - 1);
                    tx.setText("");
                }

                tx.setLayoutParams(rp);

                final int finalI = i;
                final int finalJ = j;
                //                选择控件区域
                tx.setOnTouchListener(new View.OnTouchListener() {


                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (isEditAble) {
                                    clickTimes++;
                                    if (clickTimes == 1) {
                                        startRow = finalI;
                                        startColumn = finalJ - 1;
                                    } else {

                                        //先判断这个位置是否有覆盖
                                        //根据所在列查出这一列的数据
                                        endRow = finalI;
                                        endColumn = finalJ - 1;
                                        clickTimes = 0;
                                        if ((startRow == endRow) || (startColumn == endColumn)) {
                                            LogUtils.d("同一行或者同一列不实现");
                                        } else {
                                            if (endColumn > startColumn) {

                                                checkSelectLocation(startColumn, endColumn, startRow, endRow);
                                            } else {
                                                int reprStartColumn = endColumn;
                                                int reprEndColumn = startColumn;
                                                int reprEndRow = startRow;
                                                int reprStartRow = endRow;
                                                //调换起点终点
                                                checkSelectLocation(reprStartColumn, reprEndColumn, reprStartRow, reprEndRow);
                                            }

                                        }


                                    }
                                } else {
                                    LogUtils.d("initTable", "不可编辑");

                                    mDialog.setMessage("请先点击编辑")
                                            .setTitle("提示")
                                            .setEditorText(false)
                                            .setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                                        @Override
                                        public void onPositiveClick(String name) {
                                            mDialog.dismiss();
                                        }

                                        @Override
                                        public void onNegtiveClick() {
                                            mDialog.dismiss();

                                        }
                                    }).show();

                                }
                                break;

                            case MotionEvent.ACTION_UP:

                                break;
                            default:
                        }
                        return true;
                    }
                });
                rlContent.addView(tx);
            }
        }


    }

    private void checkSelectLocation(int startCol, int endCol, int startR, int endR) {
        List<BigScreenBean> endBeans = mDBHelper.searchByColumm(endCol, currentTemp);
        List<BigScreenBean> startBeans = mDBHelper.searchByColumm(startCol, currentTemp);
        if (endBeans.size() > 0) {
            //判断终点有数据
            for (int k = 0; k < endBeans.size(); k++) {
                BigScreenBean currentColumnData = endBeans.get(k);
                if (currentColumnData.getLessonfrom() > endR && currentColumnData.getLessonfrom() <= startR) {
                    //终点下面有数据  起点在下方
                    LogUtils.d("initTable", "不可以选择");
                } else if (currentColumnData.getLessonto() < endR && currentColumnData.getLessonto() >= startR) {
                    //终点上面有数据 起点在上方
                    LogUtils.d("initTable", "ye不可以选择");
                } else {
                    //终点有数据 起点在中间
                    LogUtils.d("initTable", "终点有数据--起点在中间");

                    addColor(startCol, endCol, startR, endR);

                }
            }
        } else {
            //  终点没有数据 起点有数据

            if (startBeans.size() > 0) {
                //起点有数据
                for (int n = 0; n < startBeans.size(); n++) {
                    BigScreenBean currentStartColumnData = startBeans.get(n);
                    if (currentStartColumnData.getLessonfrom() > startR && currentStartColumnData.getLessonfrom() <= endR) {
                        LogUtils.d("initTable", "起点有数据不可以选择");
                    } else if (currentStartColumnData.getLessonto() < startR && currentStartColumnData.getLessonto() >= endR) {
                        LogUtils.d("initTable", "起点有数据ye不可以选择");
                    } else {
                        addColor(startCol, endCol, startR, endR);
                    }
                }
            } else {
                //起点没数据终点灭
                addColor(startCol, endCol, startR, endR);
            }
        }
    }

    private void addColor(int startCol, int endCol, int startR, int endR) {
        LogUtils.d("initTable", "addColor" + startCol, endCol, startR, endR);
        int selectColumn = endCol - startCol + 1;
        int typeNum = (int) (Math.random() * 500 + 50);

        for (int i = 0; i < selectColumn; i++) {
            if (startR > endR) {
                mDBHelper.insert(new BigScreenBean(typeNum, startCol + i, endR, startR, "cctv" + typeNum, currentTemp, currentTempName));
            } else {
                mDBHelper.insert(new BigScreenBean(typeNum, startCol + i, startR, endR, "cctv" + typeNum, currentTemp, currentTempName));
            }

        }

        updataTelep();
    }


    //初始化课表，分配空间，将courseInfoList中的课程放入courseInfoMap中
    private void initCourse() {
        courseInfoMap = new HashMap<String, List<BigScreenBean>>();
        for (int i = 0; i < columnNum; i++) {
            LinkedList<BigScreenBean> dayCourses = new LinkedList<BigScreenBean>();
            for (BigScreenBean courseInfo : bigScreenBeans) {
                int column = courseInfo.getColumn();
                if (column == i) {
                    dayCourses.add(courseInfo);
                }
            }
            courseInfoMap.put(String.valueOf(i), dayCourses);
        }
    }


    private void initCourseTableBody() {

        for (Map.Entry<String, List<BigScreenBean>> entry : courseInfoMap.entrySet()) {
            //查找出最顶层的课程信息（顶层课程信息即显示在最上层的课程，最顶层的课程信息满足两个条件 1、当前周数在该课程的周数范围内 2、该课程的节数跨度最大
            BigScreenBean upperCourse = null;
            //list里保存的是一周内某 一天的课程
            final List<BigScreenBean> list = new ArrayList<BigScreenBean>(entry.getValue());
            //按开始的时间（哪一节）进行排序
            Collections.sort(list, new Comparator<BigScreenBean>() {
                @Override
                public int compare(BigScreenBean arg0, BigScreenBean arg1) {

                    if (arg0.getLessonfrom() < arg1.getLessonfrom())
                        return -1;
                    else
                        return 1;
                }

            });
            int lastListSize;
            do {

                lastListSize = list.size();
                Iterator<BigScreenBean> iter = list.iterator();
                //先查找出第一个在周数范围内的课
                while (iter.hasNext()) {
                    BigScreenBean c = iter.next();

                    upperCourse = c;


                }
                if (upperCourse != null) {
                    List<BigScreenBean> cInfoList = new ArrayList<BigScreenBean>();
                    cInfoList.add(upperCourse);
                    int index = 0;
                    iter = list.iterator();
                    //查找这一天有哪些课与刚刚查找出来的顶层课相交
                    while (iter.hasNext()) {
                        BigScreenBean c = iter.next();
                        //先判断该课程与upperCourse是否相交，如果相交加入cInfoList中
                        if ((c.getLessonfrom() <= upperCourse.getLessonfrom()
                                && upperCourse.getLessonfrom() < c.getLessonto())
                                || (upperCourse.getLessonfrom() <= c.getLessonfrom()
                                && c.getLessonfrom() < upperCourse.getLessonto())) {
                            cInfoList.add(c);
                            iter.remove();


                        }

                    }

                    //五种颜色的背景
                    int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                            R.drawable.main_course3, R.drawable.main_course4,
                            R.drawable.main_course5, R.drawable.main_course6, R.drawable.main_course7,
                            R.drawable.main_course8, R.drawable.main_course9,
                            R.drawable.main_course10};
                    //记录顶层课程在cInfoList中的索引位置
                    final int upperCourseIndex = index;
                    // 动态生成课程信息TextView
                    TextView courseInfo = new TextView(this);
                    courseInfo.setId((int) (1000 + upperCourse.getColumn() * 100 + upperCourse.getLessonfrom() * 10 + upperCourse.getId()));//设置id区分不同课程
                    int id = courseInfo.getId();
                    textviewCourseInfoMap.put(id, cInfoList);
                    courseInfo.setText("");
                    //该textview的高度根据其节数的跨度来设置
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            aveWidth,
                            (gridHeight) * 2 + (upperCourse.getLessonto() - upperCourse.getLessonfrom() - 1) * gridHeight);
                    //textview的位置由课程开始节数和上课的时间（day of week）确定
                    rlp.topMargin = (upperCourse.getLessonfrom() - 1) * gridHeight;
                    rlp.leftMargin = 0;
                    // 前面生成格子时的ID就是根据Day来设置的，偏移由这节课是星期几决定
                    rlp.addRule(RelativeLayout.RIGHT_OF, upperCourse.getColumn());
                    //字体居中中
                    courseInfo.setGravity(Gravity.CENTER);
                    //选择一个颜色背景

                    courseInfo.setBackgroundResource(background[upperCourse.getType() % 10]);

                    //                    int colorIndex = ((upperCourse.getLessonfrom() - 1) * 8 + upperCourse.getDay()) % (background.length - 1);

                    courseInfo.setTextSize(12);
                    courseInfo.setLayoutParams(rlp);
                    courseInfo.setTextColor(Color.WHITE);
                    //设置不透明度
                    courseInfo.getBackground().setAlpha(200);
                    // 设置监听事件
                    courseInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            Log.v("text_view", String.valueOf(arg0.getId()));
                            Map<Integer, List<BigScreenBean>> map = textviewCourseInfoMap;
                            final List<BigScreenBean> tempList = map.get(arg0.getId());
                            if (tempList.size() > 1) {
                                //如果有多个课程，则设置点击弹出gallery 3d 对话框
                                //                                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                //                                View galleryView = layoutInflater.inflate(R.layout.info_gallery_layout, null);
                                //                                final Dialog coursePopupDialog = new AlertDialog.Builder(CourseActivity.this).create();
                                //                                coursePopupDialog.setCanceledOnTouchOutside(true);
                                //                                coursePopupDialog.setCancelable(true);
                                //                                coursePopupDialog.show();
                                //                                WindowManager.LayoutParams params = coursePopupDialog.getWindow().getAttributes();
                                //                                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                                //                                coursePopupDialog.getWindow().setAttributes(params);
                                //                                CourseInfoAdapter adapter = new CourseInfoAdapter(CourseActivity.this, tempList, screenWidth, cw);
                                //                                InfoGallery gallery = (InfoGallery) galleryView.findViewById(R.id.info_gallery);
                                //                                gallery.setSpacing(10);
                                //                                gallery.setAdapter(adapter);
                                //                                gallery.setSelection(upperCourseIndex);
                                //                                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                //                                    @Override
                                //                                    public void onItemClick(
                                //                                            AdapterView<?> arg0, View arg1,
                                //                                            int arg2, long arg3) {
                                //                                        CourseInfo courseInfo = tempList.get(arg2);
                                //                                        Intent intent = new Intent();
                                //                                        Bundle mBundle = new Bundle();
                                //                                        mBundle.putSerializable("courseInfo", courseInfo);
                                //                                        intent.putExtras(mBundle);
                                //                                        intent.setClass(CourseActivity.this, CourseDetailInfoActivity.class);
                                //                                        startActivity(intent);
                                //                                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                //                                        coursePopupDialog.dismiss();
                                //                                        finish();
                                //                                    }
                                //                                });
                                //                                coursePopupDialog.setContentView(galleryView);
                            } else {
                                //                                Intent intent = new Intent();
                                //                                Bundle mBundle = new Bundle();
                                //                                mBundle.putSerializable("courseInfo", tempList.get(0));
                                //                                intent.putExtras(mBundle);
                                //                                intent.setClass(CourseActivity.this, CourseDetailInfoActivity.class);
                                //                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                //                                startActivity(intent);
                                //                                finish();
                            }

                        }

                    });
                    rlContent.addView(courseInfo);


                    upperCourse = null;
                }
            } while (list.size() < lastListSize && list.size() != 0);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_edit:

                editClick++;
                if (editClick % 2 == 0) {
                    typeLits.clear();
                    updateEditState(false);
                    updataTelep();

                } else {
                    typeLits.clear();
                    updateEditState(true);
                    showEditIcon();
                }
                Log.i("initTable", "bt_edit");
                //                编辑 显示 叉号  并且可以滑动加载新的模板


                break;
            case R.id.bt_clean:
                mDBHelper.deleteByTempType(currentTemp);
                initTable();
                updataTelep();
                break;
            case R.id.bt_commit:
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        tcpClient.send("<call," + currentTemp + ",0>");
                    }
                });
                break;
            default:
        }
    }

    private void showEditIcon() {
        for (Map.Entry<String, List<BigScreenBean>> entry : courseInfoMap.entrySet()) {
            BigScreenBean upperCourse = null;
            //list里保存的是一周内某 一天的课程
            final List<BigScreenBean> list = new ArrayList<BigScreenBean>(entry.getValue());
            for (int m = 0; m < list.size(); m++) {

                upperCourse = list.get(m);

                final int type = upperCourse.getType();
                //只保留相同类型数据 在第一个item 显示叉号
                if (typeLits.contains(type)) {

                } else {
                    typeLits.add(type);


                    final ImageView split = new ImageView(this);
                    split.setId((int) (2000 + upperCourse.getColumn() * 100 + upperCourse.getLessonfrom() * 10 + upperCourse.getId()));
                    split.setImageResource(R.mipmap.icon_close);


                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            aveWidth / 4,
                            (gridHeight) / 4);
                    //textview的位置由课程开始节数和上课的时间（day of week）确定
                    rlp.topMargin = (upperCourse.getLessonfrom() - 1) * gridHeight + 10;
                    rlp.leftMargin = 10;
                    // 前面生成格子时的ID就是根据Day来设置的，偏移由这节课是星期几决定
                    rlp.addRule(RelativeLayout.RIGHT_OF, upperCourse.getColumn());
                    //字体居中中

                    split.setLayoutParams(rlp);
                    if (isEditAble) {
                        rlContent.addView(split);
                    } else {
                        rlContent.removeView(split);
                    }


                    split.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击后 删除拼接的模块


                            mDialog.setMessage("是否删除")
                                    .setTitle("提示")
                                    .setEditorText(false)
                                    .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                                @Override
                                public void onPositiveClick(String name) {
                                    mDialog.dismiss();
                                    mDBHelper.deleteByType(type);
                                    rlContent.removeView(split);
                                    initTable();
                                    updataTelep();
                                    updateEditState(true);
                                    typeLits.clear();
                                    showEditIcon();
                                }

                                @Override
                                public void onNegtiveClick() {
                                    mDialog.dismiss();

                                }
                            }).show();


                        }
                    });


                }
            }
        }
    }

    private class MyHandler extends android.os.Handler {
        private WeakReference<GreenActivity> mActivity;

        MyHandler(GreenActivity activity) {
            mActivity = new WeakReference<GreenActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null) {
                switch (msg.what) {
                    case 1:

                        mDialog.setMessage("服务连接失败,重新连接")
                                .setTitle("提示")
                                .setEditorText(false)
                                .setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick(String name) {
                                mDialog.dismiss();

                                initTCP();
                            }

                            @Override
                            public void onNegtiveClick() {
                                mDialog.dismiss();

                            }
                        }).show();
                        break;

                }
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction) {
                case "tcpClientReceiver":
                    Message message = Message.obtain();
                    message.what = 1;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }


}
