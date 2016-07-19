package com.wsd.text.pict_can;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;

import com.wsd.text.pict_can.ui.activity.BaseActivity;
import com.wsd.text.pict_can.ui.fragment.HomeFragmemt;
import com.wsd.text.pict_can.ui.fragment.PersonalFragment;
import com.wsd.text.pict_can.ui.fragment.ShopFragment;

public class MainActivity extends BaseActivity {
    public static final int TYPE_HOME = 1;
    public static final int TYPE_SHOP = 2;
    public static final int TYPE_PERSONAL = 3;

    private FragmentManager fragmentManager;
    private HomeFragmemt homeFragment; //首页
    private ShopFragment expoFragment; //家博会
    private PersonalFragment carFragment; //购物车
    public int type = TYPE_HOME;
    public RadioButton marketBtn, expoBtn, homeBtn, carBtn;
    private Fragment currentFragment; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        fragmentManager = getSupportFragmentManager();
        homeBtn = (RadioButton) findViewById(R.id.main_tab_1);
        expoBtn = (RadioButton) findViewById(R.id.main_tab_2);
        carBtn = (RadioButton) findViewById(R.id.main_tab_3);


        homeBtn.setOnClickListener(clickListener);
        expoBtn.setOnClickListener(clickListener);
        carBtn.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.main_tab_1:
                    type = TYPE_HOME;
                    showHomeFragment();
                    break;
                case R.id.main_tab_3:
                    type = TYPE_PERSONAL;
                    showCategoryFragment();
                    break;
                case R.id.main_tab_2:
                    type = TYPE_SHOP;
                    showExpoFragment();
                    break;
            }
        }
    };


    public void showHomeFragment() {
        if (currentFragment instanceof HomeFragmemt) return;
        if (homeFragment == null) {
            homeFragment = new HomeFragmemt();
        }
        switchContent(homeFragment);
    }

    public void showExpoFragment() {
        if (currentFragment instanceof ShopFragment) return;
        if (expoFragment == null) {
            expoFragment = new ShopFragment();
        }
        switchContent(expoFragment);
    }

    public void showCategoryFragment() {
        if (currentFragment instanceof  PersonalFragment) return;
        if (carFragment == null) {
            carFragment = new PersonalFragment();
        }
        switchContent(carFragment);
    }
    private void switchContent(Fragment to) {
        fragmentManager.beginTransaction().replace(R.id.main_center, to).commit();
        currentFragment = to;
    }
}
