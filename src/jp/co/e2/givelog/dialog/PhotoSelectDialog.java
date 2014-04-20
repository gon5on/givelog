package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * 画像選択ダイアログクラス
 * 
 * @access public
 */
public class PhotoSelectDialog extends Dialog
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public PhotoSelectDialog(Context context)
	{
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_photo_select);

		//カメラ起動ボタン
		Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
		buttonCamera.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		//ギャラリーボタン
		Button buttonGallery = (Button) findViewById(R.id.buttonGallery);
		buttonGallery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		//画像削除ボタン
		Button buttonPhotoDelete = (Button) findViewById(R.id.buttonPhotoDelete);
		buttonPhotoDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}