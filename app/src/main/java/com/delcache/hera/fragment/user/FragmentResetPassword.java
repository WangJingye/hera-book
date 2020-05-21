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

public class FragmentResetPassword extends BaseFragment {

    private PublicController publicController;

    @BindView(R.id.action_toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.new_password)
    EditText newPasswordText;
    @BindView(R.id.confirm_password)
    EditText confirmPasswordText;

    private String telephone;
    private String resetToken;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_reset_password, container);
    }

    @Override
    protected void setupView(View view) {
        toolbar.setMainTitle(getString(R.string.title_reset_password));
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
        if(getArguments()!=null) {
            telephone = getArguments().getString("telephone");
            resetToken = getArguments().getString("resetToken");
        }
    }

    @OnClick(R.id.confirm)
    void confirm() {
        String newPassword = newPasswordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            showMsg(getString(R.string.please_enter_new_password));
        } else if (TextUtils.isEmpty(confirmPassword)) {
            showMsg(getString(R.string.please_enter_confirm_password));
        } else if (!newPassword.equals(confirmPassword)) {
            showMsg(getString(R.string.password_reset_check));
        } else {
            publicController.resetPassword(telephone,resetToken, newPassword, confirmPassword);
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
}
