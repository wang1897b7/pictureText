package com.wsd.text.pict_can.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.utils.CheckUtil;
import com.wsd.text.pict_can.utils.PixelUtil;


/**
 * Created by Libery on 2016/11/9.
 * Email:libery.szq@qq.com
 * toolbar 菜单Menu 若为文字需写死宽、高度，图片则不需要
 */

public class MenuView extends ImageView {

    public int getColor() {
        return mColor;
    }

    public void setColor(final int color) {
        mColor = color;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(final float textSize) {
        mTextSize = textSize;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    private String mTitle;

    private Paint mPaint;
    private Rect mBounds;
    @ColorInt
    private int mColor;
    private float mTextSize = PixelUtil.dp2px(16);

    public MenuView(final Context context) {
        super(context);
    }


    public MenuView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(final AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuView);
        mTitle = a.getString(R.styleable.MenuView_menu_title);
        mColor = a.getColor(R.styleable.MenuView_menu_color, getResources().getColor(R.color.main_black));
        mTextSize = a.getDimension(R.styleable.MenuView_menu_text_size, mTextSize);
        a.recycle();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setTextSize(mTextSize);
        mBounds = new Rect();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (CheckUtil.isNotEmpty(mTitle)) {
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mTitle, (getMeasuredWidth() / 2 - mBounds.width() / 2), baseline, mPaint);
        }
    }
}
