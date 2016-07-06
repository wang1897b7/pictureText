package com.wsd.text.pict_can.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsd.text.pict_can.R;

import java.util.Random;

public class Register extends AppCompatActivity {

    private MyCount mCount;
    private TextView getCode;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=this;
        init();
    }

    private void init(){
        getCode= (TextView) findViewById(R.id.register_get_code_btn);
        EditText user= (EditText) findViewById(R.id.register_user);
        EditText pass= (EditText) findViewById(R.id.register_pass);

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCode();
                Log.e("text",""+createCode());

                startTimerTask();
                Toast.makeText(context,""+createCode(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startTimerTask(){
        if(mCount != null){
            mCount.onFinish();
        }
        mCount = new MyCount(60000,1000);
        mCount.start();
    }

    // 创建验证码
    public int createCode() {
        return new Random().nextInt(900000) + 100000;
    }
    /**
     * 定义一个倒计时的内部类
     */
    private class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
//            codeBtn.setBackgroundResource(R.drawable.bind_btn_code);
            getCode.setClickable(true);
            getCode.setText("获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setEnabled(false);
            getCode.setClickable(false);//防止重复点击
            getCode.setText(millisUntilFinished / 1000 + "秒后可重发");
//            codeBtn.setBackgroundResource(R.drawable.food_price_bg);
        }
    }
}
