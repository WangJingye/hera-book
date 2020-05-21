package com.delcache.hera.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.delcache.hera.activity.BaseActivity;
import com.delcache.hera.utils.MyLogger;

import java.util.ArrayList;
import java.util.Map;

/**
 * 主控制器类
 *
 */
public class ActivityHelper {

	protected static MyLogger logger = MyLogger.getLogger();
	private static ActivityHelper _controller = null;
	private ArrayList<Context> cxtList; // Activity队列，仅供程序退出时使用。
	private BaseActivity activity = null;
	private Map<String, Object> curContent;

	private ActivityHelper() {
		cxtList = new ArrayList<Context>();
	}

	public static ActivityHelper getInstance() {
		if (_controller == null) {
			_controller = new ActivityHelper();
		}
		return _controller;
	}

	public void setCurrentActivity(Context cxt) {
		if (cxt instanceof BaseActivity)
			this.activity = (BaseActivity) cxt;
	}

	public BaseActivity getCurrentActivity() {
		return this.activity;
	}

	public ArrayList<Context> getCxtList() {
		return cxtList;
	}

	public void launcherBack() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		activity.startActivity(i);
	}

	public void clearActivityStack() {

		for (Context c : cxtList) {
			if (c != null) {
				((Activity) c).finish();
			}
		}
		cxtList.clear();
		activity = null;
		_controller = null;
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void clearOtherActivity(Context context) {

		for (Context c : cxtList) {
			if (c != null && c != context) {
				((Activity) c).finish();
			}
		}
		cxtList.clear();
	}

	public void finishActivity(Class<?> name) {

		for (Context c : cxtList) {
			if (c != null && c.getClass() == name ) {
				((Activity) c).finish();
			}
		}
	}

	public void exitAppDalvik() {
		System.exit(0);
	}

	public void AppHide() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		i.addCategory(Intent.CATEGORY_HOME);
		activity.startActivity(i);
	}

	public Map<String, Object> getCurContent() {
		return curContent;
	}

	public void setCurContent(Map<String, Object> curContent) {
		this.curContent = curContent;
	}

}
