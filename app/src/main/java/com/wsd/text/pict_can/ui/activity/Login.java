package com.wsd.text.pict_can.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.MainActivity;

public class Login extends Activity {

    private EditText et_user;
    private EditText et_pass;
    private Button btn_sub;
    private TextView tv_findPass;
    private TextView tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent =new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        init();
    }

    private void init(){
        et_pass= (EditText) findViewById(R.id.login_pass);
        et_user= (EditText) findViewById(R.id.login_user);

        tv_findPass= (TextView) findViewById(R.id.login_tvFindPass);
        tv_register= (TextView) findViewById(R.id.login_tvRegister);
        btn_sub= (Button) findViewById(R.id.login_btn);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Login.this,Register.class);
                startActivity(intent);
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
