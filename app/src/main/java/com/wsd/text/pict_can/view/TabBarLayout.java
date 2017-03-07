package com.wsd.text.pict_can.view;

//public class TabBarLayout extends LinearLayout {

//    private static final int SIZE = 5;
//
//    private ViewPager mViewPager;
//
//    public TabBarLayout(Context context) {
//        this(context, null);
//    }
//
//    public TabBarLayout(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public void setViewPager(ViewPager viewPager) {
//        removeAllViews();
//        mViewPager = viewPager;
//        if (viewPager != null && viewPager.getAdapter() != null) {
//            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
//            initTabLayout();
//        }
//    }
//
//    private void initTabLayout() {
//        OnClickListener tabClickListener = new TabClickListener();
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        // 首页
//        View tabView0 = inflater.inflate(R.layout.tabbar_item_home, this, false);
//        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
//        lp.width = 0;
//        lp.weight = 1;
//        tabView0.setOnClickListener(tabClickListener);
//        addView(tabView0, lp);
//        tabView0.findViewById(R.id.main_bottom_tab_icon).setSelected(true);// 默认选中
//        tabView0.findViewById(R.id.main_bottom_tab_text).setSelected(true);
//        //顾客
//        View tabView1 = inflater.inflate(R.layout.tabbar_item_customer, this, false);
//        LayoutParams lp1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
//        lp1.width = 0;
//        lp1.weight = 1;
//        tabView1.setOnClickListener(tabClickListener);
//        addView(tabView1, lp1);
//        // 订单
//        View tabView2 = inflater.inflate(R.layout.tabbar_item_order, this, false);
//        LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
//        lp2.width = 0;
//        lp2.weight = 1;
//        tabView2.setOnClickListener(tabClickListener);
//        addView(tabView2, lp2);
//        // 管理
//        View tabView3 = inflater.inflate(R.layout.tabbar_item_management, this, false);
//        LayoutParams lp3 = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
//        lp3.width = 0;
//        lp3.weight = 1;
//        tabView3.setOnClickListener(tabClickListener);
//        addView(tabView3, lp3);
//        // 我
//        View tabView4 = inflater.inflate(R.layout.tabbar_item_my, this, false);
//        LayoutParams lp4 = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
//        lp4.width = 0;
//        lp4.weight = 1;
//        tabView4.setOnClickListener(tabClickListener);
//        addView(tabView4, lp4);
//
//    }
//
///*    public void showMsgDot(int tabPosition, int msgCount) {
//        if (tabPosition < 0 || tabPosition >= SIZE) {
//            return;
//        }
//        View tabView = getChildAt(tabPosition);
//        TextView dotText = (TextView) tabView.findViewById(R.id.bottom_tab_dot_text);
//        if (dotText == null) {
//            return;
//        }
//        if (msgCount > 0) {
//            String tips = (msgCount <= 99) ? String.valueOf(msgCount) : "...";
//            dotText.setVisibility(VISIBLE);
//            dotText.setText(tips);
//        } else {
//            dotText.setVisibility(GONE);
//        }
//    }
//
//    public void updateTitle(int position, String title) {
//        if (position >= SIZE) {
//            return;
//        }
//        View view = getChildAt(position);
//        TextView tv = (TextView) view.findViewById(R.id.main_bottom_tab_text);
//        tv.setText(title);
//    }*/
//
//    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            for (int i = 0; i < SIZE; i++) {
//                View tabView = getChildAt(i);
//                tabView.findViewById(R.id.main_bottom_tab_icon).setSelected(position == i);
//                tabView.findViewById(R.id.main_bottom_tab_text).setSelected(position == i);
//            }
//            if (mOnTabSelectedListener != null) {
//                mOnTabSelectedListener.onSelected(position);
//            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//        }
//    }
//
//    private class TabClickListener implements OnClickListener {
//        @Override
//        public void onClick(View v) {
//            for (int i = 0; i < getChildCount(); i++) {
//                if (v == getChildAt(i)) {
//                    mViewPager.setCurrentItem(i, false);
//                    return;
//                }
//            }
//        }
//    }
//
//    private OnTabSelectedListener mOnTabSelectedListener;
//
//    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
//        mOnTabSelectedListener = listener;
//    }
//
//    public interface OnTabSelectedListener {
//        void onSelected(int selectedPosition);
//    }

//}
