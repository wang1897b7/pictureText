package com.wsd.text.pict_can.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.component.pictureChose.ChoosePhotoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class SelectChose extends AppCompatActivity {


    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;
    @BindView(R.id.but_photo)
    Button mButPhoto;
    @BindView(R.id.activity_select_chose)
    RelativeLayout mActivitySelectChose;
    private List<PhotoInfo> mPhotoList;
    private ChoosePhotoListAdapter mChoosePhotoListAdapter;
    private Button mOpenGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chose);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String text=intent.getStringExtra("text");
        Toast.makeText(this,"这是一个空的"+text,Toast.LENGTH_LONG).show();
        init();
    }

    private void init() {
//        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
//        final FunctionConfig functionConfig = functionConfigBuilder.build();
//        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
        //带配置
        // mChoosePhotoListAdapter = new ChoosePhotoListAdapter(this, mPhotoList);
        //  mLvPhoto.setAdapter(mChoosePhotoListAdapter);

        mButPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(5)
                        .build();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);
            }
        });

    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(SelectChose.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
