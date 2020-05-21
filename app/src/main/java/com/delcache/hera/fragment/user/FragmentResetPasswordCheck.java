package com.delcache.hera.fragment.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.delcache.hera.R;
import com.delcache.hera.controller.user.PublicController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.CountDownTimerUtils;
import com.delcache.hera.utils.Utils;
import com.delcache.hera.widget.CustomToolbar;

public class FragmentResetPasswordCheck extends BaseFragment {

    private PublicController publicController;

    @BindView(R.id.action_toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.reset_telephone)
    EditText telephoneText;
    @BindView(R.id.reset_verify_code)
    EditText verifyCodeText;
    @BindView(R.id.send_verify_code)
    TextView sendVerifyCodeText;
    CountDownTimerUtils mCountDownTimerUtils;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_forget_password, container);
    }

    @Override
    protected void setupView(View view) {
        toolbar.setMainTitle(getString(R.string.title_reset_password_check));
        toolbar.setLeftTitleText("");
        toolbar.setRightTitleText("");
        toolbar.setRightTitleDrawable();
        toolbar.setTitleColor(Utils.getColor(mContext, R.color.colorGray));
        toolbar.setBackgroundColor(Utils.getColor(mContext, R.color.colorDefault));
        toolbar.setLeftTitleClickListener(this);
    }

    @Override
    protected void setupData() {
        publicController = new PublicController(mContext, this);
    }

    @OnClick(R.id.confirm)
    void confirm() {
        String telephone = telephoneText.getText().toString();
        String verifyCode = verifyCodeText.getText().toString();
        if (TextUtils.isEmpty(telephone))
            showMsg(getString(R.string.please_enter_telephone));
        else if (TextUtils.isEmpty(verifyCode))
            showMsg(getString(R.string.please_enter_verify_code));
        else {
            publicController.checkVerifyCode(telephone, verifyCode, Constants.VerifyCodeTypes.TYPE_RESET_PASSWORD);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left_title:
                FragmentHelper.getInstance().goBack();
                break;
        }
    }

    @OnClick(R.id.send_verify_code)
    void sendVerifyCode() {
        String telephone = telephoneText.getText().toString();
        if (TextUtils.isEmpty(telephone)) {
            showMsg(getString(R.string.please_enter_telephone));
            return;
        }
        mCountDownTimerUtils = new CountDownTimerUtils(sendVerifyCodeText, 60000, 1000);
        mCountDownTimerUtils.start();
        publicController.sendVerifyCode(telephoneText.getText().toString(), Constants.VerifyCodeTypes.TYPE_RESET_PASSWORD);
    }

    @Override
    public void onDestroy() {
        if(mCountDownTimerUtils!=null) {
            mCountDownTimerUtils.cancel();
        }
        super.onDestroy();
    }
}
