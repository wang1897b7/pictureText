package com.wsd.text.pict_can.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CheckUtil {

    /**
     * 去掉数组里的重复数据
     */
    public static void removeDuplicate(List arrayList) {
        HashSet h = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(h);
    }

    /**
     * 去掉数组里的重复数据，并保持原顺序
     */
    public static void removeDuplicateWithOrder(ArrayList arrayList) {
        HashSet set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = arrayList.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (!set.contains(element)) {
                set.add(element);
                newList.add(element);
            }
        }
        arrayList.clear();
        arrayList.addAll(newList);
    }

    public static JSONArray removeDuplicate(JSONArray jsonArray) {
        HashSet set = new HashSet();
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object element = jsonArray.get(i);
                if (!set.contains(element)) {
                    set.add(element);
                    newArray.put(element);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newArray;
    }

    public static boolean checkEmail(String email) {
        try {
            String check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))" +
                "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equals("null");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static void showLongMessage(Context mContext, CharSequence text) {
        if (text != null && text.length() > 0) {
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShortMessage(Context mContext, CharSequence text) {
        if (text != null && text.length() > 0) {
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 读取制定路径的文本内容
     *
     * @param filePath
     * @return String
     */
    public static String getFileContent(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sbContent = new StringBuffer();
        String sLine = "";

        while ((sLine = br.readLine()) != null) {
            String s = sLine.toString() + "\n";
            sbContent = sbContent.append(s);
        }

        fis.close();
        isr.close();
        br.close();

        return sbContent.toString();
    }

    public static String getFileContent(InputStream fis) throws IOException {
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sbContent = new StringBuffer();
        String sLine = "";

        while ((sLine = br.readLine()) != null) {
            String s = sLine.toString() + "\n";
            sbContent = sbContent.append(s);
        }

        isr.close();
        br.close();
        return sbContent.toString();
    }

    public static boolean putFileContent(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
        return false;
    }

    public static CharSequence getTextFromHtml(String content) {
        CharSequence text = "";
        if (isNotEmpty(content)) {
            text = Html.fromHtml(content);
        }
        return text;
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, float desWidth, float desHeight) {
        if (bitmap == null) {
            return null;
        }
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        if (bmpWidth > desWidth || bmpHeight > desHeight) {
            Matrix matrix = new Matrix();

            float scalFactor = Math.min(desWidth / bmpWidth, desHeight / bmpHeight);
            matrix.postScale(scalFactor, scalFactor);

            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
            bitmap.recycle();
            bitmap = resizeBitmap;
        }
        return bitmap;
    }

    public static String TimeStampToString(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date(timeStamp));
        return date;
    }

    public void stripUnderlines(TextView textView) {
        Spannable s = (Spannable) textView.getText();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    private class URLSpanNoUnderline extends URLSpan {

        public URLSpanNoUnderline(String url) {
            super(url);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }

    }

    public static String getStringEscapeHtml(String str) {
        if (isNotEmpty(str)) {
            str = str.replaceAll("<(img)[^>]+>", "[图片]");
            str = Html.fromHtml(str).toString();
        }
        return str;
    }

    public static String subStringEscapeHtml(String str, int length) {
        if (isNotEmpty(str)) {
            str = getStringEscapeHtml(str);
            if (str.length() > length)
                str = str.substring(0, length) + " ...";
        }
        return str;
    }

    /**
     * 过滤字符串里的tags对应的标签内�?
     */
    public static String escapeByTags(String s, String[] tags) {
        for (String re : tags) {
            Pattern p = Pattern
                    .compile("<[\\s]*?" + re + "[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?" + re + "[\\s]*?>", Pattern
							.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            s = m.replaceAll("");
        }
        return s;
    }

    // 清除多余的换行
    public static String fliterBR(String body) {
        if (body != null) {
            body = body.replaceAll("\n{2,}", "\n");
            body = body.replace("<br />\n<br />", "<br />");
            body = body.replace("<br /><br />", "<br />");
            body = body.replace("<br />\n\r<br />", "<br />");
            body = body.replace("<br />\r<br />", "<br />");
            body = body.trim();
            if (body.endsWith("<br />")) {
                body = body.substring(0, body.lastIndexOf("<br />"));
            }
        } else {
            body = "";
        }
        return body;
    }

    /**
     * 在屏幕中央显示loading
     */
    public static ProgressDialog showProgress(Context context) {
        ProgressDialog window = showProgress(context, "请稍候...");
        return window;
    }

    public static ProgressDialog showProgress(final Context context, String hintText) {
        ProgressDialog window = ProgressDialog.show(context, "", hintText);
        window.getWindow().setGravity(Gravity.CENTER);

        // 默认可取消的模式，并在取消之时�?知用户，线程仍在后台加载�?
        window.setCancelable(true);
        window.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
               // CheckUtil.showShortMessage(context, "自动转入后台...");
            }
        });
        return window;
    }

    // 根据内容，自动设置ListView的高度，解决ScrollView和ListView的滚动冲突问题
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    public static String getRealPathFromURI(Uri uri, Activity activity) {
        String path = null;
        if (uri.getScheme().equals("content")) {
            String[] proj = {MediaColumns.DATA};
            Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        } else {
            path = uri.getPath();
        }
        return path;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String text = writer.toString();
        return text;
    }

    public static void printLog(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void printLog(String msg) {
        Log.e("test", msg);
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 判断包含中文
    public static boolean isChineseStr(String pValue) {
        for (int i = 0; i < pValue.length(); i++) {
            if ((int) pValue.charAt(i) > 256)
                return true;
        }
        return false;
    }

    public static long getDirectorySize(File file, long totalSize) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    totalSize += files[i].length();
                } else {
                    getDirectorySize(files[i], totalSize);
                }
            }
        } else {
            totalSize += file.length();
        }
        return totalSize;
    }

    public static void deleteFileInDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    deleteFileInDirectory(files[i]);
                }
            }
        } else {
            file.delete();
        }
    }

    public static String displayFileSize(long fileSize) {
        // 取小数点后两位，注意除的是100.0，如是100则无法取得预期效果
        double num = 0;
        if (fileSize < 1024) {
            return fileSize + "B";
        } else if (fileSize < 1024 * 1024 && fileSize > 1024) {
            num = (int) (fileSize * 100 / 1024) / 100.0;
            return num + "KB";
        } else if (fileSize > 1024 * 1024) {
            num = (int) (fileSize * 100 / (1024 * 1024)) / 100.0;
            return num + "M";
        }
        return "";
    }

    public static Activity getActivityContext(Activity activity) {
        if (activity.getParent() != null) {
            if (activity.getParent().getParent() != null)
                return activity.getParent().getParent();
            return activity.getParent();
        } else {
            return activity;
        }
    }

    /**
     * 获得SD卡根目录
     */
    public static String getSDPath() {
        if (isSDPresent()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 应用程序文件在SD卡中的根目录
     */
    public static String getAppSDPath() {
        String path = getSDPath() + "/dxy/";
        File tmp = new File(path);
        if (!tmp.exists())
            tmp.mkdirs();
        return tmp.getAbsolutePath();
    }

    /**
     * sd卡中的cache目录
     */
    public static String getAppSDCacheDir() {
        String outfileDir = getAppSDPath() + File.separator + "cache";
        // 如果不存在，则建立个目录
        File tmp = new File(outfileDir);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        return tmp.getAbsolutePath();
    }

    /**
     * 检测SD卡状态
     */
    public static boolean isSDPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检测WIFI情况
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
				.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获得IP地址
     */
    public String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            CheckUtil.printLog("ip error", ex.toString());
        }
        return "";
    }

    public static String getStringFromCursor(Cursor cursor, String columnName) {
        String value = "";
        if (cursor != null && cursor.getPosition() >= 0 && cursor.getPosition() < cursor.getCount()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex >= 0) {
                value = cursor.getString(columnIndex);
            }
        }
        if (CheckUtil.isNotEmpty(value)) {
            value = value.trim();
            if (value.endsWith("\\r")) {
                value = value.substring(0, value.lastIndexOf("\\r"));
            }
        } else {
            value = "";
        }
        return value.trim();
    }

    public static int getIntFromCursor(Cursor cursor, String columnName) {
        int value = -1;
        if (cursor != null && cursor.getPosition() >= 0 && cursor.getPosition() < cursor.getCount()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex >= 0) {
                value = cursor.getInt(columnIndex);
            }
        }
        return value;
    }

    public static Long getLongFromCursor(Cursor cursor, String columnName) {
        Long value = -1L;
        if (cursor != null && cursor.getPosition() >= 0 && cursor.getPosition() < cursor.getCount()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex >= 0) {
                value = cursor.getLong(columnIndex);
            }
        }
        return value;
    }

    public static float getFloatFromCursor(Cursor cursor, String columnName) {
        float value = -1;
        if (cursor != null && cursor.getPosition() >= 0 && cursor.getPosition() < cursor.getCount()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex >= 0) {
                value = cursor.getFloat(columnIndex);
            }
        }
        return value;
    }

    /**
     * 检测是否联网
     */
    public static boolean isNetwork(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetInfo != null && activeNetInfo.isAvailable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sleep(long time) {
        try {
            Thread.currentThread();
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getSSIDLength(String ssid) {
        int count = 0;
        char[] chars = ssid.toCharArray();
        for (char c : chars) {
            if (isChinese(c)) {
                count += 3;
            } else {
                count++;
            }
        }
        return count;
    }

    private static boolean isChinese(char c) {
        if ((c >= 0x4e00) && (c <= 0x9fbb)) {
            return true;
        }
        return false;
    }

    public static void call(Context context, String number) {
        if (isNotEmpty(number)) {
            Intent phoneIntent = new Intent(
                    "android.intent.action.CALL", Uri.parse("tel:" + number));
            context.startActivity(phoneIntent);
        } else {
            Toast.makeText(context, "电话号码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

}
