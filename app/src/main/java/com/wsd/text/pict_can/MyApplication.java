package com.wsd.text.pict_can;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.wsd.text.pict_can.component.pictureChose.listener.GlidePauseOnScrollListener;
import com.wsd.text.pict_can.component.pictureChose.loader.GlideImageLoader;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;


/**
 * @author zenghui.he
 * @date 2015-3-15
 */
public class MyApplication extends Application {

    public static final String TAG = "MyApplication";

    public static final String WX_APP_ID = "wxf2aa8fe2f9659781";

    public static Context mContext;
//    public static AppCache appCache;
//    public static UserCache userCache;
//    public static User user;
    public static String session;
    public static String areaCode = "";
    public static String cityName = "";

    public static List<String> searchKeywordsList;//搜索历史记录

    public static int unreadCarNum = 0;//购物车未读数量缓存
    public static boolean isOpened = false;

    public static final int CHOOSE_PICTURE_REQUEST_CODE = 1008;
    public static final int CHOOSE_PICTURE_REQUEST_CODE2 = 1009;
    public static final int CROP_PICTURE_REQUEST_CODE = 1010;
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        appCache = new AppCache(mContext);
//        userCache = new UserCache(mContext);
//        user = userCache.getUserInfo();
//        searchKeywordsList = appCache.getSearchKeywordsList();
//        if (searchKeywordsList == null) {
//            searchKeywordsList = new ArrayList<>();
//        }
//        areaCode = MyApplication.appCache.getAreaId();
//        cityName = MyApplication.appCache.getAreaName();
//        unreadCarNum = MyApplication.appCache.getCarNum();
//
//        SDKInitializer.initialize(getApplicationContext());
//        appImageLoaderConfig();

        //建议在application中配置
        //设置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
        FlowManager.init(this);

        initBuglyAndTinker();
    }


    private void initBuglyAndTinker() {
        Bugly.init(this, BuildConfig.BUGLY_ID,BuildConfig.DEBUG);
        Bugly.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }


    public static MyApplication getInstance() {
        if (null == mInstance) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }


    private void appImageLoaderConfig() {
    }

/*    public static String getAppVersion() {
        String version = "";
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            version = pi.versionName;
            if (AppUtil.isEmpty(version))
                version = "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }*/

    public static int getAppVersionCode() {
        int version = 0;
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            version = pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获得设备的UUID
     */
    public static String getUUID() {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider
                .Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        uniqueId = uniqueId.replace("-", "");
        return uniqueId;
    }

    /**
     * 添加Activity到集合中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exitAll() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

}
