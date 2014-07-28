package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.PhotoSelectDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 画像選択ダイアログクラス
 * 
 * @access public
 */
public class PhotoSelectDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param Integer imgExistFlag 画像が存在しているかフラグ
     * @return PhotoSelectDialog
     * @access public
     */
    public static PhotoSelectDialog getInstance(Integer imgExistFlag)
    {
        PhotoSelectDialog dialog = new PhotoSelectDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("imgExistFlag", imgExistFlag);
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
        Integer imgExistFlag = getArguments().getInt("imgExistFlag");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_photo_select);

        //カメラ起動ボタン
        Button buttonCamera = (Button) dialog.findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogCamera();
                }
                dismiss();
            }
        });

        //ギャラリーボタン
        Button buttonGallery = (Button) dialog.findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogGallery();
                }
                dismiss();
            }
        });

        //画像削除ボタン
        Button buttonPhotoDelete = (Button) dialog.findViewById(R.id.buttonPhotoDelete);
        buttonPhotoDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogDelete();
                }
                dismiss();
            }
        });

        //画像削除ボタンは、画像の有無で表示を切り替える
        buttonPhotoDelete.setVisibility((imgExistFlag == 1) ? View.VISIBLE : View.GONE);

        //閉じるボタン
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogClose();
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
        public void onClickPhotoSelectDialogCamera();

        public void onClickPhotoSelectDialogGallery();

        public void onClickPhotoSelectDialogDelete();

        public void onClickPhotoSelectDialogClose();
    }
}