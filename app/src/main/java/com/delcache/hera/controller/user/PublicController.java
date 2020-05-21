package com.delcache.hera.controller.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.delcache.hera.R;
import com.delcache.hera.bean.ResetTokenBean;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.fragment.user.FragmentLogin;
import com.delcache.hera.fragment.user.FragmentResetPassword;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.helper.RequestHelper;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.widget.ProgressSubscriber;

public class PublicController extends Controller {

    public PublicController(Context mContext, RefreshUIInterface refreshUIInterface) {
        super(mContext, refreshUIInterface);
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    public void login(String userName, String passWord) {
        RequestHelper.getInstance().login(userName, passWord)
                .doOnNext(loginBean -> {
                    putStringToSharePreference("userId", loginBean.getUserId());
                    putStringToSharePreference("identity", loginBean.getIdentity());
                    Constants.identity = loginBean.getIdentity();
                })
                .subscribe(new ProgressSubscriber(mContext, getString(R.string.login), getString(R.string.login_success)) {
                    @Override
                    public void success() {
                        FragmentHelper.getInstance().goBack();
                    }
                });
    }

    public void sendVerifyCode(String telephone, int type) {
        RequestHelper.getInstance(mContext, true).sendVerifyCode(telephone, type).subscribe(new ProgressSubscriber(mContext, false));
    }

    public void checkVerifyCode(String telephone, String verifyCode, int type) {
        RequestHelper.getInstance().checkVerifyCode(telephone, verifyCode, type)
                .subscribe(new ProgressSubscriber<ResetTokenBean>(mContext, false) {
                    @Override
                    public void success() {
                        switch (type) {
                            case Constants.VerifyCodeTypes.TYPE_RESET_PASSWORD:
                                Bundle bundle = new Bundle();
                                bundle.putString("resetToken", data.getResetToken());
                                bundle.putString("telephone", telephone);
                                Fragment fragment = new FragmentResetPassword();
                                fragment.setArguments(bundle);
                                FragmentHelper.getInstance().addFragment(fragment);
                                break;
                        }
                    }
                });
    }

    public void resetPassword(String telephone, String resetToken, String password, String confirmPassowrd) {
        RequestHelper.getInstance().resetPassword(telephone, resetToken, password, confirmPassowrd)
                .subscribe(new ProgressSubscriber(mContext, false) {
                    @Override
                    public void success() {
                        FragmentHelper.getInstance().backToFragment(FragmentLogin.class.getName());
                    }
                });
    }

    public void logout() {
        putStringToSharePreference("userId", "");
        putStringToSharePreference("identity", "");
        Constants.identity = "";
        FragmentHelper.getInstance().replaceFragment(new FragmentLogin(),new Integer[]{0,0,0,0});
    }
}
