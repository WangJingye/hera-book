package com.delcache.hera.controller.base;

import android.content.Context;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.utils.ConstantStore;

public abstract class Controller {

    protected Context mContext;
    protected RefreshUIInterface refreshUIInterface;


    public Controller(Context mContext, RefreshUIInterface refreshUIInterface) {
        this.mContext = mContext;
        this.refreshUIInterface = refreshUIInterface;
    }

    protected void putStringToSharePreference(String key, String value) {
        mContext.getSharedPreferences(ConstantStore.SP_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    protected void getStringFromSharePreference(String key) {
        mContext.getSharedPreferences(ConstantStore.SP_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public String getString(int id) {

        return mContext.getResources().getString(id);
    }
}
