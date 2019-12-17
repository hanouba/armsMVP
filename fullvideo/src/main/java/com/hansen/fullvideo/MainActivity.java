package com.hansen.fullvideo;




import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected int aveWidth;//课程格子平均宽度
    protected int screenWidth;//屏幕宽度
    protected int gridHeight = 80;//格子高度

    // 行数数量
    private final int rowNum = 6;
    //列数
    private final int columnNum =10;
    //空格显示区域
    private RelativeLayout rlContent;
    //空的用于定位
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTable();
    }

    private void initView() {
        rlContent = findViewById(R.id.rl_video_content);
        tvEmpty = findViewById(R.id.tv_empty);
    }

    private void initTable() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / columnNum;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = dm.heightPixels;
        gridHeight = height / rowNum;


        //循环遍历没行
        for (int i = 1; i <= rowNum; i++) {
            // 遍历每列
            for (int j = 1; j <= columnNum; j++) {
                BorderTextView tx = new BorderTextView(this);
                tx.setId((i - 1) * columnNum + j);
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 33 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //字体样式
                tx.setTextAppearance(this, R.style.courseTableText);
                //如果是第一列
                if (j == 1) {
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, tvEmpty.getId());
                    else
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
}
