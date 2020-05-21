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
import com.delcache.hera.utils.Utils;
import com.delcache.hera.widget.CustomToolbar;

public class FragmentLogin extends BaseFragment {

    private PublicController publicController;

    @BindView(R.id.action_toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.login_username)
    EditText username;
    @BindView(R.id.login_password)
    EditText password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_login, container);
    }

    @Override
    protected void setupView(View view) {
        toolbar.setMainTitle(getString(R.string.text_login_title));
        toolbar.setLeftTitleText("");
        toolbar.setLeftTitleDrawable(R.mipmap.icon_close,Utils.getColor(mContext, R.color.colorGray));
        toolbar.setRightTitleText();
        toolbar.setRightTitleDrawable();
        toolbar.setTitleColor(Utils.getColor(mContext, R.color.colorGray));
        toolbar.setBackgroundColor(Utils.getColor(mContext, R.color.colorDefault));
        toolbar.setLeftTitleClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    protected void setupData() {
        publicController = new PublicController(mContext, this);
    }

    @OnClick(R.id.login_confirm)
    void confirm() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        if (TextUtils.isEmpty(userName))
            showMsg(getString(R.string.please_enter_username));
        else if (TextUtils.isEmpty(passWord))
            showMsg(getString(R.string.please_enter_password));
        else {
            publicController.login(userName, passWord);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left_title:
                FragmentHelper.getInstance().goBack();
                break;
            case R.id.forget_password:
                FragmentHelper.getInstance().addFragment(new FragmentResetPasswordCheck());
                break;
        }
    }
}
