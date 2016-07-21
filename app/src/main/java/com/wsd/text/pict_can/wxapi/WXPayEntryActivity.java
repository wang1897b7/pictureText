package com.wsd.text.pict_can.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wsd.text.pict_can.MyApplication;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.otto.BusProvider;
import com.wsd.text.pict_can.otto.RefreshOtto;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String ACTION_PAY_RESULT = "com.superwan.chaojiwan.wxapi.WXPayEntryActivity.PAYRESULT";
    public static final String EXTRA_PAY_RESULT = "pay_result";
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, MyApplication.WX_APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int result = 5;
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            result = resp.errCode;
        }
        if (resp.errCode == -2) {//cancel
            finish();
            return;
        }
        Intent intent = new Intent(ACTION_PAY_RESULT);
        intent.putExtra(EXTRA_PAY_RESULT, result);
        BusProvider.getInstance().post(new RefreshOtto(true));//PayActivity
        BusProvider.getInstance().post(new RefreshOtto(true));//RechargeActivity
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}