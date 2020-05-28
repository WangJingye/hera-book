package com.delcache.hera.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.delcache.hera.R;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.fragment.home.FragmentHome;
import com.delcache.hera.fragment.user.FragmentCollect;
import com.delcache.hera.fragment.user.FragmentLogin;
import com.delcache.hera.fragment.user.FragmentUser;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.interfaces.BackHandledInterface;
import com.delcache.hera.utils.Config;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements BackHandledInterface {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private BaseFragment baseFragment;

    private List<Fragment> fragments = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_item_1:
                    setMainFragment(0);
                    return true;
                case R.id.bottom_item_2:
                    setMainFragment(1);
                    return true;
                case R.id.bottom_item_3:
                    setMainFragment(2);
                    return true;
                default:
                    return false;
            }
        }
    };

    @Override
    protected void setupView() {
        FragmentHelper.getInstance().setActivity(this);
        fragments.add(new FragmentHome());
        fragments.add(new FragmentCollect());
        fragments.add(new FragmentUser());
        setMainFragment(0);
    }

    @Override
    protected void setupData() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.bottom_item_1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void sendRequest() {

    }

    @Override
    protected void doError(int errorCode) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_main);
    }

    public void setBottombarVisibility(int visibility) {
        bottomNavigationView.setVisibility(visibility);
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.baseFragment = selectedFragment;
    }

    public void setMainFragment(int index) {
        Fragment fragment = fragments.get(index);
        FragmentHelper.getInstance().removeAllFragment();
        FragmentHelper.getInstance().addFragment(fragment, new Integer[]{0, 0, 0, 0});
    }

    public BottomNavigationView getBottomBar() {
        return bottomNavigationView;
    }

    public void setSelectedTab(int index) {
        bottomNavigationView.getMenu().getItem(index).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (baseFragment == null || !baseFragment.onBackPressed()) {
            FragmentHelper.getInstance().goBack();
        }
    }

}
