package com.wsd.text.pict_can.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wsd.text.pict_can.R;


public class SmartImageView extends ImageView {

    private Context mContext;

    public SmartImageView(Context context) {
        super(context);
        mContext = context;
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }


    public void setImageUrl(String url) {
        setImage(url, 0, 0);
    }

    public void setImageUrl(String url, @DrawableRes int errorDrawable) {
        setImage(url, errorDrawable, 0);
    }

    public void setImageUrl(String url, @DrawableRes int errorDrawable, @DrawableRes int loadDrawable) {
        setImage(url, errorDrawable, loadDrawable);
    }

    private void setImage(String url, @DrawableRes int errorDrawable, @DrawableRes int loadDrawable) {
        if (url.contains("https")) {
            url = url.replace("https", "http");
        }
        if (errorDrawable == 0) {
            errorDrawable = R.drawable.default_bg;
        }
        if (loadDrawable == 0) {
            loadDrawable = R.drawable.default_bg;
        }
        setScaleType(ScaleType.FIT_XY);
        if (mContext != null) {

            Glide.with(mContext.getApplicationContext())
                    .load(url)
                    .fitCenter()
                    .placeholder(loadDrawable)
                    .error(errorDrawable)
                    .crossFade()
                    .into(this);
        }
    }

}