package com.hansen.fullvideo;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.CourseInfo;
import com.hansen.fullvideo.bean.TemplateBean;
import com.hansen.fullvideo.dao.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
    private Button btEdit;

    //课程信息，key为星期几，value是这一天的课程信息
    //key 是第几列，value是这一列的信息
    private Map<String, List<CourseInfo>> courseInfoMap;


    /**
     * 数据存储变量 一个预案信息
     */
    private LinkedList<CourseInfo> courseInfoList;//课程信息链表，存储有包括cid在内的完整信息

    private Map<Integer, List<CourseInfo>> textviewCourseInfoMap;//保存每个textview对应的课程信息 map,key为哪一天（如星期一则key为1）
    private List<TextView> courseTextViewList;//保存显示课程信息的TextView


    private int hasShow = 0;


    private List<Integer> typeLits = new ArrayList<>();
    private DBHelper mDBHelper;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i("initTable", "onWindowFocusChanged" + hasFocus);
        hasShow++;
        if (hasShow == 1) {
            if (hasFocus) {

                initTable();

                initCourse();
                //显示课表内容
                initCourseTableBody(1);
            }
        } else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

        initData();

        checkPermiss();
    }

    private void initView() {
        rlContent = findViewById(R.id.rl_video_content);
        llBigScreen = findViewById(R.id.ll_big_screen);
        tvEmpty = findViewById(R.id.tv_empty);
        lvTemplate = findViewById(R.id.lv_template);
        llContent = findViewById(R.id.ll_content);
        rlContentTitle = findViewById(R.id.rl_content_title);
        btEdit = findViewById(R.id.bt_edit);

    }

    private void initListener() {
        btEdit.setOnClickListener(this);
    }

    private void initData() {
        mDBHelper = DBHelper.getInstance(MainActivity.this);
        courseInfoList = new LinkedList<CourseInfo>();
        textviewCourseInfoMap = new HashMap<Integer, List<CourseInfo>>();
        courseTextViewList = new ArrayList<TextView>();

        List<TemplateBean> tempLists = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {

            tempLists.add(new TemplateBean("微创预案" + i, R.mipmap.fab_add));
        }


        TemplateAdapter templateAdapter = new TemplateAdapter(this, R.layout.item_template, tempLists);

        lvTemplate.setAdapter(templateAdapter);



    }

    @AfterPermissionGranted(1)
    private void checkPermiss() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            Toast.makeText(this, "获取了权限", Toast.LENGTH_SHORT).show();
            getCourseFromServer(0);
            createLocalData();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    1, perms);
        }
    }

    /**
     * 创建预案数据
     */
    private void createLocalData() {
        BigScreenBean bigScreenBean = new BigScreenBean();

    }


    //从服务器端获取课表，此处demo为简单起见设置了两个例子展示一下效果
    private void getCourseFromServer(int userid) {
        //模拟从服务器获取的效果
        CourseInfo cInfo1 = new CourseInfo();
        cInfo1.setCid(1);
        cInfo1.setType(2);
        cInfo1.setDay(2);
        cInfo1.setLessonfrom(1);
        cInfo1.setLessonto(4);
        cInfo1.setCoursename("CCTV-2");
        cInfo1.setTeacher("李华");
        cInfo1.setPlace("第一教学楼302");
        CourseInfo cInfo6 = new CourseInfo();
        cInfo6.setCid(6);
        cInfo6.setType(2);
        cInfo6.setDay(3);
        cInfo6.setLessonfrom(1);
        cInfo6.setLessonto(4);
        cInfo6.setCoursename("CCTV-2");
        cInfo6.setTeacher("李华");
        cInfo6.setPlace("第一教学楼302");
        CourseInfo cInfo7 = new CourseInfo();
        cInfo7.setCid(7);
        cInfo7.setType(2);
        cInfo7.setDay(4);
        cInfo7.setLessonfrom(1);
        cInfo7.setLessonto(4);
        cInfo7.setCoursename("CCTV-2");
        cInfo7.setTeacher("李华");
        cInfo7.setPlace("第一教学楼302");
        CourseInfo cInfo8 = new CourseInfo();
        cInfo8.setCid(8);
        cInfo8.setType(2);
        cInfo8.setDay(5);
        cInfo8.setLessonfrom(1);
        cInfo8.setLessonto(4);
        cInfo8.setCoursename("CCTV-2");
        cInfo8.setTeacher("李华");
        cInfo8.setPlace("第一教学楼302");


        CourseInfo cInfo2 = new CourseInfo();
        cInfo2.setCid(2);
        cInfo2.setType(1);
        cInfo2.setDay(0);
        cInfo2.setLessonfrom(5);
        cInfo2.setLessonto(6);
        cInfo2.setCoursename("操作系统原理");
        cInfo2.setTeacher("王芳");
        cInfo2.setPlace("第三教学楼105");
        CourseInfo cInfo3 = new CourseInfo();
        cInfo3.setCid(3);
        cInfo3.setType(1);
        cInfo3.setDay(1);
        cInfo3.setLessonfrom(5);
        cInfo3.setLessonto(6);
        cInfo3.setCoursename("操作系统原理");
        cInfo3.setTeacher("王芳");
        cInfo3.setPlace("第三教学楼105");


        CourseInfo cInfo4 = new CourseInfo();
        cInfo4.setCid(4);
        cInfo4.setType(3);
        cInfo4.setDay(0);
        cInfo4.setLessonfrom(1);
        cInfo4.setLessonto(2);
        cInfo4.setCoursename("CCTV-1");
        cInfo4.setTeacher("王芳");
        cInfo4.setPlace("第三教学楼105");
        CourseInfo cInfo5 = new CourseInfo();
        cInfo5.setCid(5);
        cInfo5.setType(3);
        cInfo5.setDay(1);
        cInfo5.setLessonfrom(1);
        cInfo5.setLessonto(2);
        cInfo5.setCoursename("CCTV-1");
        cInfo5.setTeacher("王芳");
        cInfo5.setPlace("第三教学楼105");


        courseInfoList.add(cInfo1);
        courseInfoList.add(cInfo2);
        courseInfoList.add(cInfo3);
        courseInfoList.add(cInfo4);
        courseInfoList.add(cInfo5);
        courseInfoList.add(cInfo6);
        courseInfoList.add(cInfo7);
        courseInfoList.add(cInfo8);


        //如果从服务器获取成功，则插入数据库
        saveCourse();

    }

    //将课程列表存入数据库
    private void saveCourse() {



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 划分表格
     */
    private void initTable() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int left = rlContent.getLeft() + llBigScreen.getLeft() + lvTemplate.getLeft() + llContent.getLeft();
        int right = lvTemplate.getRight() + llBigScreen.getRight();

        int top = rlContent.getTop() + rlContentTitle.getTop() + llBigScreen.getTop();
        int bottom = rlContent.getTop() + rlContentTitle.getTop() + llBigScreen.getBottom();

        Log.i("initTable", "left" + left + "right" + right);
        Log.i("initTable", "top" + top + "bottom" + bottom);


        //屏幕宽度
        int width = right - left;


        //平均宽度
        int aveWidth = width / columnNum;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = bottom - top;
        gridHeight = height / rowNum;


        //循环遍历没行
        for (int i = 1; i <= rowNum; i++) {
            // 遍历每列
            for (int j = 1; j <= columnNum; j++) {
                BorderTextView tx = new BorderTextView(this);
                tx.setId((i - 1) * columnNum + j);
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 32 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
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
                rlContent.addView(tx);
            }
        }


    }


    //初始化课表，分配空间，将courseInfoList中的课程放入courseInfoMap中
    private void initCourse() {
        courseInfoMap = new HashMap<String, List<CourseInfo>>();
        for (int i = 0; i < columnNum; i++) {
            LinkedList<CourseInfo> dayCourses = new LinkedList<CourseInfo>();
            for (CourseInfo courseInfo : courseInfoList) {
                int day = courseInfo.getDay();
                if (day == i) {
                    dayCourses.add(courseInfo);
                }
            }
            courseInfoMap.put(String.valueOf(i), dayCourses);
        }
    }


    private void initCourseTableBody(int currentWeek) {

        for (Map.Entry<String, List<CourseInfo>> entry : courseInfoMap.entrySet()) {
            //查找出最顶层的课程信息（顶层课程信息即显示在最上层的课程，最顶层的课程信息满足两个条件 1、当前周数在该课程的周数范围内 2、该课程的节数跨度最大
            CourseInfo upperCourse = null;
            //list里保存的是一周内某 一天的课程
            final List<CourseInfo> list = new ArrayList<CourseInfo>(entry.getValue());
            //按开始的时间（哪一节）进行排序
            Collections.sort(list, new Comparator<CourseInfo>() {
                @Override
                public int compare(CourseInfo arg0, CourseInfo arg1) {

                    if (arg0.getLessonfrom() < arg1.getLessonfrom())
                        return -1;
                    else
                        return 1;
                }

            });
            int lastListSize;
            do {

                lastListSize = list.size();
                Iterator<CourseInfo> iter = list.iterator();
                //先查找出第一个在周数范围内的课
                while (iter.hasNext()) {
                    CourseInfo c = iter.next();

                    upperCourse = c;


                }
                if (upperCourse != null) {
                    List<CourseInfo> cInfoList = new ArrayList<CourseInfo>();
                    cInfoList.add(upperCourse);
                    int index = 0;
                    iter = list.iterator();
                    //查找这一天有哪些课与刚刚查找出来的顶层课相交
                    while (iter.hasNext()) {
                        CourseInfo c = iter.next();
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
                            R.drawable.main_course5};
                    //记录顶层课程在cInfoList中的索引位置
                    final int upperCourseIndex = index;
                    // 动态生成课程信息TextView
                    TextView courseInfo = new TextView(this);
                    courseInfo.setId(1000 + upperCourse.getDay() * 100 + upperCourse.getLessonfrom() * 10 + upperCourse.getCid());//设置id区分不同课程
                    int id = courseInfo.getId();
                    textviewCourseInfoMap.put(id, cInfoList);
                    courseInfo.setText(upperCourse.getCoursename() + "\n@" + upperCourse.getPlace());
                    //该textview的高度根据其节数的跨度来设置
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            aveWidth,
                            (gridHeight) * 2 + (upperCourse.getLessonto() - upperCourse.getLessonfrom() - 1) * gridHeight);
                    //textview的位置由课程开始节数和上课的时间（day of week）确定
                    rlp.topMargin = (upperCourse.getLessonfrom() - 1) * gridHeight;
                    rlp.leftMargin = 0;
                    // 前面生成格子时的ID就是根据Day来设置的，偏移由这节课是星期几决定
                    rlp.addRule(RelativeLayout.RIGHT_OF, upperCourse.getDay());
                    //字体居中中
                    courseInfo.setGravity(Gravity.CENTER);
                    //选择一个颜色背景

                    courseInfo.setBackgroundResource(background[upperCourse.getType()]);

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
                            Map<Integer, List<CourseInfo>> map = textviewCourseInfoMap;
                            final List<CourseInfo> tempList = map.get(arg0.getId());
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
                    courseTextViewList.add(courseInfo);

                    upperCourse = null;
                }
            } while (list.size() < lastListSize && list.size() != 0);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_edit:
                Log.i("initTable", "bt_edit");
                //                编辑 显示 叉号  并且可以滑动加载新的模板
                //1 拼接咋一起的 显示一个叉号
                for (Map.Entry<String, List<CourseInfo>> entry : courseInfoMap.entrySet()) {
                    CourseInfo upperCourse = null;
                    //list里保存的是一周内某 一天的课程
                    final List<CourseInfo> list = new ArrayList<CourseInfo>(entry.getValue());
                    for (int m = 0; m < list.size(); m++) {

                        upperCourse = list.get(m);

                        int type = upperCourse.getType();

                        if (typeLits.contains(type)) {
                        } else {
                            typeLits.add(type);


                            ImageView split = new ImageView(this);
                            split.setId(2000 + upperCourse.getDay() * 100 + upperCourse.getLessonfrom() * 10 + upperCourse.getCid());
                            split.setImageResource(R.mipmap.icon_close);
                            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                                    aveWidth / 4,
                                    (gridHeight) / 4);
                            //textview的位置由课程开始节数和上课的时间（day of week）确定
                            rlp.topMargin = (upperCourse.getLessonfrom() - 1) * gridHeight + 10;
                            rlp.leftMargin = 10;
                            // 前面生成格子时的ID就是根据Day来设置的，偏移由这节课是星期几决定
                            rlp.addRule(RelativeLayout.RIGHT_OF, upperCourse.getDay());
                            //字体居中中

                            split.setLayoutParams(rlp);
                            rlContent.addView(split);

                            split.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //点击后 删除拼接的模块

                                    Log.i("initTable", "删除拼接的模块"+v.getId());


                                }
                            });



                        }
                    }
                }


                break;
            default:
        }
    }
}
