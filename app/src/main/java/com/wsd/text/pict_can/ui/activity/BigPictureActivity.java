package com.wsd.text.pict_can.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wsd.text.pict_can.BuildConfig;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.utils.CheckUtil;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigPictureActivity extends AppCompatActivity {

    private Context context;
    private PhotoViewAttacher mAttacher;


    private Matrix mCurrentDisplayMatrix = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture);

        context = this;
//        Drawable bitmap = ContextCompat.getDrawable(this, R.drawable.wallpaper);
//        mImageView.setImageDrawable(bitmap);
        initData();
    }
    private void initData() {
        final PhotoView mImageView = (PhotoView) findViewById(R.id.photo_view);
        final String imgURL = getIntent().getStringExtra("imageUrl");
        final String ErrMsg = "加载失败";
       // String imageUrl = "http://cjwdata.superwan.cn/AABXowvXNTWa4czB.jpg";
        String imageUrl = "http://cjwdata.superwan.cn/AABXmc0ocLLbOszy.jpg";

        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        bitmap=resource;
//
                        mImageView .setImageBitmap(resource);
                        mAttacher = new PhotoViewAttacher(mImageView);
                        // Lets attach some listeners, not required though!
                        mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
                        mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                        mAttacher.setOnSingleFlingListener(new SingleFlingListener());

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        CheckUtil.showShortMessage(context, ErrMsg);
                        finish();
                    }
                });

        // The MAGIC happens here!

    }


    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

          //  showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }

        @Override
        public void onOutsidePhotoTap() {
         //   showToast("You have a tap event on the place where out of the photo.");
        }
    }



    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {

            Toast.makeText(context,""+rect.toString(),Toast.LENGTH_SHORT);
        }
    }

    private class SingleFlingListener implements PhotoViewAttacher.OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (BuildConfig.DEBUG) {
               // Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
            }
            return true;
        }
    }
}
