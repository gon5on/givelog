package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * アバウト用ダイアログクラス
 * 
 * @access public
 */
public class AboutDialog extends Dialog
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context
	 * @access public
	 */
	public AboutDialog(Context context)
	{
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_about);

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}