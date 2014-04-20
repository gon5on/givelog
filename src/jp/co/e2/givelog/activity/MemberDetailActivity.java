package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.PresentListAdapter;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.dialog.ConfirmDialog;
import jp.co.e2.givelog.dialog.MemoDialog;
import jp.co.e2.givelog.dialog.PresentDetailDialog;
import jp.co.e2.givelog.model.Member;
import jp.co.e2.givelog.model.MemberDao;
import jp.co.e2.givelog.model.Present;
import jp.co.e2.givelog.model.PresentDao;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * メンバー詳細アクテビティ
 * 
 * @access public
 */
public class MemberDetailActivity extends BaseActivity
{
	private static PresentListAdapter adapter;			//アダプタ－

	private static PresentDetailDialog detail_dialog;	//詳細ダイアログ
	private static ConfirmDialog member_del_dialog;		//人物削除確認ダイアログ
	private static ConfirmDialog present_del_dialog;	//プレゼント削除確認ダイアログ
	private static MemoDialog memo_dialog;				//メモダイアログ

	private static MemberDao memberDao;					//メンバーDao
	private static PresentDao presentDao;				//プレセントDao

	private static Member member;						//対象のMemberデータ
	private static ArrayList<Present> presents;			//対象のプレゼントデータ
	private static Integer member_id;					//人物ID
	private static Integer present_id;					//選択されたプレゼントID
	private static Integer position;					//選択されたポジション

	private static Integer type;						//あげるかもらうかフラグ

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
		setContentView(R.layout.activity_member_detail);

		//初期化
		setNull();

		//ヘッダのボタンセット
		setHeaderButton();

		//引数のID取得
		Intent i = getIntent();
		member_id = i.getIntExtra("ID", 0);
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

		//人物情報取得
		SQLiteDatabase db = helper.getWritableDatabase();
		memberDao = new MemberDao(getApplicationContext());
		member = memberDao.selectMemberDetail(db, member_id);
		db.close();

		//人物情報をビューにセット
		setMemberData();

		//背景画像消える対策
		ListView listViewPresent = (ListView) findViewById(R.id.listViewPresent);
		listViewPresent.setScrollingCacheEnabled(false);

		//プレゼント情報取得、ビューにセット
		setPresentData();

		//あげるボタンセット
		Button buttonGive = (Button) findViewById(R.id.buttonGive);
		buttonGive.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				type = 1;
				setPresentData();
				setTab();
			}
		});

		//もらうボタンセット
		Button buttonGave = (Button) findViewById(R.id.buttonGave);
		buttonGave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				type = 2;
				setPresentData();
				setTab();
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
	 * 人物情報をビューにセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setMemberData()
	{
		//写真セット
		if (member.getPhoto() != null) {
			Resources res = getResources();
			Integer height = res.getDimensionPixelSize(R.dimen.tmp_photo_height);
			Integer width = res.getDimensionPixelSize(R.dimen.tmp_photo_width);

			String img_path = file_dir + "/" + member.getPhoto();
			ImgUtils imgUtils = new ImgUtils(getApplicationContext(), img_path);

			ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
			imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
			imageViewPhoto.setEnabled(true);

			//タップ時の画像拡大
			imageViewPhoto.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), ShowPhotoActivity.class);
					i.putExtra("PHOTO", member.getPhoto());
					startActivity(i);
				}
			});
		} else {
			ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
			imageViewPhoto.setImageResource(R.drawable.no_image);
			imageViewPhoto.setEnabled(false);
		}

		//名前
		TextView textViewName = (TextView) findViewById(R.id.textViewName);
		textViewName.setText(member.getName());

		//ふりがな
		TextView textViewKana = (TextView) findViewById(R.id.textViewKana);

		if (member.getKana().length() != 0) {
			textViewKana.setText(member.getKana());
			textViewKana.setVisibility(View.VISIBLE);
		} else {
			textViewKana.setVisibility(View.GONE);
		}

		//関係性
		TextView textViewRelation = (TextView) findViewById(R.id.textViewRelation);
		textViewRelation.setText(member.getRelationName());

		GradientDrawable shape = (GradientDrawable) textViewRelation.getBackground();
		textViewRelation.setBackgroundDrawable(shape);
		shape.setColor(member.getLabel());

		//誕生日
		TextView textViewBirth = (TextView) findViewById(R.id.textViewBirth);

		if (member.getBirth() != null) {
			textViewBirth.setText(member.getBirthAge());
			textViewBirth.setVisibility(View.VISIBLE);
		} else {
			textViewBirth.setVisibility(View.GONE);
		}

		//メモ
		memo_dialog = new MemoDialog(this, member.getMemo());
		Button buttonMemo = (Button) findViewById(R.id.buttonMemo);

		if (member.getMemo().length() != 0) {
			buttonMemo.setVisibility(View.VISIBLE);
			buttonMemo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					memo_dialog.show();
				}
			});
		} else {
			buttonMemo.setVisibility(View.GONE);
		}
	}

	/**
	 * プレゼント情報取得、ビューにセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setPresentData()
	{
		//プレゼントデータ取得
		SQLiteDatabase db = helper.getWritableDatabase();
		presentDao = new PresentDao(getApplicationContext());
		presents = presentDao.selectPresentList(db, member_id, type);
		db.close();

		//プレゼント情報をビューにセット
		ListView listViewPresent = (ListView) findViewById(R.id.listViewPresent);

		adapter = new PresentListAdapter(this, R.layout.part_present_list, presents);
		adapter.setType(type);

		listViewPresent.setAdapter(adapter);

		//イベントリスナーセット
		listViewPresent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int l_position, long id) {
				position = l_position;
				present_id = presents.get(position).getId();
				showDetailDialog();
			}
		});
	}

	/**
	 * タブの見た目を切り替える
	 * 
	 * @return void
	 * @access private
	 */
	private void setTab()
	{
		LinearLayout layout_on;
		LinearLayout layout_off;

		if (type == 1) {
			layout_on = (LinearLayout) findViewById(R.id.linearLayoutGive);
			layout_off = (LinearLayout) findViewById(R.id.linearLayoutGave);

		} else {
			layout_on = (LinearLayout) findViewById(R.id.linearLayoutGave);
			layout_off = (LinearLayout) findViewById(R.id.linearLayoutGive);

		}

		layout_on.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_on));
		layout_off.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_off));
	}

	/**
	 * 編集処理
	 * 
	 * @return void
	 * @access private
	 */
	private void editPresent()
	{
		Intent intent = new Intent(MemberDetailActivity.this, PresentAddActivity.class);
		intent.putExtra("ID", present_id);
		startActivity(intent);

		detail_dialog.dismiss();
	}

	/**
	 * 人物削除処理
	 * 
	 * @return void
	 * @access private
	 */
	private void deleteMember()
	{
		//DB削除
		SQLiteDatabase db = helper.getWritableDatabase();
		memberDao.delete(db, file_dir, member_id, member.getName());
		db.close();

		//画像削除
		deleteImg(member_id, "member");

		member_del_dialog.dismiss();

		Utils.showToast(getApplication(), "削除しました。");
		finish();
	}

	/**
	 * プレゼント削除
	 * 
	 * @return void
	 * @access private
	 */
	private void deletePresent()
	{
		//DB削除
		SQLiteDatabase db = helper.getWritableDatabase();
		presentDao.delete(db, present_id);
		db.close();

		//画像削除
		deleteImg(present_id, "present");

		//表示更新
		adapter.remove(presents.get(position));

		present_del_dialog.dismiss();
		detail_dialog.dismiss();

		Utils.showToast(getApplicationContext(), "削除しました。");
	}

	/**
	 * ヘッダのボタンセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setHeaderButton()
	{
		//戻る
		Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
		buttonReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		//削除
		Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showMemberDeleteDialog();
			}
		});

		//編集
		Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MemberAddActivity.class);
				intent.putExtra("ID", member.getId());
				startActivity(intent);
			}
		});

		//プレゼント追加
		Button buttonPresentAdd = (Button) findViewById(R.id.buttonPresentAdd);
		buttonPresentAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PresentAddActivity.class);
				i.putExtra("MEMBER_ID", member_id);
				i.putExtra("TYPE", type);
				startActivity(i);
			}
		});
	}

	/**
	 * 詳細ダイアログを開く
	 * 
	 * @return void
	 * @access private
	 */
	private void showDetailDialog()
	{
		if (detail_dialog == null) {
			detail_dialog = new PresentDetailDialog(this, file_dir);

			//編集ボタン
			Button detailButtonEdit = (Button) detail_dialog.findViewById(R.id.buttonEdit);
			detailButtonEdit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					editPresent();
				}
			});

			//削除ボタン
			Button detailButtonDelete = (Button) detail_dialog.findViewById(R.id.buttonDelete);
			detailButtonDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					showPresentDeleteDialog();
				}
			});

			//画像拡大表示
			ImageView imageViewPhoto = (ImageView) detail_dialog.findViewById(R.id.imageViewPhoto);
			imageViewPhoto.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), ShowPhotoActivity.class);
					i.putExtra("PHOTO", presents.get(position).getPhoto());
					startActivity(i);
				}
			});
		}

		detail_dialog.setContent(presents.get(position));
		detail_dialog.show();
	}

	/**
	 * 人物削除確認ダイアログ表示
	 * 
	 * @return void
	 * @access private
	 */
	private void showMemberDeleteDialog()
	{
		if (member_del_dialog == null) {
			member_del_dialog = new ConfirmDialog(this, "この人物を削除してよろしいですか？");

			Button alertButtonOk = (Button) member_del_dialog.findViewById(R.id.buttonOk);
			alertButtonOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					deleteMember();
				}
			});
		}

		member_del_dialog.show();
	}

	/**
	 * プレゼント削除確認ダイアログ表示
	 * 
	 * @return void
	 * @access private
	 */
	private void showPresentDeleteDialog()
	{
		if (present_del_dialog == null) {
			present_del_dialog = new ConfirmDialog(this, "削除してよろしいですか？");

			//OKボタン
			Button confirmButtonOk = (Button) present_del_dialog.findViewById(R.id.buttonOk);
			confirmButtonOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					deletePresent();
				}
			});
		}

		present_del_dialog.show();
	}

	/**
	 * 初期化
	 * 
	 * @return void
	 * @access private
	 */
	private void setNull()
	{
		adapter = null;
		detail_dialog = null;
		memo_dialog = null;
		member_del_dialog = null;
		present_del_dialog = null;
		memberDao = null;
		presentDao = null;
		member = null;
		presents = null;
		member_id = null;
		position = null;
		type = 1;
	}
}