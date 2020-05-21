package com.delcache.hera.utils;

import android.app.Application;

/**
 * 在无 Activity | Context | Application 的地方读取 string.xml 字符串读取
 */
public class StringTool {

    private static StringTool stringTool;

    protected Application application;

    public static StringTool getInstance() {
        if (stringTool == null) {
            stringTool = new StringTool();
        }
        return stringTool;
    }

    public void setApplication(Application app) {
        application = app;
    }

    public String getStingById(int stringId) {
        return application.getString(stringId);
    }
}



