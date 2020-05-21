package com.delcache.hera.interfaces;

import com.delcache.hera.bean.HttpResult;
import com.delcache.hera.bean.ResetTokenBean;
import com.delcache.hera.bean.UserBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("v1/public/login")
    Observable<HttpResult<UserBean>> login(@QueryMap Map<String, String> token, @Field("username") String userName, @Field("password") String passWord);

    @FormUrlEncoded
    @POST("v1/public/send-verify-code")
    Observable<HttpResult<Object>> sendVerifyCode(@QueryMap Map<String, String> token, @Field("telephone") String telephone, @Field("type") int type);

    @FormUrlEncoded
    @POST("v1/public/check-verify-code")
    Observable<HttpResult<ResetTokenBean>> checkVerifyCode(@QueryMap Map<String, String> token, @Field("telephone") String telephone, @Field("verify_code") String verifyCode, @Field("type") int type);

    @FormUrlEncoded
    @POST("v1/public/reset-password")
    Observable<HttpResult<Object>> resetPassword(@QueryMap Map<String, String> token, @Field("telephone") String telephone, @Field("reset_token") String resetToken, @Field("password") String password, @Field("confirm_password") String confirmPassword);
}
