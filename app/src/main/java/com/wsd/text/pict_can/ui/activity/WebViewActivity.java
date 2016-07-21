package com.wsd.text.pict_can.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wsd.text.pict_can.R;

import java.net.URLDecoder;

public class WebViewActivity extends AppCompatActivity {

    private TextView tv_data;
    private WebView webview;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        tv_data=(TextView) findViewById(R.id.tv_text);
        webview=(WebView) findViewById(R.id.web_te);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadData("", "text/html", null);
        // <a onClick="window.demo.clickOnAndroid()"> demo 是程序实例的意思，基于windows开发的demo都可以运行的
        //demo 是服务器端的class文件名 用于绑定webview 的js方法
        webview.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");//文件地址和方法名
        webview.loadUrl("file:///android_asset/demo.html");
      //  webview.loadUrl("http://wap.baidu.com");
        webview.setWebViewClient(new HelloWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());


    }


    final class DemoJavaScriptInterface {


        DemoJavaScriptInterface(){}
        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        @JavascriptInterface
        public void clickOnAndroid() {
            Log.e("Webview_http", "clickAndroid");
            mHandler.post(new Runnable() {


                public void run() {
                    //Log.e("Webview_http", "javascript:wave()");
                    //跳转到联系人
                    //   	jumpToContacts();
//                	Log.e(TAG, "hhhhh"+phoneNum);
                    //      webview.loadUrl("javascript:wave("+1111111111+")");//本地js的function 方法名
                }
            });

        }

        @JavascriptInterface
        public void barCode(String js) {
            Log.e("Webview_http", "js++"+js);
            String  text = decode(js);
            Log.e("Webview_http", "clickOnBarcode"+text);
            mHandler.post(new Runnable() {


                public void run() {
                    //Log.e("Webview_http", "javascript:wave()");
                    // 	jumpToContacts();
//                 	Log.e(TAG, "hhhhh"+phoneNum);
//                	    webview.loadUrl("javascript:wave("+2222+")");
                    //  	 jumpBarcode();

                }
            });

        }
    }
    public static String decode(String s) {
        if (s == null) {
            return null;
        }
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (Exception e) {
            // utf-8 always available
            e.printStackTrace();
        }
        return null;
    }







    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        //捕获点击事件
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            // 重新设置websettings
           WebSettings s = webview.getSettings();
            s.setBuiltInZoomControls(true);
            s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            s.setUseWideViewPort(true);
            s.setLoadWithOverviewMode(true);
//			 s.setSavePassword(true); s.setSaveFormData(true);
           s.setJavaScriptEnabled(true); // 设置支持javascript脚本
            s.setGeolocationEnabled(true);
            s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
            // enable Web Storage: localStorage, sessionStorage
            s.setDomStorageEnabled(true);
            s.setSavePassword(false);//关闭webview 记住密码   不然报: Unable to add window
            s.setSaveFormData(false);
			// webview.requestFocus();
//			 webview.setScrollBarStyle(0);
			 view.loadUrl(url);
            return true;
        }



        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {



        }



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            view.loadUrl("javascript:clickOnBarcode1()");

//           view.evaluateJavascript("clickOnBarcode1()", new ValueCallback<String>() {
//               @Override
//               public void onReceiveValue(String s) {
//                   Log.i("String","json:"+s);
//               }
//
//           });

        }


    }



    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * debugging your javascript.
     */
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.e("Webview_http","alert++"+ result);
            Log.e("Webview_http","MyWebChromeClient:"+ view.getTitle());

            result.confirm();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {


            //Log.e(TAG, "title--"+title);

            //tv_top_title.setText(title);


        }


    }
}
