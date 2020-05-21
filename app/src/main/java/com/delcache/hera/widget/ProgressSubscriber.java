package com.delcache.hera.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.delcache.hera.R;
import com.delcache.hera.dialog.MessageDialog;
import com.delcache.hera.dialog.ProgressDialog;
import com.delcache.hera.utils.StringTool;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class ProgressSubscriber<T> implements Observer<T>, MessageDialog.SuccessInterface {

    private Context mContext;
    private boolean isShowProgress = true;
    private ProgressDialog proDialog;
    private MessageDialog loadingDialog;

    private String content;
    private String successContent;
    public T data;

    public ProgressSubscriber(Context context) {
        this.mContext = context;
    }

    public ProgressSubscriber(Context context, boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        this.mContext = context;
    }

    public ProgressSubscriber(Context context, String content, String successContent) {
        this.mContext = context;
        this.content = content;
        this.successContent = successContent;
    }

    /**
     * @param t 存在下的data 暂时只对单个请求
     */
    public void afterCompleted(T t) {

    }

    @Override
    public void onError(Throwable e) {
        Log.d("ProgressSubscriber-", "onError:" + e.toString());
        dismissProgress();

        String error = e.getMessage();
        if (e instanceof SocketTimeoutException) {
            error = StringTool.getInstance().getStingById(R.string.connect_timeout);
        } else if (e instanceof ConnectException) {
            error = StringTool.getInstance().getStingById(R.string.connect_exception);
        }

        if (!failLoading(error)) {
            Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
        }
        error(error);
    }

    @Override
    public void onComplete() {
        dismissProgress();
        if (isShowProgress) {
            successLoading(successContent);
        } else {
            success();
        }
        afterCompleted(data);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!d.isDisposed()) {
            if (TextUtils.isEmpty(content) && isShowProgress) {
                showProgress();
            } else if (isShowProgress) {
                showLoading();
            }
        }
    }

    @Override
    public void onNext(T t) {
        data = t;
    }

    public void showProgress() {
        if (proDialog == null) {
            proDialog = new ProgressDialog(mContext);
        }
        proDialog.show();
    }

    public void dismissProgress() {
        if (proDialog != null && proDialog.isShowing()) {
            proDialog.dismiss();
        }
    }

    public boolean isShow() {
        if (loadingDialog != null) {
            return loadingDialog.isShowing();
        }
        return false;
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new MessageDialog(mContext);
            loadingDialog.setSuccessInterface(this);
        }
        loadingDialog.setContent(content);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }


    public boolean failLoading(String error) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.fail(error);
            return true;
        } else
            return false;
    }

    public void successLoading(String content) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.success(content);
        }
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void success() {

    }
}
