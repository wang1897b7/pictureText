package com.wsd.text.pict_can.api.subscribers;


import com.wsd.text.pict_can.BuildConfig;
import com.wsd.text.pict_can.MyApplication;
import com.wsd.text.pict_can.model.Result;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Libery on 2016/3/28.
 * Email:libery.szq@qq.com
 */
public class Api {

    public static final String BASE_DAILY_URL = "https://apptest.superwan.cn/msi/";
    public static final String BASE_RELEASE_URL = "https://app.superwan.cn/msi/";
    public static final String SIGN = "qPF1SaAOycVN4v1G8sKtciw1WoQgU4p2";

    public String getBaseUrl() {
        if (BuildConfig.FLAVOR.equals("dev")) {
            return BASE_DAILY_URL;
        } else if (BuildConfig.FLAVOR.equals("produce")) {
            return BASE_RELEASE_URL;
        } else if (BuildConfig.FLAVOR.equals("thirdParty")) {
            return BASE_RELEASE_URL;
        } else {
            return BASE_RELEASE_URL;
        }
    }

    private static final int DEFAULT_TIMEOUT = 15;
    public static final int HTTP_OK = 200;

    private ApiService apiService;

    //构造方法私有
    private Api() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                final String PHPSESSID;
                if (MyApplication.getInstance() != null && MyApplication.session != null) {
                    PHPSESSID = MyApplication.session;
                } else {
                    PHPSESSID = "";
                }

                Headers headers = new Headers.Builder()
                        .add("Cookie", "PHPSESSID=" + PHPSESSID)
                        .add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .add("User-Agent", "Test")
                        .build();
                Request request = chain.request()
                        .newBuilder()
                        .headers(headers)
                        .build();

                Response response = chain.proceed(request);
               /* int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    response = chain.proceed(request);
                    tryCount++;
                    LogUtil.e("tryCount", tryCount + "");
                }*/
                return response;
            }
        };

        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws
                    CertificateException {

            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws
                    CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        TrustManager[] trustAllCerts = new TrustManager[]{trustManager};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory(), trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        });

        builder.addInterceptor(interceptor);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
        apiService = retrofit.create(ApiService.class);

    }


    //在访问Api时创建单例
    private static class SingleApi {
        private static final Api INSTANCE = new Api();
    }

    //获取单例
    public static Api getInstance() {
        return SingleApi.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的status,并将HttpResult的业务数据部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型
     */
    private static class HttpResultFunc<T extends Result> implements Func1<T, T> {
        @Override
        public T call(final T t) {
            if (t.status != HTTP_OK) {
                throw new ApiException(t.status, t.message);
            } else {
                return t;
            }
        }
    }


    /**
     * 返回结果只有Result
     * 用来统一处理Http的status
     */
    private static class HttpResultFunc2 implements Func1<Result, Boolean> {

        @Override
        public Boolean call(final Result result) {
            if (result.status == HTTP_OK) {
                return true;
            } else {
                throw new ApiException(result.status, result.message);
            }
        }

    }
    //--------------------------------------------homeApi---------------------------------------------------------------
/*
    public void getHomeAllInfo(Subscriber<HomeAllInfo> subscriber, String area_id) {
        Observable<HomeAllInfo> observable = apiService.getHomeAllInfo(area_id).map(new HttpResultFunc<HomeAllInfo>());
        toSubscribe(observable, subscriber);
    }

    public void getAreaInfo(Subscriber<AreaAll> subscriber) {
        Observable<AreaAll> observable = apiService.getAreaInfo().map(new HttpResultFunc<AreaAll>());
        toSubscribe(observable, subscriber);
    }

    public void scanBarcode(Subscriber<ScanCode> subscriber, String area_id, String code) {
        Observable<ScanCode> observable = apiService.scanBarcode(area_id, code).map(new HttpResultFunc<ScanCode>());
        toSubscribe(observable, subscriber);
    }

    public void scanPreOrder(Subscriber<BillConfirm> subscriber, String area_id, String preorder_id) {
        Observable<BillConfirm> observable = apiService.scanPreOrder(area_id, preorder_id).map(new
                HttpResultFunc<BillConfirm>());
        toSubscribe(observable, subscriber);
    }
    //-----------------------------------------------expoApi------------------------------------------------------------

    public void getExpoList(Subscriber<Expo> subscriber, String area_id, String type) {
        Observable<Expo> observable = apiService.getExpoList(area_id, type).map(new
                HttpResultFunc<Expo>());
        toSubscribe(observable, subscriber);
    }

    public void getExpoDetail(Subscriber<ExpoDetail> subscriber, String expo_id) {
        Observable<ExpoDetail> observable = apiService.getExpoDetail(expo_id).map(new
                HttpResultFunc<ExpoDetail>());
        toSubscribe(observable, subscriber);
    }

    public void getProductList(Subscriber<MarketProduct> subscriber, String expo_id, String cate_id, String keyword,
                               String order_by, PageBean pageBean) {
        Observable<MarketProduct> observable = apiService.getExpoProductList(expo_id, cate_id, keyword, order_by,
                pageBean.getCurrent()).map(new HttpResultFunc<MarketProduct>());
        toSubscribe(observable, subscriber);
    }

    public void getCategoryList(Subscriber<ExpoCategory> subscriber, String expo_id) {
        Observable<ExpoCategory> observable = apiService.getExpoCategoryList(expo_id).map(new
                HttpResultFunc<ExpoCategory>());
        toSubscribe(observable, subscriber);
    }

    public void getPrizeList(Subscriber<ExpoPrize> subscriber, String expo_id) {
        Observable<ExpoPrize> observable = apiService.getPrizeList(expo_id).map(new
                HttpResultFunc<ExpoPrize>());
        toSubscribe(observable, subscriber);
    }

    public void signupExpo(Subscriber<ExpoSignUp> subscriber, String expo_id, String shipping_id, String shop_id) {
        Observable<ExpoSignUp> observable = apiService.signupExpo(expo_id, shipping_id, shop_id).map(new
                HttpResultFunc<ExpoSignUp>());
        toSubscribe(observable, subscriber);
    }

    public void bindTicket(Subscriber<ExpoDetail> subscriber, String area_id, String code) {
        Observable<ExpoDetail> observable = apiService.bindTicket(area_id, code).map(new
                HttpResultFunc<ExpoDetail>());
        toSubscribe(observable, subscriber);
    }

    public void selectBrand(Subscriber<Shop> subscriber, String expo_id) {
        Observable<Shop> observable = apiService.selectBrand(expo_id).map(new
                HttpResultFunc<Shop>());
        toSubscribe(observable, subscriber);
    }
    //---------------------------------------------userApi--------------------------------------------------------------

    //2.14	系统-上传一个图片文件
    public void getUploadImage(Subscriber<ImageItem> subscriber, String Filedata) {
        Observable<ImageItem> observable = apiService.getUploadImage(Filedata).map(new
                HttpResultFunc<ImageItem>());
        toSubscribe(observable, subscriber);
    }

    //  2.15	系统-送货地址区县的选择
    public void getAreaList(Subscriber<ProvinceAddress> subscriber, String area_id) {
        Observable<ProvinceAddress> observable = apiService.getAreaList(area_id).map(new
                HttpResultFunc<ProvinceAddress>());
        toSubscribe(observable, subscriber);
    }

    public void modifyPwd(Subscriber<Boolean> subscriber, String login_pwd, String new_pwd) {
        Observable<Boolean> observable = apiService.modifyPwd(Md5Util.md5_encrypt(login_pwd), Md5Util.md5_encrypt
                (new_pwd)).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void reSetPhone(Subscriber<Boolean> subscriber, String phone, String checkCode) {
        Observable<Boolean> observable = apiService.reSetPhone(phone, checkCode).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void resetPwd(Subscriber<Boolean> subscriber, String phone, String checkCode, String pass) {
        Observable<Boolean> observable = apiService.resetPwd(phone, checkCode, pass).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void getP2CheckCode(Subscriber<Boolean> subscriber, String phone, String checkCode) {
        Observable<Boolean> observable = apiService.getP2CheckCode(phone, checkCode).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void getCheckCodeWithSession(Subscriber<Result> subscriber) {
        Observable<Result> observable = apiService.getCheckCodeWithSession().map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    public void getCheckCode(Subscriber<Result> subscriber, String phone) {
        Observable<Result> observable = apiService.getCheckCode(phone, Md5Util.md5_encrypt(phone + SIGN)).map(new
                HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    public void register(Subscriber<User> subscriber, String phone, String password, String code, String sale_id,
                         String device) {
        Observable<User> observable = apiService.register(phone, password, code, sale_id, device).map(new
                HttpResultFunc<User>());
        toSubscribe(observable, subscriber);
    }

    public void getVersion(Subscriber<Version> subscriber) {
        Observable<Version> observable = apiService.getVersion("A").map(new HttpResultFunc<Version>());
        toSubscribe(observable, subscriber);
    }

    public void loginUser(Subscriber<User> subscriber, String username, String password, String device) {
        Observable<User> observable = apiService.loginUser(username, Md5Util.md5_encrypt(password), device).map(new
                HttpResultFunc<User>());
        toSubscribe(observable, subscriber);
    }

    //2.80	我的中心-个人中心首页
    public void getPersonalInfo(Subscriber<PersonalInfo> subscriber, String area_id) {
        Observable<PersonalInfo> observable = apiService.getPersonalInfo(area_id).map(new
                HttpResultFunc<PersonalInfo>());
        toSubscribe(observable, subscriber);
    }

    // 2.81	我的中心-收货地址列表
    public void getAddressList(Subscriber<Address> subscriber) {
        Observable<Address> observable = apiService.getAddressList().map(new HttpResultFunc<Address>());
        toSubscribe(observable, subscriber);
    }

    //2.82	我的中心-新增/编辑收货地址
    public void orderAddress(Subscriber<Address> subscriber, String shippingId, String contact,
                             String phone, String address, String province, String city, String county) {
        Observable<Address> observable = apiService.orderAddress(shippingId, contact, phone, address, province, city,
                county).map(new HttpResultFunc<Address>());
        toSubscribe(observable, subscriber);
    }

    //2.83	我的中心-删除收货地址
    public void delAddress(Subscriber<Result> subscriber, String shippingId) {
        Observable<Result> observable = apiService.delAddress(shippingId).map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    //2.84	我的中心-个人信息编辑
    public void editUser(Subscriber<Result> subscriber, String name, String face) {
        Observable<Result> observable = apiService.editUser(name, face).map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    //2.85	我的中心-我的关注
    public void getMyFollowList(Subscriber<FocusList> subscriber) {
        Observable<FocusList> observable = apiService.getMyFollowList().map(new HttpResultFunc<FocusList>());
        toSubscribe(observable, subscriber);
    }

    //2.86	我的中心-我的浏览记录
    public void getMyHistoryList(Subscriber<FocusList> subscriber) {
        Observable<FocusList> observable = apiService.getMyHistoryList().map(new HttpResultFunc<FocusList>());
        toSubscribe(observable, subscriber);
    }

    //2.87	我的中心-我的优惠券
    public void getMyCouponList(Subscriber<Coupon> subscriber,String area_id) {
        Observable<Coupon> observable = apiService.getMyCouponList(area_id).map(new HttpResultFunc<Coupon>());
        toSubscribe(observable, subscriber);
    }

    //2.88	我的中心-我的家博会列表
    public void getMyExpoList(Subscriber<Expo> subscriber) {
        Observable<Expo> observable = apiService.getMyExpoList().map(new HttpResultFunc<Expo>());
        toSubscribe(observable, subscriber);
    }

    // 2.89	我的中心-提现接收帐号列表
    public void getPayList(Subscriber<PayItem> subscriber) {
        Observable<PayItem> observable = apiService.getPayList().map(new HttpResultFunc<PayItem>());
        toSubscribe(observable, subscriber);
    }

    //2.90	我的中心-提现申请
    public void getWithdraw(Subscriber<Result> subscriber, String account_id) {
        Observable<Result> observable = apiService.getWithdraw(account_id).map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    //2.91	我的中心-我的账户收支明细
    public void getMyAccountList(Subscriber<Account> subscriber, String page_num, String trans_type) {
        Observable<Account> observable = apiService.getMyAccountList(page_num, trans_type).map(new
                HttpResultFunc<Account>());
        toSubscribe(observable, subscriber);
    }

    //2.92	我的中心-意见与反馈
    public void getFeedBack(Subscriber<Result> subscriber, String remark, String pic) {
        Observable<Result> observable = apiService.postFeedBack(remark, pic).map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);
    }

    // 2.93	我的中心-系统通知消息
    public void getMyMessageList(Subscriber<Message> subscriber, String page_num) {
        Observable<Message> observable = apiService.getMyMessageList(page_num).map(new HttpResultFunc<Message>());
        toSubscribe(observable, subscriber);
    }

    // 2.96	我的中心-服务中心-留言列表
    public void serviceChat(Subscriber<Chat> subscriber, String page_num) {

        Observable<Chat> observable = apiService.serviceChat(page_num).map(new HttpResultFunc<Chat>());
        toSubscribe(observable, subscriber);
    }

    //2.97	我的中心-服务中心-提交留言
    public void sendChat(Subscriber<Result> subscriber, String content, String pic, String pic_thumb) {
        Observable<Result> observable = apiService.sendChat(content, pic, pic_thumb).map(new HttpResultFunc<>());
        toSubscribe(observable, subscriber);

    }

    //2.98	腕友圈-首页
    public void getMoments(Subscriber<Moments> subscriber, String area_id) {
        Observable<Moments> observable = apiService.getMoments(area_id).map(new HttpResultFunc<Moments>());
        toSubscribe(observable, subscriber);
    }
    //-----------------------------------------------marketApi----------------------------------------------------------

    public void getMarketCategoryList(Subscriber<MarketCategoryAll> subscriber, String area_id) {
        Observable<MarketCategoryAll> observable = apiService.getMarketCategoryList(area_id).map(new
                HttpResultFunc<MarketCategoryAll>());
        toSubscribe(observable, subscriber);
    }

    public void getMarketProductList(Subscriber<MarketProductList> subscriber, String area_id, String cat_id,
                                     String prop_value, String order_by, PageBean pageBean) {
        Observable<MarketProductList> observable = apiService.getMarketProductList(area_id, cat_id, prop_value,
                order_by, pageBean.getCurrent()).map(new HttpResultFunc<MarketProductList>());
        toSubscribe(observable, subscriber);
    }

    public void getShopList(Subscriber<MarketShop> subscriber, String area_id, String cat_id,
                            String prop_value, PageBean pageBean) {
        Observable<MarketShop> observable = apiService.getShopList(area_id, cat_id, prop_value, pageBean.getCurrent()
        ).map(new HttpResultFunc<MarketShop>());
        toSubscribe(observable, subscriber);
    }

    public void searchProductList(Subscriber<MarketProductList> subscriber, String keyword, String area_id, String
            order_by, PageBean pageBean) {
        Observable<MarketProductList> observable = apiService.searchProductList(keyword, area_id, order_by, pageBean
                .getCurrent()
        ).map(new HttpResultFunc<MarketProductList>());
        toSubscribe(observable, subscriber);
    }

    public void searchShopList(Subscriber<MarketShop> subscriber, String area_id, String keyword, PageBean pageBean) {
        Observable<MarketShop> observable = apiService.searchShopList(area_id, keyword, pageBean.getCurrent())
                .map(new HttpResultFunc<MarketShop>());
        toSubscribe(observable, subscriber);
    }

    public void getOnSaleCategoryList(Subscriber<MarketCategoryAll> subscriber, String area_id) {
        Observable<MarketCategoryAll> observable = apiService.getOnSaleCategoryList(area_id)
                .map(new HttpResultFunc<MarketCategoryAll>());
        toSubscribe(observable, subscriber);
    }

    public void getOnSaleProductList(Subscriber<MarketProduct> subscriber, String area_id, String cat_id, PageBean
            pageBean) {
        Observable<MarketProduct> observable = apiService.getOnSaleProductList(area_id, cat_id, pageBean.getCurrent())
                .map(new HttpResultFunc<MarketProduct>());
        toSubscribe(observable, subscriber);
    }

    public void getShopInfo(Subscriber<MarketShop> subscriber, String shop_id) {
        Observable<MarketShop> observable = apiService.getShopInfo(shop_id).map(new HttpResultFunc<MarketShop>());
        toSubscribe(observable, subscriber);
    }

    public void getShopCategory(Subscriber<ExpoCategory> subscriber, String shop_id) {
        Observable<ExpoCategory> observable = apiService.getShopCategory(shop_id).map(new
                HttpResultFunc<ExpoCategory>());
        toSubscribe(observable, subscriber);
    }

    public void getShopProductList(Subscriber<MarketProduct> subscriber, String shop_id, String cat_id, String
            keywords, String order_by, PageBean pageBean) {
        Observable<MarketProduct> observable = apiService.getShopProductList(shop_id, cat_id, keywords, order_by,
                pageBean.getCurrent()).map(new HttpResultFunc<MarketProduct>());
        toSubscribe(observable, subscriber);
    }

    public void getFrontShopList(Subscriber<MarketFrontShop> subscriber, String shop_id) {
        Observable<MarketFrontShop> observable = apiService.getFrontShopList(shop_id).map(new
                HttpResultFunc<MarketFrontShop>());
        toSubscribe(observable, subscriber);
    }

    public void getProductDetail(Subscriber<MarketProductDetail> subscriber, String sku_id) {
        Observable<MarketProductDetail> observable = apiService.getProductDetail(sku_id).map(new
                HttpResultFunc<MarketProductDetail>());
        toSubscribe(observable, subscriber);
    }

    public void getCommentList(Subscriber<MarketCommentList> subscriber, String prod_id, String comment_type,
                               PageBean mPageBean) {
        Observable<MarketCommentList> observable = apiService.getCommentList(prod_id, comment_type, mPageBean
                .getCurrent()).map(new HttpResultFunc<MarketCommentList>());
        toSubscribe(observable, subscriber);
    }

    public void focusProduct(Subscriber<Boolean> subscriber, String sku_id) {
        Observable<Boolean> observable = apiService.focusProduct(sku_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void cancelFocusProduct(Subscriber<Boolean> subscriber, String sku_id) {
        Observable<Boolean> observable = apiService.cancelFocusProduct(sku_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void focusShop(Subscriber<Boolean> subscriber, String shop_id) {
        Observable<Boolean> observable = apiService.focusShop(shop_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void cancelFocusShop(Subscriber<Boolean> subscriber, String shop_id) {
        Observable<Boolean> observable = apiService.cancelFocusShop(shop_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void getCoupon(Subscriber<Boolean> subscriber, String coupon_id) {
        Observable<Boolean> observable = apiService.getCoupon(coupon_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void getPreOrderList(Subscriber<ProductPreOrder> subscriber, String shop_id, String sku_id) {
        Observable<ProductPreOrder> observable = apiService.getPreOrderList(shop_id, sku_id).map(new
                HttpResultFunc<ProductPreOrder>());
        toSubscribe(observable, subscriber);
    }
    //-------------------------------------------BillApi----------------------------------------------------------------


    public void getCarList(Subscriber<MarketShop> subscriber, PageBean mPageBean) {
        Observable<MarketShop> observable = apiService.getCarList(mPageBean.getCurrent()).map(new
                HttpResultFunc<MarketShop>());
        toSubscribe(observable, subscriber);
    }

    public void addCarProduct(Subscriber<Boolean> subscriber, String sku_id, String quantity) {
        Observable<Boolean> observable = apiService.addCarProduct(sku_id, quantity).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void deleteCarProduct(Subscriber<Boolean> subscriber, String sku_id) {
        Observable<Boolean> observable = apiService.deleteCarProduct(sku_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void confirmBill(Subscriber<BillConfirm> subscriber, String shop_id, String order_type) {
        Observable<BillConfirm> observable = apiService.confirmBill(shop_id, order_type).map(new
                HttpResultFunc<BillConfirm>());
        toSubscribe(observable, subscriber);
    }

    public void submitBill(Subscriber<Bill> subscriber, String shipping_id, String preorder_id, String order_info) {
        Observable<Bill> observable = apiService.submitBill(shipping_id, preorder_id, order_info).map(new
                HttpResultFunc<Bill>());
        toSubscribe(observable, subscriber);
    }

    public void submitQuick(Subscriber<Bill> subscriber, String shipping_id, String order_info) {
        Observable<Bill> observable = apiService.submitQuick(shipping_id, order_info).map(new HttpResultFunc<Bill>());
        toSubscribe(observable, subscriber);
    }

    public void getBillList(Subscriber<Bill> subscriber, String status, PageBean pageBean) {
        Observable<Bill> observable = apiService.getBillList(status, pageBean.getCurrent()).map(new
                HttpResultFunc<Bill>());
        toSubscribe(observable, subscriber);
    }

    public void getBookingBillList(Subscriber<BookingBill> subscriber, PageBean pageBean) {
        Observable<BookingBill> observable = apiService.getBookingBillList(pageBean.getCurrent()).map(new
                HttpResultFunc<BookingBill>());
        toSubscribe(observable, subscriber);
    }

    public void getBillInfo(Subscriber<BillInfo> subscriber, String order_id) {
        Observable<BillInfo> observable = apiService.getBillInfo(order_id).map(new HttpResultFunc<BillInfo>());
        toSubscribe(observable, subscriber);
    }

    public void confirmReceive(Subscriber<Boolean> subscriber, String order_id) {
        Observable<Boolean> observable = apiService.confirmReceive(order_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void cancelBill(Subscriber<Boolean> subscriber, String order_id) {
        Observable<Boolean> observable = apiService.cancelBill(order_id).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void comment(Subscriber<Boolean> subscriber, String order_id, String sku_id, String score, String remark,
                        String pic, String thumbnail) {
        Observable<Boolean> observable = apiService.comment(order_id, sku_id, score, remark, pic, thumbnail).map(new
                HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void prepareAlipay(Subscriber<AliPay> subscriber, String type, String shop_id, String order_ids, String
            pay_money, String acc_money, String shipping_id) {
        Observable<AliPay> observable = apiService.prepareAlipay(type, shop_id, order_ids, pay_money, acc_money,
                shipping_id).map(new HttpResultFunc<AliPay>());
        toSubscribe(observable, subscriber);
    }

    public void alipaySuccess(Subscriber<Boolean> subscriber, String status, String result) {
        Observable<Boolean> observable = apiService.alipaySuccess(status, result).map(new HttpResultFunc2());
        toSubscribe(observable, subscriber);
    }

    public void prepareWechat(Subscriber<WechatPay> subscriber, String type, String shop_id, String order_ids, String
            pay_money, String acc_money, String shipping_id) {
        Observable<WechatPay> observable = apiService.prepareWechat(type, shop_id, order_ids, pay_money, acc_money,
                shipping_id).map(new HttpResultFunc<WechatPay>());
        toSubscribe(observable, subscriber);
    }

    public void accountSuccess(Subscriber<AccountPay> subscriber, String type, String shop_id, String order_ids,
                               String acc_money, String shipping_id) {
        Observable<AccountPay> observable = apiService.accountSuccess(type, shop_id, order_ids, acc_money,
                shipping_id).map(new HttpResultFunc<AccountPay>());
        toSubscribe(observable, subscriber);
    }

    public void prepareBookingPay(Subscriber<BookingPay> subscriber, String expo_id, String shop_id) {
        Observable<BookingPay> observable = apiService.prepareBookingPay(expo_id, shop_id).map(new
                HttpResultFunc<BookingPay>());
        toSubscribe(observable, subscriber);
    }

    public void getBalance(Subscriber<Balance> subscriber, String shop_id, String expo_id) {
        Observable<Balance> observable = apiService.getBalance(shop_id, expo_id).map(new HttpResultFunc<Balance>());
        toSubscribe(observable, subscriber);
    }

    public void getGift(Subscriber<GiftCode> subscriber, String order_id, String type, String gift_id) {
        Observable<GiftCode> observable = apiService.getGift(order_id, type, gift_id).map(new
                HttpResultFunc<GiftCode>());
        toSubscribe(observable, subscriber);
    }

    public void getGiftList(Subscriber<GiftList> subscriber, String order_id, String type) {
        Observable<GiftList> observable = apiService.getGiftList(order_id, type).map(new HttpResultFunc<GiftList>());
        toSubscribe(observable, subscriber);
    }*/
}