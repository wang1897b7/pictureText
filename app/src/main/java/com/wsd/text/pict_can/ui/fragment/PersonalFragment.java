package com.wsd.text.pict_can.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.activity.BigPictureActivity;

/**
 * Created by Sun on 2016/7/14.
 */
public class PersonalFragment extends BaseFragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_personal,container,false);
        init();
        return view;
    }

    private void init(){
        TextView tv=(TextView) view.findViewById(R.id.tv_bigPic);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BigPictureActivity.class);
                startActivity(intent);
            }
        });
    }
}
