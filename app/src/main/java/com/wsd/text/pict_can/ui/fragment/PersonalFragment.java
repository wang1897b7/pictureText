package com.wsd.text.pict_can.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.model.Customer;
import com.wsd.text.pict_can.ui.BaseLoadingFragment;
import com.wsd.text.pict_can.view.MenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 2016/7/14.
 */
public class PersonalFragment extends BaseLoadingFragment {
    View view;
    @BindView(R.id.archive_recycler_view)
    RecyclerView mArchiveRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.left_menu)
    MenuView mLeftMenu;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.right_menu)
    MenuView mRightMenu;
    @BindView(R.id.toolbar)
    FrameLayout mToolbar;
    @BindView(R.id.container)
    CoordinatorLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        init();
        ButterKnife.bind(this, view);
        return view;
    }

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

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }

    private void init() {
//        TextView tv=(TextView) view.findViewById(R.id.tv_bigPic);
//        PullToZoomScrollViewEx tt= (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);

       /* tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BigPictureActivity.class);
                startActivity(intent);
            }
        });*/
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);


        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    private void initArchiveRecyclerView(){
//        mSwipeRefreshLayout.setOnRefreshListener(getActivity());
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer c = new Customer();
            c.name= i + "";
            customers.add(c);
        }
//        mArchiveAdapter = new SwipeAdapter(customers);
//        mArchiveAdapter.setOnLoadMoreListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mArchiveRecyclerView.setLayoutManager(manager);
     //   mArchiveRecyclerView.setAdapter(mArchiveAdapter);

    }
//    private void loadViewForCode() {
//        PullToZoomScrollViewEx scrollView= (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);
//        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_head_view, null, false);
//        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_zoom_view, null, false);
//        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_content_view, null, false);
//        scrollView.setHeaderView(headView);
//        scrollView.setZoomView(zoomView);
//        scrollView.setScrollContentView(contentView);
//    }
}
