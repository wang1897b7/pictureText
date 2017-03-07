package com.wsd.text.pict_can.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wsd.text.pict_can.R;


/**
 * 作者：王敏 on 2015/8/21 17:31
 * 类说明：画出扫描框的四个脚的脚边框，也可以直接用一张图片代替
 */
public class ScanView extends ImageView {
    private Context context;
    Paint mPaint;


    public ScanView(Context context) {
        super(context);
        this.context = context;
    }

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.main));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(t(5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.drawLine(0, 0, 0, t(18), mPaint);
        canvas.drawLine(0, 0, t(18), 0, mPaint);

        canvas.drawLine(0, height - t(18), 0, height, mPaint);
        canvas.drawLine(0, height, t(18), height, mPaint);

        canvas.drawLine(width - t(18), 0, width, 0, mPaint);
        canvas.drawLine(width, 0, width, t(18), mPaint);

        canvas.drawLine(width, height - t(18), width, height, mPaint);
        canvas.drawLine(width - t(18), height, width, height, mPaint);
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int t(float dpVal) {
        return dp2px(dpVal);
    }

}
