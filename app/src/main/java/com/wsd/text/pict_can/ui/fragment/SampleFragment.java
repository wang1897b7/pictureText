package com.wsd.text.pict_can.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.BaseLoadingFragment;
import com.wsd.text.pict_can.view.FloatingActionButton;

public class SampleFragment extends BaseLoadingFragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static SampleFragment newInstance(int position) {
        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public static SampleFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.page, container, false);

        ProgressBarCircular progressBarCircular = (ProgressBarCircular) rootView.findViewById(R.id.progress);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
        switch (position) {
            case 0:
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                progressBarCircular.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                break;
            case 1:
                fab.setBackgroundColor(getResources().getColor(R.color.red));
                progressBarCircular.setBackgroundColor(getResources().getColor(R.color.red));

                break;
            case 2:
                progressBarCircular.setBackgroundColor(getResources().getColor(R.color.blue));
                fab.setBackgroundColor(getResources().getColor(R.color.blue));

                break;
            case 3:
                fab.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                progressBarCircular.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));

                break;
        }

        return rootView;
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

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }
}