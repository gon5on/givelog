package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.ConfirmDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 確認用ダイアログクラス
 * 
 * @access public
 */
public class ConfirmDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param String msg テキスト
     * @return ConfirmDialog
     * @access public
     */
    public static ConfirmDialog getInstance(String msg)
    {
        ConfirmDialog dialog = new ConfirmDialog();

        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * onCreateDialog
     * 
     * @param Bundle savedInstanceState
     * @return Dialog
     * @access public
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //bundleから値を取り出す
        String msg = getArguments().getString("msg");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_confirm);

        //文言セット
        TextView textViewMsg = (TextView) dialog.findViewById(R.id.textViewMsg);
        textViewMsg.setText(msg);

        //OKボタン
        Button alertButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
        alertButtonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogOk();
                }
                dismiss();
            }
        });

        //キャンセルボタン
        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogCancel();
                }
                dismiss();
            }
        });

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogClose();
                }
                dismiss();
            }
        });

        return dialog;
    }

    /**
     * コールバックリスナー
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickConfirmDialogOk();

        public void onClickConfirmDialogCancel();

        public void onClickConfirmDialogClose();
    }
}