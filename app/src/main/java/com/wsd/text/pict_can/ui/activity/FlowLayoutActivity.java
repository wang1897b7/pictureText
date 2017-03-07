package com.wsd.text.pict_can.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wsd.text.pict_can.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowLayoutActivity extends AppCompatActivity {

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mIdFlowlayout;
    private Context mContext;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        mContext=this;
        ButterKnife.bind(this);
        init();

    }
    private void init(){
        mIdFlowlayout.setMaxSelectCount(1);
        mIdFlowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView)  LayoutInflater.from(mContext).inflate(R.layout.tv,
                        mIdFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }
}
