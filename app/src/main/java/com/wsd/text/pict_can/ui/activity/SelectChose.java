package com.wsd.text.pict_can.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ns.mutiphotochoser.constant.Constant;
import com.wsd.text.pict_can.BuildConfig;
import com.wsd.text.pict_can.MyApplication;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.component.ShapeImageView;
import com.wsd.text.pict_can.component.pictureChose.ChoosePhotoListAdapter;
import com.wsd.text.pict_can.utils.AppUtil;
import com.wsd.text.pict_can.utils.ChoosePictureUtil;
import com.wsd.text.pict_can.utils.ToastUtil;

import java.util.ArrayList;
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
    LinearLayout mActivitySelectChose;
    @BindView(R.id.expo_sale_shopLogo)
    ShapeImageView mExpoSaleShopLogo;
    private List<PhotoInfo> mPhotoList;
    private ChoosePhotoListAdapter mChoosePhotoListAdapter;
    private Button mOpenGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chose);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        Toast.makeText(this, "这是一个空的" + text, Toast.LENGTH_LONG).show();
        init();
    }

    private void init() {
        mExpoSaleShopLogo.setImageUrl("https://cjwdata.oss-cn-hangzhou.aliyuncs.com/AABY0ePF1O6y154t.jpg");
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();


        mButPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(5)
                        .build();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);
            }
        });

  //      checkPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                ArrayList<String> images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
                Log.e("www", "22" + images.size());
                //   mAdaper.swapDatas(images);
                break;

            case MyApplication.CHOOSE_PICTURE_REQUEST_CODE2:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> images1 = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);

                    for (int i=0;i<images1.size();i++){
                        Log.e("www", "" + images1.get(i));
                    }
                }
                break;
        }
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

    private void checkPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(SelectChose.this, Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SelectChose.this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {


           // takePhoto(mPhotoRequestCode);

            mButPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChoosePictureUtil choosePictureUtil = new ChoosePictureUtil(SelectChose.this);
                    choosePictureUtil.showDialog(MyApplication.CHOOSE_PICTURE_REQUEST_CODE, MyApplication
                            .CHOOSE_PICTURE_REQUEST_CODE2);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission Granted

                    mButPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ChoosePictureUtil choosePictureUtil = new ChoosePictureUtil(SelectChose.this);
                            choosePictureUtil.showDialog(MyApplication.CHOOSE_PICTURE_REQUEST_CODE, MyApplication
                                    .CHOOSE_PICTURE_REQUEST_CODE2);
                        }
                    });
                } else {
                    // Permission Denied
                    ToastUtil.showAtUI("请在APP设置页面打开相应权限");
                    AppUtil.openAppInfo(this, BuildConfig.APPLICATION_ID);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
