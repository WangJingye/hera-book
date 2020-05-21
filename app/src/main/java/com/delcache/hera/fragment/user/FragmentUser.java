package com.delcache.hera.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.controller.user.PublicController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.utils.Utils;

public class FragmentUser extends BaseFragment {

    private PublicController publicController;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_user, container);
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
}
