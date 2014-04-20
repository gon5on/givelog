package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * メモ表示ダイアログクラス
 * 
 * @access public
 */
public class MemoDialog extends Dialog
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @param String msg 表示する文言
	 * @access public
	 */
	public MemoDialog(Context context, String msg)
	{
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_memo);

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
		TextView textViewMemo = (TextView) findViewById(R.id.textViewMemo);
		textViewMemo.setText(msg);
	}
}