package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.ErrorDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * エラー表示ダイアログクラス
 * 
 * @access public
 */
public class ErrorDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param String msg テキスト
     * @return ErrorDialog
     * @access public
     */
    public static ErrorDialog getInstance(String msg)
    {
        ErrorDialog dialog = new ErrorDialog();

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
        Dialog dialog = createDefaultDialog(R.layout.dialog_error);

        //文言セット
        TextView textViewMsg = (TextView) dialog.findViewById(R.id.textViewMsg);
        textViewMsg.setText(msg);

        //OKボタン
        Button alertButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
        alertButtonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickErrorDialogOk();
                }
                dismiss();
            }
        });

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickErrorDialogClose();
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
        public void onClickErrorDialogOk();

        public void onClickErrorDialogClose();
    }
}