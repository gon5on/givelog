package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.ColorPickerDialog.CallbackListener;
import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;

/**
 * カラーピッカーダイアログクラス
 * 
 * @access public
 */
public class ColorPickerDialog extends AppDialog<CallbackListener>
{
    //ボタンリソースID
    private Integer[] BUTTON_RES_ID = {
            R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05,
            R.id.button06, R.id.button07, R.id.button08, R.id.button09
    };

    //色リソースID
    private Integer[] COLOR_RES_ID = {
            R.color.group1, R.color.group2, R.color.group3, R.color.group4, R.color.group5,
            R.color.group6, R.color.group7, R.color.group8, R.color.group9
    };

    /**
     * インスタンスを返す
     * 
     * @param Integer id ID
     * @param IStrng name グループ名
     * @return ErrorDialog
     * @access public
     */
    public static RelationDialog getInstance()
    {
        RelationDialog dialog = new RelationDialog();

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
        Dialog dialog = createDefaultDialog(R.layout.dialog_relation);

        //ボタンセット
        setButton(dialog);

        return dialog;
    }

    /**
     * ボタンセット
     * 
     * @param Dialog dialog
     * @return void
     * @access private
     */
    private void setButton(Dialog dialog)
    {
        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickColorPickerDialogClose();
                }
                dismiss();
            }
        });

        //各色選択ボタン
        final SparseIntArray colorArray = new SparseIntArray();

        for (int i = 0; i < BUTTON_RES_ID.length; i++) {
            //オンクリックメソッド内でアクセスできるように、ボタンIDをキーとした色マップを生成しておく
            colorArray.put(BUTTON_RES_ID[i + 1], COLOR_RES_ID[i + 1]);

            //ボタンイベント
            Button button = (Button) dialog.findViewById(BUTTON_RES_ID[i + 1]);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mCallbackListener != null) {
                        Integer color = colorArray.get(v.getId());
                        mCallbackListener.onClickColorPickerDialogSelect(color);
                    }
                    dismiss();
                }
            });

            //ボタン背景色セット
            GradientDrawable shape = (GradientDrawable) button.getBackground();
            button.setBackgroundDrawable(shape);
            shape.setColor(getResources().getColor(COLOR_RES_ID[i + 1]));
        }
    }

    /**
     * コールバックリスナー
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickColorPickerDialogClose();

        public void onClickColorPickerDialogSelect(Integer color);
    }
}