package jp.co.e2.givelog.activity;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.AboutDialog;
import jp.co.e2.givelog.dialog.GuideDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 設定アクテビティ
 * 
 * @access public
 */
public class SettingsActivity extends BaseActivity
{
	private static AboutDialog about_dialog;		//アバウト用ダイアログ	
	private static GuideDialog guide_dialog;		//ガイド用ダイアログ	

	/**
	 * onCreate
	 * 
	 * @param Bundle savedInstanceState
	 * @return void
	 * @access public
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//初期化
		setNull();

		//ヘッダのボタン
		setHeaderReturnButton();

		//アバウトダイアログ作成
		about_dialog = new AboutDialog(this);

		//ガイドダイアログ作成
		guide_dialog = new GuideDialog(this);

		//グループ設定ボタン
		Button buttonGroup = (Button) findViewById(R.id.buttonGroup);
		buttonGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, RelationListActivity.class);
				startActivity(intent);
			}
		});

		//ヘルプ設定ボタン
		Button helpGroup = (Button) findViewById(R.id.buttonHelp);
		helpGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				guide_dialog.show();
			}
		});

		//アバウト設定ボタン
		Button aboutGroup = (Button) findViewById(R.id.buttonAbout);
		aboutGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				about_dialog.show();
			}
		});
	}

	/**
	 * onDestroy
	 * 
	 * @return void
	 * @access protected
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		//初期化
		setNull();
	}

	/**
	 * 初期化
	 * 
	 * @return void
	 * @access private
	 */
	private void setNull()
	{
		about_dialog = null;
	}
}