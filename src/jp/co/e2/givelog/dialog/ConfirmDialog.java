package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 削除確認用ダイアログクラス
 * 
 * @access public
 */
public class ConfirmDialog extends Dialog
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @param String msg 表示する文言
	 * @access public
	 */
	public ConfirmDialog(Context context, String msg)
	{
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_confirm);

		//OKボタン  コールバック関数の使い方がわからないので、呼び出し元で実装する
		/*Button alertButtonOk = (Button) findViewById(R.id.buttonOk);
		alertButtonOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		})*/;

		//キャンセルボタン
		Button confirmButtonCancel = (Button) findViewById(R.id.buttonCancel);
		confirmButtonCancel.setOnClickListener(new View.OnClickListener() {
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

		//文言セット
		setMsg(msg);
	}

	/**
	 * 表示する文言をセットする
	 * 
	 * @param String msg 表示する文言
	 * @return void
	 * @access public
	 */
	public void setMsg(String msg)
	{
		TextView alertTextViewMsg = (TextView) findViewById(R.id.textViewMsg);
		alertTextViewMsg.setText(msg);
	}
}