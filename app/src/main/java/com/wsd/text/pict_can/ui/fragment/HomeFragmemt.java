package com.wsd.text.pict_can.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mView);
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
    }


}
