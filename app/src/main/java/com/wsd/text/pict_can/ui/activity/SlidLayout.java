package com.wsd.text.pict_can.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.adapter.ViewPagerAdapter;
import com.wsd.text.pict_can.view.SlidingTabLayout;

import java.util.List;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

public class SlidLayout extends AppCompatActivity {

    ViewPager pager;
    private String[] titles = new String[]{"Sample Tab 1", "Sample Tab 2", "Sample Tab 3", "Sample Tab 4"
            , "Sample Tab 5", "Sample Tab 6", "Sample Tab 7", "Sample Tab 8"};
    private Toolbar toolbar;
    View mView;
    SlidingTabLayout slidingTabLayout;
    private FrameLayout topBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slid_layout);
        pager = (ViewPager) mView.findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
        init();
    }

    private void init() {

        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));
        topBanner = (FrameLayout) mView.findViewById(R.id.top_banner);
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
        InfiniteIndicatorLayout indicator = new InfiniteIndicatorLayout(this);
        for (int i = 0, size = banners.size(); i < size; i++) {
            // final Banner banner = banners.get(i);
            DefaultSliderView sliderView = new DefaultSliderView(this);
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
