package com.wsd.text.pict_can.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.adapter.ViewPagerAdapter;
import com.wsd.text.pict_can.ui.BaseFragment;
import com.wsd.text.pict_can.view.SlidingTabLayout;

import java.util.List;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

/**
 * Created by Sun on 2016/7/14.
 */
public class HomeFragmemt extends BaseFragment {

    ViewPager pager;
    private String[] titles = new String[]{"Sample Tab 1", "Sample Tab 2", "Sample Tab 3", "Sample Tab 4"
            , "Sample Tab 5", "Sample Tab 6", "Sample Tab 7", "Sample Tab 8"};
    private Toolbar toolbar;
    View mView;
    SlidingTabLayout slidingTabLayout;
    private FrameLayout topBanner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.fragment_home, container, false);
        pager = (ViewPager)mView. findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout)mView. findViewById(R.id.sliding_tabs);
        init();
        return mView;
    }

    public static HomeFragmemt newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragmemt fragment = new HomeFragmemt();
        fragment.setArguments(args);
        return fragment;
    }

    private void init(){

        pager.setAdapter(new ViewPagerAdapter(getFragmentManager(), titles));
        topBanner= (FrameLayout)  mView.findViewById(R.id.top_banner);
        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
    }

    private void initBanner(final List<String> banners, int inteval) {
        topBanner.removeAllViews();
        InfiniteIndicatorLayout indicator = new InfiniteIndicatorLayout(getActivity());
        for (int i = 0, size = banners.size(); i < size; i++) {
           // final Banner banner = banners.get(i);
            DefaultSliderView sliderView = new DefaultSliderView(getActivity());
           // DefaultSliderView sliderView = new DefaultSliderView(getActivity());
            sliderView.image("www.baidu.com")
                    .setScaleType(BaseSliderView.ScaleType.FitCenter)
                    .showImageResForEmpty(R.drawable.default_bg)
                    .showImageResForError(R.drawable.default_bg)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                          //  AppUtil.jumpToInfo(getActivity(), banner);
                        }
                    });
            indicator.addSlider(sliderView);
        }
        indicator.startAutoScroll(inteval * 1000);
        indicator.setIndicatorPosition(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        topBanner.addView(indicator);
    }
}
