package com.delcache.hera.utils;

/**
 * 常量帮助类
 */
public class Constants {

    public static Boolean isExit = false;

    public static String SP_NAME = "sp_base";
    public static String IMAGE_SAVE_PATH = "image_base";
    public static String versionCode = "";

    public static boolean isFirstLogin = true;

    public static String coordinateX = "";
    public static String coordinateY = "";

    public static String userName = "";
    public static String userPassword = "";
    public static String depot = "";
    public static String tagType = "";

    public static String identity = "";
    public static boolean readMode = false;
    public static int screenBrightness = 255;

    public static boolean longClick = false;

    public static boolean isRefreshed = false;

    public static boolean getMissionRefresh = false;

    public static String resultString = "";

    public static boolean isShowingTaskDialog = false;

    public static class RefreshParams {

        /**
         * 刷新页面
         */
        public static final int REQUEST_ERROR = 999;
        /**
         * 刷新页面
         */
        public static final int REQUEST_UPDATE_ALL = 9999;
        public static final int UPDATE_ALL = 1000;
        public static final int UI_1001 = 1001;
        public static final int DATA_8001 = 8001;
        public static final int DATA_8002 = 8002;
        public static final int DATA_8003 = 8003;
        public static final int DATA_8004 = 8004;
    }

    public static class RequestCodes {
        /**
         * 获取成功
         */
        public static final int REQUEST_SUCCESS = 200;
        /**
         * 未登录
         */
        public static final int REQUEST_NO_LOGIN = 999;
    }

    public static class VerifyCodeTypes {

        //登录
        public static final int TYPE_LOGIN = 1;

        //忘记密码
        public static final int TYPE_RESET_PASSWORD = 2;

    }

    /**
     * 广播 action
     */
    public static class BroadcastActionParams {

        /**
         * 我的任务
         */
        public static String ACTION_UPDATE_DATA = "android.intent.action.UPDATE_DATA_BROADCAST";
    }

}
