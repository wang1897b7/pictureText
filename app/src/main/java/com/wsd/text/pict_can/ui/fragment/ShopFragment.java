package com.wsd.text.pict_can.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.model.TestModel;
import com.wsd.text.pict_can.ui.BaseLoadingFragment;
import com.wsd.text.pict_can.utils.DividerItemDecoration;
import com.wsd.text.pict_can.utils.ItemTouchHelperCallback;
import com.wsd.text.pict_can.utils.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sun on 2016/7/14.
 */
public class ShopFragment extends BaseLoadingFragment {

    @BindView(R.id.shop_tvText)
    TextView username;
    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;

    private MainRecyclerAdapter mAdapter;
    public ItemTouchHelperExtension mItemTouchHelper;
    public ItemTouchHelperExtension.Callback mCallback;

    public static ShopFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_shop,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    @Override
    protected int getContentLayout() {
        return 0;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(View view) {

    }

    private void init(){
//    username.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//      });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mAdapter.updateData(createTestDatas());
        mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter.setItemTouchHelperExtension(mItemTouchHelper);

    }

    private List<TestModel> createTestDatas() {
        List<TestModel> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TestModel testModel= new TestModel(i,":Item Swipe Action Button Container Width");
            if (i == 1) {
                testModel = new TestModel(i, "Item Swipe with Action container width and no spring");
            }
            if (i == 2) {
                testModel = new TestModel(i, "Item Swipe with RecyclerView Width");
            }
            result.add(testModel);
        }
        return result;
    }
    @OnClick(R.id.shop_tvText) void submit() {
        Toast.makeText(getActivity(),"测试",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }
}
