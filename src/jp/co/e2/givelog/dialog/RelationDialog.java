package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.RelationDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * グループ編集ダイアログ
 * 
 * @access public
 */
public class RelationDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param Integer id ID
     * @param IStrng name グループ名
     * @return ErrorDialog
     * @access public
     */
    public static RelationDialog getInstance(Integer id, String name)
    {
        RelationDialog dialog = new RelationDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", name);
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
        Integer id = getArguments().getInt("id");
        String name = getArguments().getString("name");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_relation);

        //ボタンセット
        setButton(dialog);

        //編集か新規作成化かで表示を切り替える
        controlCreateOrEdit(dialog, id, name);

        return dialog;
    }

    /**
     * ボタンをセット
     * 
     * @param Dialog dialog
     * @return void
     * @access public
     */
    private void setButton(Dialog dialog)
    {
        //OKボタン
        Button buttonOk = (Button) dialog.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickRelationDialogOk();
                }
                dismiss();
            }
        });

        //削除ボタン
        Button buttonDelete = (Button) dialog.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickRelationDialogClose();
                }
                dismiss();
            }
        });

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickRelationDialogClose();
                }
                dismiss();
            }
        });
    }

    /**
     * 新規登録か編集かで画面を変える
     * 
     * @param Dialog dialog
     * @param Integer id グループID
     * @param String name グループ名
     * @return void
     * @access public
     */
    public void controlCreateOrEdit(Dialog dialog, Integer id, String name)
    {
        LinearLayout linearLayoutCreate = (LinearLayout) dialog.findViewById(R.id.linearLayoutCreate);
        LinearLayout linearLayoutEdit = (LinearLayout) dialog.findViewById(R.id.linearLayoutEdit);

        //新規追加
        if (id == 0) {
            linearLayoutCreate.setVisibility(View.VISIBLE);
            linearLayoutEdit.setVisibility(View.GONE);
        }
        //編集
        else {
            linearLayoutCreate.setVisibility(View.GONE);
            linearLayoutEdit.setVisibility(View.VISIBLE);

            //未分類の場合、削除ボタンは無効且つ、名前編集不可にする
            Button buttonDelete = (Button) dialog.findViewById(R.id.buttonDelete);
            TextView textEditName = (TextView) dialog.findViewById(R.id.textEditName);

            if (id != null && id == 1) {
                buttonDelete.setEnabled(false);
                textEditName.setEnabled(false);
                textEditName.setFocusable(false);
            } else {
                buttonDelete.setEnabled(true);
                textEditName.setEnabled(true);
                textEditName.setFocusable(true);
                textEditName.setFocusableInTouchMode(true);
            }
        }
    }

    /**
     * コールバックリスナー
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickRelationDialogOk();

        public void onClickRelationDialogDelete();

        public void onClickRelationDialogClose();
    }
}