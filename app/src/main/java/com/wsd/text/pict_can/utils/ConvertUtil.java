package com.wsd.text.pict_can.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Libery on 2016/8/28.
 * Email:libery.szq@qq.com
 */
public class ConvertUtil {

    public static int stringToInt(String str) {
        if (CheckUtil.isNotEmpty(str)) {
            int i;
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                i = 0;
                e.printStackTrace();
            }
            return i;
        } else {
            return 0;
        }
    }

    public static double stringToDouble(String str) {
        if (CheckUtil.isNotEmpty(str)) {
            double i;
            try {
                i = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                i = 0;
                e.printStackTrace();
            }
            return i;
        } else {
            return 0;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;

    }
}
