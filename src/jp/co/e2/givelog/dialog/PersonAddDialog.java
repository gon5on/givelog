package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.PersonAddDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * カスタム人物追加ダイアログクラス
 * 
 * @access public
 */
public class PersonAddDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param String person カスタム人物名
     * @return PersonAddDialog
     * @access public
     */
    public static PersonAddDialog getInstance(String person)
    {
        PersonAddDialog dialog = new PersonAddDialog();

        Bundle bundle = new Bundle();
        bundle.putString("person", person);
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
        String person = getArguments().getString("person");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_person_add);

        //カスタム人物名セット
        final TextView editTextPerson = (TextView) dialog.findViewById(R.id.editTextPerson);
        editTextPerson.setText(person);

        //OKボタン
        Button alertButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
        alertButtonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPersonAddDialogOk(editTextPerson.getText().toString());
                }
                dismiss();
            }
        });

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPersonAddDialogClose();
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
        public void onClickPersonAddDialogOk(String person);

        public void onClickPersonAddDialogClose();
    }
}