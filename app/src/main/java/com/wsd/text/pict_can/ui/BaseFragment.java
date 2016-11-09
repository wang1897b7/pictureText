package com.wsd.text.pict_can.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import rx.subscriptions.CompositeSubscription;

/**
 * Created by Libery on 2016/11/1.
 * Email:libery.szq@qq.com
 */

public class BaseFragment extends Fragment {

    public CompositeSubscription mSubscription;

//    protected abstract static class ResultListener<T> implements SubscriberListener<T> {
//        @Override
//        public void onError(final Throwable e) {
//
//        }
//    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }

}
