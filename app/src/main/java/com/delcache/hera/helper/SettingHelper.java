package com.delcache.hera.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import com.delcache.hera.activity.MainActivity;

public class SettingHelper {

    Context mContext;
    private static SettingHelper settingHelper = null;

    public static SettingHelper getInstance(Context context) {
        if (settingHelper == null) {
            settingHelper = new SettingHelper();
        }
        settingHelper.mContext = context;
        return settingHelper;
    }

    public int getScreenBrightness() {
        ContentResolver contentResolver = mContext.getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }

    public void setScreenBrightness(int brightness) {
        Window window = ((MainActivity) mContext).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }
}
