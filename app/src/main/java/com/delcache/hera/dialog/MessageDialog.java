package com.delcache.hera.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.view.ConfirmView;

public class MessageDialog extends BaseDialog implements ConfirmView.AnimationSuccess,
        DialogInterface.OnKeyListener {

    @BindView(R.id.message_confirm)
    ConfirmView messageConfirm;
    @BindView(R.id.message_content)
    TextView messageContent;

    private SuccessInterface successInterface;

    public MessageDialog(Context context) {
        super(context, R.style.TranslucentMenuDialog);
        this.setContentView(R.layout.dialog_message);
        ButterKnife.bind(this);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.setOnKeyListener(this);
    }

    @Override
    public void show() {
        super.show();
        messageConfirm.animatedWithState(ConfirmView.State.Progressing);
        messageConfirm.setConfirmSuccess(this);
    }

    @Override
    public void dismiss() {
        messageContent.setText("");
        messageContent.setVisibility(View.GONE);
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public void setContent(final String content) {
        messageContent.setText(content);
        messageContent.setVisibility(View.VISIBLE);
    }

    public void fail(String content) {
        messageContent.setText(content);
        messageConfirm.animatedWithState(ConfirmView.State.Fail);
    }

    public void success(String content) {
        messageContent.setText(content);
        messageConfirm.animatedWithState(ConfirmView.State.Success);
    }

    public void setSuccessInterface(SuccessInterface successInterface) {
        this.successInterface = successInterface;
    }

    @Override
    public void animationSuccess(ConfirmView.State mCurrentState) {

        dismiss();
        if (mCurrentState == ConfirmView.State.Success) {
            successInterface.success();
        }
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return true;
    }

    public interface SuccessInterface {

        void error(String error);

        void success();

    }
}
