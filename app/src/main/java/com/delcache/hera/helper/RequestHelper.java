package com.delcache.hera.helper;


import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.delcache.hera.BuildConfig;
import com.delcache.hera.bean.HttpResult;
import com.delcache.hera.bean.ResetTokenBean;
import com.delcache.hera.bean.UserBean;
import com.delcache.hera.interfaces.ApiInterface;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.Utils;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RequestHelper {

    private static RequestHelper INSTANCE;
    public String BASE_URL;

    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 10;

    private Retrofit retrofit;
    private ApiInterface apiServer;
    private boolean showMessage = false;
    private Context mContext = null;


    //构造方法私有
    private RequestHelper() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);

        setParam(httpClientBuilder);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        apiServer = retrofit.create(ApiInterface.class);
    }

    //获取单例
    public static RequestHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestHelper();
        }
        INSTANCE.showMessage = false;
        INSTANCE.mContext = null;
        return INSTANCE;
    }

    //获取单例
    public static RequestHelper getInstance(Context mContext, boolean showMessage) {
        if (INSTANCE == null) {
            INSTANCE = new RequestHelper();
        }
        INSTANCE.showMessage = showMessage;
        INSTANCE.mContext = mContext;
        return INSTANCE;
    }

    private String setParam(OkHttpClient.Builder httpClientBuilder) {
        if (BuildConfig.DEBUG) {
            BASE_URL = "https://api.delcache.com/";
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging).build();
        } else {
            BASE_URL = "https://api.delcache.com/";
        }
        return BASE_URL;
    }

    private static HashMap<String, String> getRequestToken() {
        HashMap<String, String> map = new HashMap<>();
        String timestamp = System.currentTimeMillis() / 1000 + "";
        map.put("token", Utils.sha1("HeraApi" + timestamp).toLowerCase());
        map.put("timestamp", timestamp);
        map.put("identity", Constants.identity);
        return map;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final HttpResult<T> httpResult) {
        return Observable.create(emitter -> {
            if (!emitter.isDisposed()) {
                switch (httpResult.getCode()) {
                    case Constants.RequestCodes.REQUEST_SUCCESS:
                        if (mContext != null && showMessage) {
                            Toast.makeText(mContext, httpResult.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if (httpResult.getData() == null) {
                            emitter.onNext(httpResult.getEmpty());
                        } else {
                            emitter.onNext(httpResult.getData());
                        }
                        break;
                    case Constants.RequestCodes.REQUEST_NO_LOGIN:
                        FragmentHelper.getInstance().redirectLogin();
                        emitter.onError(new Exception(httpResult.getMessage()));
                        break;
                    default:
                        emitter.onError(new Exception(httpResult.getMessage()));
                        break;

                }
            }
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    final ObservableTransformer transformer = observable -> ((Observable) observable).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap((io.reactivex.functions.Function) response -> flatResponse((HttpResult<Object>) response));

    protected <T> ObservableTransformer<HttpResult<T>, T> applySchedulers() {
        return (ObservableTransformer<HttpResult<T>, T>) transformer;
    }

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @return
     */
    public Observable<UserBean> login(String userName, String passWord) {
        return apiServer.login(getRequestToken(), userName, passWord).compose(this.applySchedulers());
    }

    /**
     * 发送验证码
     *
     * @param telephone
     * @return
     */
    public Observable<Object> sendVerifyCode(String telephone, int type) {
        return apiServer.sendVerifyCode(getRequestToken(), telephone, type).compose(this.applySchedulers());
    }

    /**
     * 校验验证码
     *
     * @param telephone
     * @return
     */
    public Observable<ResetTokenBean> checkVerifyCode(String telephone, String verifyCode, int type) {
        return apiServer.checkVerifyCode(getRequestToken(), telephone, verifyCode, type).compose(this.applySchedulers());
    }

    /**
     * 重置密码
     *
     * @param telephone
     * @return
     */
    public Observable<Object> resetPassword(String telephone, String resetToken, String password, String confirmPassword) {
        return apiServer.resetPassword(getRequestToken(), telephone, resetToken, password, confirmPassword).compose(this.applySchedulers());
    }

}
