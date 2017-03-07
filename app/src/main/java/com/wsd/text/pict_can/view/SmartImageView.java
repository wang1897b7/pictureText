package com.wsd.text.pict_can.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SmartImageView extends ImageView {

    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setImageUrl(String url) {
        setImage(url, 0, 0);
    }

    public void setImageUrl(String url, @DrawableRes int loadDrawable) {
        setImage(url, 0, loadDrawable);
    }

    public void setImageUrl(String url, @DrawableRes int errorDrawable, @DrawableRes int loadDrawable) {
        setImage(url, errorDrawable, loadDrawable);
    }

    private void setImage(String url, @DrawableRes int errorDrawable, @DrawableRes int loadDrawable) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.contains("https")) {
            url = url.replace("https", "http");
        }
        // TODO: 2016/11/3 加入占位图片
        if (errorDrawable == 0) {
            errorDrawable = 0;
        }
        if (loadDrawable == 0) {
            loadDrawable = 0;
        }
        setScaleType(ScaleType.FIT_XY);
        if (getContext() != null) {
            Glide.with(getContext().getApplicationContext())
                    .load(url)
                    .fitCenter()
                    .placeholder(loadDrawable)
                    .error(errorDrawable)
                    .crossFade()
                    .into(this);
        }
        invalidate();
    }

}