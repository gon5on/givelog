package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.MemberListAdapter;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.dialog.GuideDialog;
import jp.co.e2.givelog.model.Member;
import jp.co.e2.givelog.model.MemberDao;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * メンバー一覧アクテビティ
 * 
 * @access public
 */
public class MemberListActivity extends BaseActivity
{
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
		setContentView(R.layout.activity_member_list);

		//初期化
		setNull();

		//ヘッダのボタンセット
		setHeaderButton();

		//初回起動判定
		if (Utils.getIntFromPref(getApplication(), "TWICE_FLG", 0) == 0) {
			new GuideDialog(this).show();
			Utils.saveIntToPref(getApplication(), "TWICE_FLG", 1);
		}
	}

	/**
	 * onResume
	 * 
	 * @return void
	 * @access public
	 */
	@Override
	public void onResume()
	{
		super.onResume();

		//初期化
		setNull();

		//人物一覧
		SQLiteDatabase db = helper.getWritableDatabase();
		MemberDao memberDao = new MemberDao(getApplicationContext());
		ArrayList<Member> member_list = memberDao.selectMemberList(db);
		db.close();

		//アダプターをセット
		ListView listViewMember = (ListView) findViewById(R.id.listViewMember);
		MemberListAdapter adapter = new MemberListAdapter(this, R.layout.part_member_list, member_list);
		listViewMember.setAdapter(adapter);

		//背景画像消える対策
		listViewMember.setScrollingCacheEnabled(false);

		//イベント
		listViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView listView = (ListView) parent;
				Member item = (Member) listView.getAdapter().getItem(position);

				Intent i = new Intent(getApplicationContext(), MemberDetailActivity.class);
				i.putExtra("ID", item.getId());
				startActivity(i);
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
	 * ヘッダのボタンセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setHeaderButton()
	{
		Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
		Button buttonMemberAdd = (Button) findViewById(R.id.buttonMemberAdd);
		Button buttonPresentAdd = (Button) findViewById(R.id.buttonPresentAdd);

		//設定
		buttonSettings.setVisibility(View.VISIBLE);
		buttonSettings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
				startActivity(i);
			}
		});

		//人物追加
		buttonMemberAdd.setVisibility(View.VISIBLE);
		buttonMemberAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MemberAddActivity.class);
				startActivity(i);
			}
		});

		//プレゼント追加
		buttonPresentAdd.setVisibility(View.VISIBLE);
		buttonPresentAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PresentAddActivity.class);
				startActivity(i);
			}
		});
	}

	/**
	 * 初期化
	 * 
	 * @return void
	 * @access private
	 */
	private void setNull()
	{
	}
}