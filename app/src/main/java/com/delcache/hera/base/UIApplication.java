package com.delcache.hera.base;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.delcache.hera.BuildConfig;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.MyLogger;
import com.orm.SugarContext;

/**
 * Created by jack.yang on 15/11/13.
 */
public class UIApplication extends Application implements Thread.UncaughtExceptionHandler {

    protected static MyLogger logger = MyLogger.getLogger();

    private static UIApplication sInstance = null;

    public UIApplication() {
        sInstance = this;
    }

    public static UIApplication getInstance() {
        if (sInstance == null) {
            sInstance = new UIApplication();
        }
        return sInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);

        String rootPath = Environment.getExternalStorageDirectory().getPath()
                + "/Hera";
        init(rootPath);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    protected void init(String rootPath) {
        initFrameworkEvn();
        initStore(rootPath);
        initDeviceInfo();
    }

    private void initFrameworkEvn() {
        Context context = this.getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.e("========onLowMemory========");
    }

    /**
     * 初始化设备等信息
     */
    private void initDeviceInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        logger.d("screenWidth = " + screenWidth);
        logger.d("screenHeight = " + screenHeight);
        ConstantStore.getInstance().setScreenWidth(screenWidth);
        ConstantStore.getInstance().setScreenHeight(screenHeight);

        Context c = this.getApplicationContext();
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pinfo = pm.getPackageInfo(c.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            String versionName = pinfo.versionName;
            ConstantStore.getInstance().setVersion(versionName);
            logger.d("versionName = " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化存储路径
     */
    private void initStore(String rootPath) {
        ConstantStore.getInstance().setRootPath(rootPath);
        ConstantStore.getInstance()
                .setImageCachePath(rootPath + "/.cache_pic/");
        ConstantStore.getInstance().setFileDownloadPath(
                rootPath + "/.mydownload/");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        showError();
        ex.printStackTrace();
        if (!BuildConfig.DEBUG) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(
                    getApplicationContext(), 0, intent, 0);
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    restartIntent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void showError() {

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), getString(R.string.error_and_restart), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }
}
