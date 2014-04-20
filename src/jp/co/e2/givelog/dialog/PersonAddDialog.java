package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * カスタム人物追加ダイアログクラス
 * 
 * @access public
 */
public class PersonAddDialog extends Dialog
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public PersonAddDialog(Context context)
	{
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_person_add);

		//OKボタン
		/*Button buttonOk = (Button) findViewById(R.id.buttonOk);
		buttonOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});*/

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * 表示するカスタム人物名をセットする
	 * 
	 * @param String text カスタム人物名
	 * @return void
	 * @access public
	 */
	public void setText(String text)
	{
		TextView editTextPerson = (TextView) findViewById(R.id.editTextPerson);
		editTextPerson.setText(text);
	}

	/**
	 * カスタム人物名を取得する
	 * 
	 * @return String 人物名
	 * @access public
	 */
	public String getText()
	{
		TextView editTextPerson = (TextView) findViewById(R.id.editTextPerson);
		return editTextPerson.getText().toString();
	}
}