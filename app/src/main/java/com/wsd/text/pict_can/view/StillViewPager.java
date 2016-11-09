package com.wsd.text.pict_can.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Libery on 2016/11/2.
 * Email:libery.szq@qq.com
 */

public class StillViewPager extends ViewPager {

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    public void setPagingEnabled(final boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    private boolean pagingEnabled;

    public StillViewPager(final Context context) {
        super(context);
    }

    public StillViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !pagingEnabled || super.onTouchEvent(ev);
    }
}