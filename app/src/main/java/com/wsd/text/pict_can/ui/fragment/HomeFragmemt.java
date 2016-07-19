package com.wsd.text.pict_can.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.adapter.ViewPagerAdapter;
import com.wsd.text.pict_can.view.SlidingTabLayout;

/**
 * Created by Sun on 2016/7/14.
 */
public class HomeFragmemt extends BaseFragment{

    ViewPager pager;
    private String[] titles = new String[]{"Sample Tab 1", "Sample Tab 2", "Sample Tab 3", "Sample Tab 4"
            , "Sample Tab 5", "Sample Tab 6", "Sample Tab 7", "Sample Tab 8"};
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        pager = (ViewPager)mView. findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout)mView. findViewById(R.id.sliding_tabs);
        return mView;
    }

    private void init(){

        pager.setAdapter(new ViewPagerAdapter(getFragmentManager(), titles));

        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
    }
}
