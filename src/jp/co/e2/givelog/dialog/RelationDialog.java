package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * グループ編集ダイアログ
 * 
 * @access public
 */
public class RelationDialog extends Dialog
{
	private Context context;		//コンテキスト

	/**
	 * コンストラクタ
	 * 
	 * @param Context my_context コンテキスト
	 * @access public
	 */
	public RelationDialog(Context my_context)
	{
		super(my_context);

		context = my_context;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_relation);

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * 新規登録か編集かで画面を変える
	 * 
	 * @param Integer id グループID
	 * @return void
	 * @access public
	 */
	public void setCondition(Integer id)
	{
		LinearLayout linearLayoutCreate = (LinearLayout) findViewById(R.id.linearLayoutCreate);
		LinearLayout linearLayoutEdit = (LinearLayout) findViewById(R.id.linearLayoutEdit);

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
			Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
			TextView textEditName = (TextView) findViewById(R.id.textEditName);

			if (id != null && id == 1) {
				buttonDelete.setEnabled(false);
				textEditName.setEnabled(false);
				textEditName.setFocusable(false);
			} else {
				buttonDelete.setEnabled(true);
				textEditName.setEnabled(true);
				textEditName.setFocusable(true);
				textEditName.setFocusableInTouchMode(true);		//setFocusable=falseにした場合はこれもtrueにしないといけないらしい
			}
		}
	}
}