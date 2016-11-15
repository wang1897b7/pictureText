package com.wsd.text.pict_can.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.adapter.SwipeAdapter;
import com.wsd.text.pict_can.model.Customer;
import com.wsd.text.pict_can.ui.BaseLoadingFragment;
import com.wsd.text.pict_can.ui.zxing.ScanCodeActivity;
import com.wsd.text.pict_can.view.MenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 2016/7/14.
 */
public class PersonalFragment extends BaseLoadingFragment implements BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {
    View view;
    SwipeAdapter mArchiveAdapter;
    MenuView mRightMenu;
    @BindView(R.id.scroll_view)
    PullToZoomScrollViewEx mScrollView;
    ViewHolder viewHolder;


    public static PersonalFragment newInstance() {

        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
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
        ButterKnife.bind(this, view);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void initData() {
        init();
    }

    private void init() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_content_view, null, false);
        mScrollView.setHeaderView(headView);
        mScrollView.setZoomView(zoomView);
        mScrollView.setScrollContentView(contentView);
        viewHolder = new ViewHolder(contentView);


        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        mScrollView.setHeaderLayoutParams(localObject);

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer c = new Customer();
            c.name = i + "";
            customers.add(c);
        }
        mArchiveAdapter = new SwipeAdapter(customers);
        mArchiveAdapter.setOnLoadMoreListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        viewHolder.mArchiveRecyclerView.setLayoutManager(manager);
        viewHolder.mArchiveRecyclerView.setAdapter(mArchiveAdapter);
        viewHolder.mArchiveRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==0){
                   startActivity(ScanCodeActivity.intent(getActivity()));
                }
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }


    @Override
    public void onRefresh() {
//        final List<Customer> customers = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Customer c = new Customer();
//            c.name = i + "";
//            customers.add(c);
//        }
//            mArchiveAdapter.setNewData(customers);
//        viewHolder.mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    static class ViewHolder {
        @BindView(R.id.archive_recycler_view)
        RecyclerView mArchiveRecyclerView;
        @BindView(R.id.swipe_refresh_layout)
        SwipeRefreshLayout mSwipeRefreshLayout;
        @BindView(R.id.container)
        CoordinatorLayout mContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
