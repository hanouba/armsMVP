package com.hansen.fullvideo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;

import com.hansen.fullvideo.R;
import com.hansen.fullvideo.utils.LogUtils;

/**
 * @author HanN on 2019/12/19 15:58
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/19 15:58
 * @updateremark:
 * @version: 2.1.67
 */
public class BigScreenControlView extends View {
    private Paint mPaint;
    private Canvas mCanvas;
    private float mLeft,mRight,mTop,mBottom;
    public BigScreenControlView(Context context) {
        this(context,null);
    }

    public BigScreenControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BigScreenControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);




        mCanvas = canvas;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        LogUtils.d("onDraw"+mLeft);
        drawSelectArea();
    }

    private void drawSelectArea() {
        mCanvas.drawRect(mLeft,mTop,mRight,mBottom,mPaint);

    }
    public void setSelectArea(float left, float top,float right ,float bottom) {
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
        invalidate();
    }
}
