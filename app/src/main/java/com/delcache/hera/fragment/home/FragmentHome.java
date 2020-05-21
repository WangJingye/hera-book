package com.delcache.hera.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.utils.Utils;

public class FragmentHome extends BaseFragment {

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_home, container);
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity)mContext).setSelectedTab(0);
    }
    @Override
    protected int isDisplayTopMenu() {
        return View.VISIBLE;
    }

    @Override
    protected int isDisplayBottomMenu() {
        return View.VISIBLE;
    }

    @Override
    public boolean onBackPressed() {
        Utils.exit(mContext);
        return true;
    }
}
