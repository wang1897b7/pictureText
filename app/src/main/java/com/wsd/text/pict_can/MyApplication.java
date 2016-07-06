package com.wsd.text.pict_can;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


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
        FlowManager.init(this);
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
