package com.wsd.text.pict_can.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author zenghui.he
 * @email hezh@dxyer.com
 * @date 2014-2-8
 */
@SuppressLint("InflateParams")
public class BaseActivity extends FragmentActivity  implements View.OnClickListener{

    protected Context mContext;
    protected LayoutInflater mInflater;
    public ProgressDialog progress;
  // public CompositeSubscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = LayoutInflater.from(mContext);
      /*  if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }*/
    }

    @Override
    public void onClick(View view) {

    }

//    protected void initActionBar(String title) {
//        View view = findViewById(R.id.actionbar);
//        ImageView backView = (ImageView) view.findViewById(R.id.actionbar_back);
//        TextView titleView = (TextView) view.findViewById(R.id.actionbar_title);
//        if (AppUtil.isNotEmpty(title)) {
//            titleView.setText(title);
//        }
//        backView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//    }
//
//
//    public void start_activity(Intent intent) {
//        startActivity(intent);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//    }
//
//    public void start_activityForResult(Intent intent, int requestCode) {
//        startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//    }

//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//        if (mSubscription != null) {
//            mSubscription.clear();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//    }
}
