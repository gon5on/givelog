package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.MemoDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * メモ表示ダイアログクラス
 * 
 * @access public
 */
public class MemoDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param String msg メモ
     * @return MemoDialog
     * @access public
     */
    public static MemoDialog getInstance(String memo)
    {
        MemoDialog dialog = new MemoDialog();

        Bundle bundle = new Bundle();
        bundle.putString("memo", memo);
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
        String memo = getArguments().getString("memo");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_memo);

        //文言セット
        TextView textViewMemo = (TextView) dialog.findViewById(R.id.textViewMemo);
        textViewMemo.setText(memo);

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickMemoDialogClose();
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
        public void onClickMemoDialogClose();
    }
}