package com.hansen.fullvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hansen.fullvideo.utils.LogUtils;

/**
 * Created by yuxin on 2017/5/22.
 */


@SuppressLint("DrawAllocation")
public class BorderTextView extends AppCompatTextView {

    private Paint paint,mPainRect;
    private int mcolor;

    public BorderTextView(Context context) {
        super(context);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int sroke_width = 3;

    @Override
    protected void onDraw(Canvas canvas) {

        paint = new Paint();

        //  将边框设为黑色
        paint.setColor(getResources().getColor(R.color.white));

        //  画TextView的4个边
        canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);
        canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);
        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);


        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        paint.setColor(getResources().getColor(mcolor));
        canvas.drawRect(0+sroke_width,0+sroke_width,this.getWidth()-sroke_width,this.getHeight()-sroke_width, paint);
    }

    public void setBackColor(int color) {
        mcolor = color;
        invalidate();
    }
}
