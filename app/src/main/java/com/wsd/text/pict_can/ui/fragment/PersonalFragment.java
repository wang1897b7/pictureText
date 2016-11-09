package com.wsd.text.pict_can.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.BaseLoadingFragment;
import com.wsd.text.pict_can.ui.activity.BigPictureActivity;

/**
 * Created by Sun on 2016/7/14.
 */
public class PersonalFragment extends BaseLoadingFragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_personal,container,false);

        return view;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(View view) {
        init();
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }
    private void init(){
        TextView tv=(TextView) view.findViewById(R.id.tv_bigPic);
        PullToZoomScrollViewEx tt= (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BigPictureActivity.class);
                startActivity(intent);
            }
        });

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        tt.setHeaderLayoutParams(localObject);
    }


}
