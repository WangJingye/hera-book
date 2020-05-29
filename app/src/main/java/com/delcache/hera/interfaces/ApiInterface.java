package com.delcache.hera.interfaces;

import com.delcache.hera.bean.*;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.bean.table.BookMenuBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("v1/public/login")
    Observable<HttpResult<UserBean>> login(@Field("username") String userName, @Field("password") String passWord);

    @FormUrlEncoded
    @POST("v1/public/send-verify-code")
    Observable<HttpResult<Object>> sendVerifyCode(@Field("telephone") String telephone, @Field("type") int type);

    @FormUrlEncoded
    @POST("v1/public/check-verify-code")
    Observable<HttpResult<ResetTokenBean>> checkVerifyCode(@Field("telephone") String telephone, @Field("verify_code") String verifyCode, @Field("type") int type);

    @FormUrlEncoded
    @POST("v1/public/reset-password")
    Observable<HttpResult<Object>> resetPassword(@Field("telephone") String telephone, @Field("reset_token") String resetToken, @Field("password") String password, @Field("confirm_password") String confirmPassword);

    @POST("v1/home/index")
    Observable<HttpResult<HomeBean>> home();

    @POST("v1/user/get-collection")
    Observable<HttpResult<List<BookBean>>> getCollection();

    @FormUrlEncoded
    @POST("v1/book/info")
    Observable<HttpResult<BookBean>> getBookInfo(@Field("book_id") long bookId);

    @FormUrlEncoded
    @POST("v1/book/detail")
    Observable<HttpResult<BookMenuBean>> getBookDetail(@Field("book_id") long bookId, @Field("menu_id") long menuId);

    @FormUrlEncoded
    @POST("v1/user/add-to-collection")
    Observable<HttpResult<Object>> addToCollection(@Field("book_id") long bookId);

    @FormUrlEncoded
    @POST("v1/book/get-menu-list")
    Observable<HttpResult<List<BookMenuBean>>> getMenuList(@Field("book_id") long bookId);
}
