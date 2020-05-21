package com.delcache.hera.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.delcache.hera.R;
import com.delcache.hera.utils.Constants;

public abstract class BaseDialog extends Dialog {

	protected Context mContext;

	protected LayoutInflater mInflater;

	public BaseDialog(Context context) {
		super(context);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setWindowAnimations(R.style.Theme_Dialog);
	}

	protected BaseDialog(Context context, boolean cancelable,
                         OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setWindowAnimations(R.style.Theme_Dialog);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setWindowAnimations(R.style.Theme_Dialog);
	}

     void onOkDialog(){}

    void onCloseDialog(){}

	public boolean onKeyDown(int keyCode, KeyEvent event) {


		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * @param keyCode
	 * @param event
	 * @return
	 * @function KEYCODE_ENTER 拦截enter按钮 等于点击确定  KEYCODE_BACK 拦截 返回键等于 点击标题上的取消
	 */
	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

		Log.e("keycode:baselog:",""+keyCode);
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            onOkDialog();
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
			onCloseDialog();
		}
        return super.onKeyUp(keyCode, event);
    }

	/**
	 * 弹出toast message
	 *
	 * @param msg
	 */
	public void showMsg(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void show() {
		if (!Constants.isShowingTaskDialog ) {
			Constants.isShowingTaskDialog = true;
			super.show();
		}
	}

	@Override
	public void dismiss() {
		Constants.isShowingTaskDialog = false;
		super.dismiss();
	}
}
