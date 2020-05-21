package com.delcache.hera.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.view.ConfirmView;

public class ProgressDialog extends BaseDialog implements DialogInterface.OnKeyListener {

    @BindView(R.id.process_confirm)
    ConfirmView processConfirm;

    Activity baseActivity;

    public ProgressDialog(Context context) {
        super(context, R.style.TranslucentMenuDialog);
        this.setContentView(R.layout.dialog_process);
        ButterKnife.bind(this);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.setOnKeyListener(this);
        baseActivity =  (Activity)context;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void show() {
        super.show();
        processConfirm.animatedWithState(ConfirmView.State.Progressing);
    }

    @Override
    public void dismiss() {
        if (baseActivity != null && !baseActivity.isFinishing()) {
            super.dismiss();    //调用超类对应方法
        }
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        return true;
    }
}
