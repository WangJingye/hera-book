package com.delcache.hera.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.controller.user.PublicController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.helper.SettingHelper;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.Utils;

public class FragmentUser extends BaseFragment {

    private PublicController publicController;

    @BindView(R.id.read_mode)
    Switch readModeSwitch;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_user, container);
    }

    @Override
    public void setupView(View view) {
        readModeSwitch.setChecked(Constants.readMode);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setSelectedTab(2);
    }

    @Override
    protected int isDisplayBottomMenu() {
        return View.VISIBLE;
    }


    @Override
    protected void setupData() {
        publicController = new PublicController(mContext, this);
    }

    @Override
    public boolean onBackPressed() {
        Utils.exit(mContext);
        return true;
    }

    @OnClick(R.id.logout_confirm)
    void logout() {
        publicController.logout();
    }

    @OnCheckedChanged(R.id.read_mode)
    void setReadMode() {
        mContext.getSharedPreferences(ConstantStore.SP_NAME, Context.MODE_PRIVATE).edit().putString("readMode", readModeSwitch.isChecked() ? "night_mode" : "day_mode").commit();
        Constants.screenBrightness = SettingHelper.getInstance(mContext).getScreenBrightness();
        Constants.readMode = readModeSwitch.isChecked();
        setScreenBrightness();
    }

    protected void setScreenBrightness() {
        if (Constants.readMode) {
            Constants.screenBrightness = SettingHelper.getInstance(mContext).getScreenBrightness();
            SettingHelper.getInstance(mContext).setScreenBrightness(0);
        } else {
            SettingHelper.getInstance(mContext).setScreenBrightness(255);
        }
    }
}
