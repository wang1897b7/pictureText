package com.wsd.text.pict_can.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.wsd.text.pict_can.MyApplication;

import java.text.MessageFormat;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author Shaojie
 * @date 2015-06-29 23:12:24
 */
public class ToastUtil {

    public static void show(Activity activity, String message) {
        show(activity, message, LENGTH_SHORT);
    }

    public static void show(Activity activity, int resId) {
        if (activity == null) {
            return;
        }
        show(activity, activity.getString(resId));
    }

    public static void showAtUI(String message) {
        showAtUI(MyApplication.getInstance(), message, LENGTH_SHORT);
    }

    public static void showAtUI(@StringRes int resId) {
        showAtUI(MyApplication.getInstance(), resId, LENGTH_SHORT);
    }

    public static void showAtUI(Context context, String message) {
        showAtUI(context, message, LENGTH_SHORT);
    }

    public static void showAtUI(Context context, @StringRes int resId) {
        showAtUI(context, resId, LENGTH_SHORT);
    }

    public static void showLong(Activity activity, @StringRes int resId) {
        show(activity, resId, LENGTH_LONG);
    }

    public static void showLong(Activity activity, String message) {
        show(activity, message, LENGTH_LONG);
    }

    public static void showLong(Activity activity, String message, Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_LONG);
    }

    public static void showShort(Activity activity, String message, Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_SHORT);
    }

    public static void showLong(Activity activity, int resId, Object... args) {
        if (activity == null) {
            return;
        }
        String message = activity.getString(resId);
        showLong(activity, message, args);
    }

    public static void showShort(Activity activity, @StringRes int resId, Object... args) {
        if (activity == null) {
            return;
        }
        String message = activity.getString(resId);
        showShort(activity, message, args);
    }

    private static void show(Activity activity, @StringRes final int resId, final int duration) {
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, resId, duration).show();
            }
        });
    }

    private static void show(Activity activity, final String message, final int duration) {
        if ((activity == null) || (message == null)) {
            return;
        }
        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });
    }

    private static void showAtUI(Context context, String message, int duration) {
        if ((context == null) || (message == null)) {
            return;
        }
        Toast.makeText(context, message, duration).show();
    }

    private static void showAtUI(Context context, @StringRes int resId, int duration) {
        if ((context == null) || (resId == 0)) {
            return;
        }
        Toast.makeText(context, resId, duration).show();
    }

}
