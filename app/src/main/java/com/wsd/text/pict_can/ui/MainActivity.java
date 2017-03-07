package com.wsd.text.pict_can.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.fragment.HomeFragmemt;
import com.wsd.text.pict_can.ui.fragment.PersonalFragment;
import com.wsd.text.pict_can.ui.fragment.SampleFragment;
import com.wsd.text.pict_can.ui.fragment.ShopFragment;
import com.wsd.text.pict_can.utils.ToastUtil;
import com.wsd.text.pict_can.view.StillViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.viewpager)
    StillViewPager mViewpager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void obtainParam(final Intent intent) {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mViewpager.setPagingEnabled(false);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_nor, "Home").setActiveColorResource(R
                        .color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_customer_nor, "Books").setActiveColorResource(R
                        .color.main_black))
                .addItem(new BottomNavigationItem(R.drawable.ic_management_nor, "Music")
                        .setActiveColorResource(R.color.main_black_sub))
                .addItem(new BottomNavigationItem(R.drawable.ic_order_nor, "Movies & TV").setActiveColorResource
                        (R.color.main_black_third))
                .addItem(new BottomNavigationItem(R.drawable.ic_customer_nor, "个人")
                        .setActiveColorResource(R.color.red))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

        setupViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(5);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mBottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    @Override
    public void onTabSelected(final int position) {
        mViewpager.setCurrentItem(position, false);
    }

    @Override
    public void onTabUnselected(final int position) {

    }

    @Override
    public void onTabReselected(final int position) {

    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragmemt.newInstance());
        adapter.addFragment(SampleFragment.newInstance());
        adapter.addFragment(ShopFragment.newInstance());
        adapter.addFragment(SampleFragment.newInstance());
        adapter.addFragment(PersonalFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static class FragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

    long firstBackPressedTime = 0;

    @Override
    public void onBackPressed() {
        long secondBackPressedTime = System.currentTimeMillis();
        if (secondBackPressedTime - firstBackPressedTime > 2000) {
            ToastUtil.showAtUI("再按一次，退出应用");
            firstBackPressedTime = secondBackPressedTime;
        } else {
            finish();
        }
    }
}
