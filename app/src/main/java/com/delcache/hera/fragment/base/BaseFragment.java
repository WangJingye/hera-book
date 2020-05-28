package com.delcache.hera.fragment.base;

import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.helper.ActivityHelper;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.helper.SettingHelper;
import com.delcache.hera.interfaces.BackHandledInterface;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.MyLogger;
import com.delcache.hera.utils.Utils;
import com.delcache.hera.widget.CustomToolbar;

/**
 * Fragment 基类
 */
public class BaseFragment extends Fragment implements RefreshUIInterface, OnClickListener {

    protected MyLogger logger = MyLogger.getLogger();

    protected Context mContext;

    protected View mContainerView;

    protected ActivityHelper mActivityHelper = null;

    protected BackHandledInterface mBackHandledInterface;

    public boolean isFirstLoading = true;

    protected void setupView(View view) {

    }

    protected void setupData() {

    }

    protected void sendRequest() {
    }

    protected int isDisplayBottomMenu() {
        return View.GONE;
    }

    public boolean onBackPressed() {
        FragmentHelper.getInstance().goBack();
        return true;
    }

    protected View init(int resId, ViewGroup parent) {

        if (mContainerView == null) {
            mContext = this.getActivity();
            mActivityHelper = ActivityHelper.getInstance();

            // 禁止输入法在Activity启动时自动弹出，手动点击输入框可以弹出
            this.getActivity()
                    .getWindow()
                    .setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mContainerView = LayoutInflater.from(mContext).inflate(resId, parent, false);
            ButterKnife.bind(this, mContainerView);
            setupView(mContainerView);
            setupData();
            mContainerView.postDelayed(() -> {
                sendRequest();
            }, 400);
        } else {
            if (mContainerView.getParent() != null) {
                ((ViewGroup) mContainerView.getParent()).removeView(mContainerView);
            }
        }

        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }

        return mContainerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setBottombarVisibility(isDisplayBottomMenu());
        Constants.readMode = false;
        if ("night_mode".equals(getStringFromSharePreference("readMode"))) {
            Constants.readMode = true;
        }
        if (Constants.readMode) {
            SettingHelper.getInstance(mContext).setScreenBrightness(0);
        } else {
            SettingHelper.getInstance(mContext).setScreenBrightness(Constants.screenBrightness);
        }
    }

    protected String getParam() {
        if (getArguments() != null)
            return getArguments().getString("string_param");
        else
            return "";
    }

    /**
     * 弹出toast message
     *
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    protected String getStringFromSharePreference(String key) {
        return mContext.getSharedPreferences(ConstantStore.SP_NAME,
                Context.MODE_PRIVATE).getString(key, "");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left_title:
                FragmentHelper.getInstance().goBack();
                break;
        }
    }

    @Override
    public void refreshUI(Object object) {

    }

    @Override
    public void refreshWithResult(Object object, int type) {

    }

    @Override
    public void doError(int type) {

    }
}
