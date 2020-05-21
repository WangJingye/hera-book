package com.delcache.hera.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("已发送("+String.valueOf(millisUntilFinished / 1000) + ")");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        mTextView.setText("发送验证码");
        mTextView.setClickable(true);//重新获得点击
    }
}