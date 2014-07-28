package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.GuideDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 初回起動時ガイド表示ダイアログクラス
 * 
 * @access public
 */
public class GuideDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @return GuideDialog
     * @access public
     */
    public static GuideDialog getInstance()
    {
        GuideDialog dialog = new GuideDialog();

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
        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_guide);

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickGuideDialogClose();
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
        public void onClickGuideDialogClose();
    }
}