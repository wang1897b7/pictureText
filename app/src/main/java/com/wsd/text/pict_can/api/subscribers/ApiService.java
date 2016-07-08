package com.wsd.text.pict_can.api.subscribers;

/**
 * Created by Libery on 2016/3/28.
 * Email:libery.szq@qq.com
 */
public interface ApiService {

   /* @GET("home.php")
    Observable<HomeAllInfo> getHomeAllInfo(@Query("area_id") String area_id);

    @GET("area.php")
    Observable<AreaAll> getAreaInfo();

    @GET("scan_barcode.php")
    Observable<ScanCode> scanBarcode(@Query("area_id") String area_id, @Query("code") String code);

    @GET("scan_preorder.php")
    Observable<BillConfirm> scanPreOrder(@Query("area_id") String area_id, @Query("preorder_id") String preorder_id);
    //--------------------------------------------------------expoApi---------------------------------------------------

    @GET("list_expo.php")
    Observable<Expo> getExpoList(@Query("area_id") String area_id, @Query("type") String type);

    @GET("expo.php")
    Observable<ExpoDetail> getExpoDetail(@Query("expo_id") String expo_id);

    @GET("list_expo_prod.php")
    Observable<MarketProduct> getExpoProductList(@Query("expo_id") String expo_id, @Query("cate_id") String cate_id,
                                                 @Query("keyword") String keyword, @Query("order_by") String order_by,
                                                 @Query("page_num") int page_num);

    @GET("list_expo_cat.php")
    Observable<ExpoCategory> getExpoCategoryList(@Query("expo_id") String expo_id);

    @GET("list_expo_prize.php")
    Observable<ExpoPrize> getPrizeList(@Query("expo_id") String expo_id);

    @GET("signup_expo.php")
    Observable<ExpoSignUp> signupExpo(@Query("expo_id") String expo_id, @Query("shipping_id") String shipping_id,
                                      @Query("shop_id") String shop_id);

    @GET("bind_ticket.php")
    Observable<ExpoDetail> bindTicket(@Query("area_id") String area_id, @Query("code") String code);

    @GET("list_expo_shop.php")
    Observable<Shop> selectBrand(@Query("expo_id") String expo_id);

    //----------------------------------------------UserApi-------------------------------------------------------------

    @GET("my_home.php")
    Observable<PersonalInfo> getPersonalInfo(@Query("area_id") String merchantId);

    @GET("list_shipping.php")
    Observable<Address> getAddressList();

    @GET("del_shipping.php")
    Observable<Result> delAddress(@Query("shipping_id") String shippingId);

    @GET("save_shipping.php")
    Observable<Address> orderAddress(@Query("shipping_id") String shippingId, @Query("contact") String contact,
                                     @Query("phone") String phone, @Query("address") String address,
                                     @Query("province") String province, @Query("city") String city,
                                     @Query("county") String county);

    @GET("community.php")
    Observable<Moments> getMoments(@Query("area_id") String merchantId);

    @GET("list_board_message.php")
    Observable<Chat> serviceChat(@Query("page_num") String page_num);

    @FormUrlEncoded
    @POST("save_board_message.php")
    Observable<Result> sendChat(@Field("content") String content, @Field("pic") String pic, @Field("pic_thumb")
    String pic_thumb);

    @GET("save_feedback.php")
    Observable<Result> postFeedBack(@Query("remark") String remark, @Query("pic") String pic);

    @GET("list_account_translog.php")
    Observable<Account> getMyAccountList(@Query("page_num") String page_num, @Query("trans_type") String trans_type);

    @GET("withdraw.php")
    Observable<Result> getWithdraw(@Query("account_id") String account_id);

    @GET("list_refund_account.php")
    Observable<PayItem> getPayList();

    @GET("list_message.php")
    Observable<Message> getMyMessageList(@Query("page_num") String page_num);

    @FormUrlEncoded
    @POST("upload_file_field.php")
    Observable<ImageItem> getUploadImage(@Field("Filedata") String file);

    @FormUrlEncoded
    @POST("update_my_info.php")
    Observable<Result> editUser(@Field("name") String name, @Field("face") String file);

    @GET("list_focus.php")
    Observable<FocusList> getMyFollowList();

    @GET("list_view_log.php")
    Observable<FocusList> getMyHistoryList();

    @GET("list_coupon.php")
    Observable<Coupon> getMyCouponList(@Query("area_id") String area_id);

    @GET("list_my_expo.php")
    Observable<Expo> getMyExpoList();

    @GET("get_district.php")
    Observable<ProvinceAddress> getAreaList(@Query("area_id") String area_id);

    @GET("change_password.php")
    Observable<Result> modifyPwd(@Query("login_pass") String login_pwd, @Query("new_pass") String new_pwd);

    @GET("reset_phone.php")
    Observable<Result> reSetPhone(@Query("phone") String phone, @Query("code") String checkCode);

    @GET("reset_password.php")
    Observable<Result> resetPwd(@Query("phone") String phone, @Query("code") String checkCode, @Query("pass") String
            pass);

    @GET("send_p2_vcode.php")
    Observable<Result> getP2CheckCode(@Query("phone") String phone, @Query("code") String checkCode);

    @GET("send_p1_vcode.php")
    Observable<Result> getCheckCodeWithSession();

    @GET("send_vcode.php")
    Observable<Result> getCheckCode(@Query("phone") String phone, @Query("sign") String sign);

    @GET("register.php")
    Observable<User> register(@Query("phone") String phone, @Query("pass") String password,
                              @Query("code") String code, @Query("sale_id") String sale_id,
                              @Query("device") String device);

    @GET("get_latest_edition.php")
    Observable<Version> getVersion(@Query("os") String os);

    @GET("login.php")
    Observable<User> loginUser(@Query("phone") String username, @Query("pass") String password, @Query("device")
    String device);

    //----------------------------------------------marketApi-----------------------------------------------------------

    @GET("list_mall_cat.php")
    Observable<MarketCategoryAll> getMarketCategoryList(@Query("area_id") String area_id);

    @GET("list_mall_prod.php")
    Observable<MarketProductList> getMarketProductList(@Query("area_id") String area_id, @Query("cat_id") String cat_id,
                                                       @Query("prop_value") String prop_value, @Query("order_by")
                                                       String order_by, @Query("page_num") int page);


    @GET("list_mall_shop.php")
    Observable<MarketShop> getShopList(@Query("area_id") String area_id, @Query("cat_id") String cat_id, @Query
            ("prop_value") String prop_value, @Query("page_num") int page);

    @GET("search_prod.php")
    Observable<MarketProductList> searchProductList(@Query("keyword") String keyword, @Query("area_id") String
            area_id, @Query("order_by") String order_by, @Query("page_num") int pageBean);

    @GET("search_shop.php")
    Observable<MarketShop> searchShopList(@Query("area_id") String area_id, @Query("keyword") String keyword, @Query
            ("page_num") int page);

    @GET("list_onsale_cat.php")
    Observable<MarketCategoryAll> getOnSaleCategoryList(@Query("area_id") String area_id);

    @GET("list_onsale_prod.php")
    Observable<MarketProduct> getOnSaleProductList(@Query("area_id") String area_id, @Query("cat_id") String
            cat_id, @Query("page_num") int page);

    @GET("shop.php")
    Observable<MarketShop> getShopInfo(@Query("shop_id") String shop_id);

    @GET("list_shop_cat.php")
    Observable<ExpoCategory> getShopCategory(@Query("shop_id") String shop_id);


    @GET("list_shop_prod.php")
    Observable<MarketProduct> getShopProductList(@Query("shop_id") String shop_id, @Query("cat_id") String cat_id,
                                                 @Query("keywords") String keywords, @Query("order_by") String order_by,
                                                 @Query("page_num") int page);

    @GET("list_shop_front.php")
    Observable<MarketFrontShop> getFrontShopList(@Query("shop_id") String shop_id);

    @GET("prod.php")
    Observable<MarketProductDetail> getProductDetail(@Query("sku_id") String sku_id);


    @GET("list_prod_comment.php")
    Observable<MarketCommentList> getCommentList(@Query("prod_id") String prod_id, @Query("comment_type") String
            comment_type, @Query("page_num") int page);

    @GET("focus_prod.php")
    Observable<Result> focusProduct(@Query("sku_id") String sku_id);


    @GET("cancel_focus_prod.php")
    Observable<Result> cancelFocusProduct(@Query("shop_id") String shop_id);

    @GET("focus_shop.php")
    Observable<Result> focusShop(@Query("shop_id") String shop_id);

    @GET("cancel_focus_shop.php")
    Observable<Result> cancelFocusShop(@Query("shop_id") String shop_id);

    @GET("get_coupon.php")
    Observable<Result> getCoupon(@Query("coupon_id") String shop_id);

    @GET("list_preorder.php")
    Observable<ProductPreOrder> getPreOrderList(@Query("shop_id") String shop_id, @Query("sku_id") String sku_id);
    //--------------------------------------------BillApi---------------------------------------------------------------

    @GET("list_cart.php")
    Observable<MarketShop> getCarList(@Query("page_num") int page_num);

    @GET("add_cart.php")
    Observable<Result> addCarProduct(@Query("sku_id") String sku_id, @Query("quantity") String quantity);

    @GET("del_cart.php")
    Observable<Result> deleteCarProduct(@Query("sku_id") String sku_id);

    @GET("prepare_order.php")
    Observable<BillConfirm> confirmBill(@Query("shop_id") String shop_id, @Query("order_type") String order_type);

    @FormUrlEncoded
    @POST("submit_order.php")
    Observable<Bill> submitBill(@Field("shipping_id") String shipping_id, @Field("preorder_id") String preorder_id,
                                @Field("order_info") String order_info);

    @FormUrlEncoded
    @POST("submit_quick_order.php")
    Observable<Bill> submitQuick(@Field("shipping_id") String shipping_id, @Field("order_info") String order_info);

    @GET("list_order.php")
    Observable<Bill> getBillList(@Query("status") String status, @Query("page_num") int page_num);

    @GET("list_booking.php")
    Observable<BookingBill> getBookingBillList(@Query("page_num") int page_num);

    @GET("order.php")
    Observable<BillInfo> getBillInfo(@Query("order_id") String order_id);

    @GET("confirm_order.php")
    Observable<Result> confirmReceive(@Query("order_id") String order_id);

    @GET("cancel_order.php")
    Observable<Result> cancelBill(@Query("order_id") String order_id);

    @GET("comment_order_prod.php")
    Observable<Result> comment(@Query("order_id") String order_id, @Query("sku_id") String sku_id, @Query("score")
    String score, @Query("remark") String remark, @Query("pic") String pic, @Query("thumbnail") String thumbnail);

    @GET("prepare_alipay.php")
    Observable<AliPay> prepareAlipay(@Query("type") String type, @Query("shop_id") String shop_id, @Query
            ("order_ids") String order_ids, @Query("pay_money") String pay_money, @Query("acc_money") String
                                             acc_money, @Query("shipping_id") String shipping_id);

    @GET("save_order_promotion.php")
    Observable<GiftCode> getGift(@Query("order_id") String order_id, @Query("type") String type, @Query("gift_id")
    String gift_id);

    @GET("list_order_promotion.php")
    Observable<GiftList> getGiftList(@Query("order_id") String order_id, @Query("type") String type);

    @GET("success_alipay.php")
    Observable<Result> alipaySuccess(@Query("status") String status, @Query("result") String result);

    @GET("prepare_wxpay.php")
    Observable<WechatPay> prepareWechat(@Query("type") String type, @Query("shop_id") String shop_id, @Query
            ("order_ids") String order_ids, @Query("pay_money") String pay_money, @Query("acc_money") String acc_money
            , @Query("shipping_id") String shipping_id);

    @GET("success_account_pay.php")
    Observable<AccountPay> accountSuccess(@Query("type") String type, @Query("shop_id") String shop_id, @Query
            ("order_ids") String order_ids, @Query("acc_money") String acc_money, @Query("shipping_id") String
                                                  shipping_id);

    @GET("prepare_booking.php")
    Observable<BookingPay> prepareBookingPay(@Query("expo_id") String expo_id, @Query("shop_id") String shop_id);

    @GET("my_balance.php")
    Observable<Balance> getBalance(@Query("shop_id") String shop_id, @Query("expo_id") String expo_id);*/

}
