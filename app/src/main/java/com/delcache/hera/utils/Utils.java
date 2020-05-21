package com.delcache.hera.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.helper.ActivityHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    /**
     * token 加密
     * @param s
     * @return
     */
    public static String sha1(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(String.format("%02X", 0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        return sdf.format(new Date(Long.parseLong(time) * 1000));
    }

    public static String getTimes(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd  hh:mm");
        return sdf.format(new Date(Long.parseLong(time) * 1000));
    }

    public static String getVersionName(Context context) {
        String version = "";
        try {
            version = ""
                    + context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static void setAdapterEmptyView(Context context, int layoutId, AdapterView adapterView) {
        View emptyView = LayoutInflater.from(context).inflate(layoutId, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        if (adapterView instanceof GridView)
            ((ViewGroup) adapterView.getParent().getParent()).addView(emptyView);
        else
            ((ViewGroup) adapterView.getParent()).addView(emptyView);
        adapterView.setEmptyView(emptyView);
    }

    public static String converToString(String[] ig) {
        String str = "";
        if (ig != null && ig.length > 0) {
            for (int i = 0; i < ig.length; i++) {
                str += ig[i] + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    public static void exit(Context context) {
        Timer tExit = null;
        if (Constants.isExit == false) {

            Constants.isExit = true;
            Toast.makeText(context, context.getResources().getString(R.string.exit_massage), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    Constants.isExit = false;
                }
            }, 2000);

        } else {
            ActivityHelper.getInstance().clearActivityStack();
            ActivityHelper.getInstance().exitAppDalvik();
        }
    }

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(intent);
    }

    public static void toBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(Intent.createChooser(intent, "Please chose browser!"));
    }


    /**
     * 获取app相关信息
     *
     * @param mContext
     * @param num      根据num 选择返回参数
     * @return
     */
    public static String getAppInfo(Context mContext, int num) {
        String returnStr = "";
        try {
            String pkName = mContext.getPackageName();
            switch (num) {
                case 0:
                    String versionName = mContext.getPackageManager()
                            .getPackageInfo(pkName, 0).versionName;
                    returnStr = versionName;
                    break;
                case 1:
                    int versionCode = mContext.getPackageManager().getPackageInfo(
                            pkName, 0).versionCode;
                    returnStr = String.valueOf(versionCode);
                    break;
            }

        } catch (Exception e) {
        }
        return returnStr;
    }


    /**
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 代码中解析“＃112233” 颜色
     *
     * @param context  上下文
     * @param colorStr string id
     * @return
     */
    public static int getColor(Context context, int colorStr) {
        return Color.parseColor(context.getResources().getString(colorStr));
    }

    public static boolean isInstallShortcut(Context context, String applicationName) {
        boolean isInstallShortcut = false;
        ContentResolver cr = context.getContentResolver();
        //sdk大于8的时候,launcher2的设置查找
        String AUTHORITY = "com.android.launcher2.settings";
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"},
                "title=?", new String[]{applicationName}, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        if (c != null) {
            c.close();
        }
        //如果存在先关闭cursor，再返回结果
        if (isInstallShortcut) {
            return isInstallShortcut;
        }
        //android.os.Build.VERSION.SDK_INT < 8时
        AUTHORITY = "com.android.launcher.settings";
        CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?",
                new String[]{applicationName}, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        if (c != null) {
            c.close();
        }
        return isInstallShortcut;
    }


    /**
     * 获取当前app的应用程序名称
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }


    /**
     * 使用前提 target只在orgString中出现一次
     *
     * @param orgString
     * @param target
     * @param a         index 0 背景颜色  1，字体颜色
     * @return
     */
    public static SpannableString getStringStyle(String orgString, String target, String... a) {

        SpannableString spannableString = new SpannableString(orgString);
        int index1 = orgString.indexOf(target);
        int index2 = orgString.indexOf(target) + target.length();
        if (a.length > 0) {
            spannableString.setSpan(new BackgroundColorSpan(Color.parseColor(a[0])), index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (a.length > 1) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(a[1])), index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    public static SpannableString getStringStyle(String orgString, String target, String b) {

        SpannableString spannableString = new SpannableString(orgString);
        int index1 = orgString.indexOf(target);
        int index2 = orgString.indexOf(target) + target.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(b)), index1, index2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return spannableString;
    }

    public static SpannableString getBoldStyle(SpannableString spannableString, String orgString, String target) {

        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        int index1 = orgString.indexOf(target);
        spannableString.setSpan(styleSpan_B, index1, index1 + target.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }


}
