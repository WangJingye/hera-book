package com.delcache.hera.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.MyLogger;

public abstract class BaseActivity extends FragmentActivity {

    protected MyLogger logger = MyLogger.getLogger();

    protected Context mContext;

    protected SharedPreferences preferences;

    protected abstract void setupView();

    protected abstract void setupData();

    protected abstract void sendRequest();

    protected abstract void doError(int errorCode);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.d("--onCreate ---Current Activity ==" + this);
    }

    @Override
    protected void onPause() {
        logger.d("--onPause ---Current Activity ==" + this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        logger.d("--onResume ---Current Activity ==" + this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        logger.d("--onDestroy ---Current Activity ==" + this);
        super.onDestroy();
    }

    protected void init(int layoutId) {

        mContext = this;
        setContentView(layoutId);
        ButterKnife.bind(this);
        // 禁止输入法在Activity启动时自动弹出，手动点击输入框可以弹出
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setupView();
        setupData();
        sendRequest();
        preferences = getSharedPreferences(ConstantStore.SP_NAME, MODE_PRIVATE);
    }

    protected void init(View v) {
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(v);
        ButterKnife.bind(this);
        // 禁止输入法在Activity启动时自动弹出，手动点击输入框可以弹出
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupView();
        setupData();
        preferences = getSharedPreferences(ConstantStore.SP_NAME, MODE_PRIVATE);
    }

    /**
     * 弹出toast message
     *
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
