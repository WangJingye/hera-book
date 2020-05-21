package com.delcache.hera.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;

/**
 * 自定义Toolbar
 */
public class CustomToolbar extends Toolbar {

    //左侧Title
    @BindView(R.id.txt_left_title)
    TextView mTxtLeftTitle;
    //中间Title
    @BindView(R.id.txt_main_title)
    TextView mTxtMiddleTitle;
    //右侧Title
    @BindView(R.id.txt_right_title)
    TextView mTxtRightTitle;
    @BindView(R.id.action_bar)
    Toolbar toolbar;

    public CustomToolbar(Context context) {
        this(context, null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.action_toolbar, this);
        ButterKnife.bind(this);
    }


    //设置中间title的内容
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    //设置中间title的内容文字的颜色
    public void setTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
        mTxtLeftTitle.setTextColor(color);
        mTxtRightTitle.setTextColor(color);
    }

    //设置背景颜色
    @Override
    public void setBackgroundColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    //设置title左边文字
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    //设置title左边文字颜色
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    //设置title左边图标
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }

    //设置title左边图标
    public void setLeftTitleDrawable(int res, int color) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        dwLeft.setTint(color);
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }

    //设置title左边图标
    public void setLeftTitleDrawable() {
        mTxtLeftTitle.setCompoundDrawables(null, null, null, null);
    }

    //设置title左边图标
    public void setRightTitleDrawable() {
        mTxtRightTitle.setCompoundDrawables(null, null, null, null);
    }

    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener) {
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }

    //设置title右边文字
    public void setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mTxtRightTitle.setText(text);
    }

    //取消右边文字
    public void setRightTitleText() {
        mTxtRightTitle.setVisibility(View.GONE);
    }

    //取消左边文字
    public void setLeftTitleText() {
        mTxtLeftTitle.setVisibility(View.GONE);
    }

    //设置title右边文字颜色
    public void setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
    }

    //设置title右边图标
    public void setRightTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTxtRightTitle.setCompoundDrawables(null, null, dwRight, null);
    }

    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener) {
        mTxtRightTitle.setOnClickListener(onClickListener);
    }
}
