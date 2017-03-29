package com.wsd.text.pict_can.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.component.SmartImageView;
import com.wsd.text.pict_can.ui.BaseFragment;
import com.wsd.text.pict_can.ui.activity.FlowLayoutActivity;
import com.wsd.text.pict_can.ui.activity.SelectChose;
import com.wsd.text.pict_can.ui.activity.SlidLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 2016/7/14.
 */
public class HomeFragmemt extends BaseFragment {


    @BindView(R.id.first)
    Button mFirst;

    View mView;
    @BindView(R.id.second)
    Button mSecond;
    @BindView(R.id.pictureChose)
    Button mPictureChose;
    @BindView(R.id.img_url)
    SmartImageView mImgUrl;
    String url="";
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mView);
        mContext=getActivity();
        init();
        return mView;
    }

    public static HomeFragmemt newInstance() {

        Bundle args = new Bundle();
        HomeFragmemt fragment = new HomeFragmemt();
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        mFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidLayout.class);
                startActivity(intent);
            }
        });
        mSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FlowLayoutActivity.class);
                startActivity(intent);
            }
        });
        mPictureChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectChose.class);
                intent.putExtra("text","2134");
                startActivity(intent);
            }
        });
        mImgUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url= TextUtils.isEmpty(url)?  "https://jmttest.oss-cn-hangzhou.aliyuncs.com/AABYcxnZaubgbw1Z.png":"http://www.baidu.com";
                mImgUrl.setImageUrl(url);
            }
        });
        checkPermission();
    }

    private void checkPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission
                .WRITE_EXTERNAL_STORAGE);
        int phoneSatePermission =
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED && phoneSatePermission != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtil.showAtUI("授权成功");
//                PushManager.getInstance().initialize(this.getApplicationContext(), GetuiPushService.class);
//                PushManager.getInstance().registerPushIntentService(this.getApplicationContext(),
// GetuiIntentService.class);
            } else {
                new AlertDialog.Builder(getActivity()).setMessage("请在APP设置页面打开相应权限").setPositiveButton("确定", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                              //  AppUtil.openAppInfo(getActivit.y, getPackageName());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                       // finish();
                    }
                }).show();

            }
        }
    }


}
