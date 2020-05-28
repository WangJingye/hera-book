package com.delcache.hera.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.fragment.user.FragmentLogin;
import com.delcache.hera.utils.Config;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.Constants;

import java.util.List;

public class FragmentHelper {

    private static FragmentHelper FragmentHelper = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity activity;

    protected boolean isDisplayAnimation = true;


    private FragmentHelper() {
    }

    public static FragmentHelper getInstance() {
        if (FragmentHelper == null)
            FragmentHelper = new FragmentHelper();
        return FragmentHelper;
    }

    public void setIsDisplayAnimation(boolean isDisplayAnimation) {
        this.isDisplayAnimation = isDisplayAnimation;
    }

    public void addFragment(Fragment fragment) {
        Integer[] anim = new Integer[]{
                R.anim.fragment_slide_in_from_right,
                R.anim.fragment_slide_out_to_left,
                R.anim.fragment_slide_in_from_left,
                R.anim.fragment_slide_out_to_right
        };
        addFragment(fragment, anim);
    }

    public void removeAllFragment() {
        if (fragmentManager.getFragments() != null && fragmentManager.getFragments().size() > 0) {
            fragmentManager.getFragments().clear();
        }
    }

    public void addFragment(Fragment fragment, Integer[] anim) {
        if (isInStack(fragment.getClass().getSimpleName())) {
            return;
        }
        List<String> needLoginFragment = Config.getLoginAction();
        Constants.identity = activity.getSharedPreferences(ConstantStore.SP_NAME, Context.MODE_PRIVATE).getString("identity", "");
        //当前fragment需要登录并且当前用户未登录
        if (needLoginFragment.contains(fragment.getClass().getSimpleName())
                && "".equals(Constants.identity)) {
            fragment = new FragmentLogin();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        if (isDisplayAnimation) {
            fragmentTransaction.setCustomAnimations(
                    anim[0],
                    anim[1],
                    anim[2],
                    anim[3]
            );
        } else {
            fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
            isDisplayAnimation = true;
        }
        fragmentTransaction.replace(R.id.main_frame, fragment, "fragmentTag");
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment() {
        return fragmentManager.findFragmentByTag("fragmentTag");
    }

    public void goBack() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            popBackStackInclusive();
        } else {
            fragmentManager.popBackStack();
        }
    }

    public void popBackStackInclusive() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void backToFragment(String fragmentName) {
        fragmentManager.popBackStackImmediate(fragmentName, 0);
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }

    //删除当前fragment并且新增一个
    public void replaceFragment(Fragment fragment) {
        goBack();
        addFragment(fragment);
    }

    //删除当前fragment并且新增一个
    public void replaceFragment(Fragment fragment, Integer[] anim) {
        goBack();
        addFragment(fragment, anim);
    }

    public void redirectLogin() {
        Integer[] anim = new Integer[]{
                R.anim.fragment_slide_in_from_top,
                R.anim.fragment_slide_out_to_bottom,
                R.anim.fragment_slide_in_from_bottom,
                R.anim.fragment_slide_out_to_top,
        };
        addFragment(new FragmentLogin(), anim);
    }

    public void redirectHome() {
        activity.getBottomBar().setSelectedItemId(R.id.bottom_item_1);
    }

    private boolean isInStack(String fragName) {
        List<Fragment> list = fragmentManager.getFragments();
        for (Fragment frag : list) {
            if (frag != null && frag.getClass().getSimpleName().equals(fragName)) {
                return true;
            }
        }
        return false;
    }

}
