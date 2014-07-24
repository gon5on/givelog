package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.RelationListAdapter;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.dialog.ColorPickerDialog;
import jp.co.e2.givelog.dialog.ConfirmDialog;
import jp.co.e2.givelog.dialog.ErrorDialog;
import jp.co.e2.givelog.dialog.RelationDialog;
import jp.co.e2.givelog.entity.RelationEntity;
import jp.co.e2.givelog.model.RelationDao;
import jp.co.e2.givelog.validate.Validate;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * グループ一覧アクテビティ
 * 
 * @access public
 */
public class RelationListActivity extends BaseActivity
{
	private static RelationListAdapter adapter;			//アダプタ－

	private static RelationDialog input_dialog;			//入力用ダイアログ
	private static ConfirmDialog confirm_dialog;		//削除確認ダイアログ
	private static ErrorDialog error_dialog;			//入力エラーダイアログ
	private static ColorPickerDialog color_dialog;		//カラーピッカーダイアログ

	private static RelationDao relationDao;				//グループDao
	private static ArrayList<RelationEntity> relations;		//グループ一覧

	private static Integer selected_position;			//選択済ポジション
	private static Integer selected_color;				//カラーピッカー選択済み色

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
		setContentView(R.layout.activity_relation_list);

		//初期化
		setNull();

		setHeaderButton();											//ヘッダのボタンセット

		relationDao = new RelationDao(getApplicationContext());		//関係性Dao
	}

	/**
	 * onResume
	 * 
	 * @return void
	 * @access protected
	 */
	@Override
	protected void onResume()
	{
		super.onResume();

		//関係性一覧取得
		SQLiteDatabase db = helper.getWritableDatabase();
		relations = relationDao.selectRelationList(db);
		db.close();

		//背景画像消える対策
		ListView relationListView = (ListView) findViewById(R.id.listViewRelation);
		relationListView.setScrollingCacheEnabled(false);

		//アダプターをセット
		adapter = new RelationListAdapter(this, R.layout.part_relation_list, relations);
		relationListView.setAdapter(adapter);

		//イベントリスナーセット
		relationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showEditDialog(position);
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
	 * リストビューのアイテムをタップされたとき
	 * 
	 * @param Integer position タップされた場所
	 * @return void
	 * @access private
	 */
	private void showEditDialog(Integer position)
	{
		createInputDialog();

		selected_position = position;

		//Relationクラス取得
		RelationEntity selectedRelation = (RelationEntity) relations.get(selected_position);
		selected_color = selectedRelation.getLabel();

		//名称と色をセット
		String name = selectedRelation.getName();
		Integer label = selectedRelation.getLabel();
		setNameAndLabelColor(name, label);

		//新規追加、編集、未分類の場合の表示制御 
		input_dialog.setCondition(selectedRelation.getId());

		input_dialog.show();
	}

	/**
	 * 関係性追加ボタンが押されたとき
	 * 
	 * @return void
	 * @access private
	 */
	private void showAddDialog()
	{
		createInputDialog();

		//リストビューの選択されたポジションを持っておく変数はnullにしておく
		selected_position = null;

		//名称は空、色の初期値は水色としてセット
		selected_color = getResources().getColor(R.color.group1);
		setNameAndLabelColor("", selected_color);

		//新規追加、編集、未分類の場合の表示制御
		input_dialog.setCondition(0);

		input_dialog.show();
	}

	/**
	 * 保存
	 * 
	 * @param Integer position タップされた場所
	 * @return void
	 * @access private
	 */
	private void saveRelation(Integer position)
	{
		TextView textEditName = (TextView) input_dialog.findViewById(R.id.textEditName);
		String name = textEditName.getText().toString();

		//バリデート
		Validate validate = new Validate();
		validate.set();
		validate.require.check(name, "名称", "");

		if (validate.getResult() == false) {
			showErrorDialog(validate.getErrorMsg());
			return;
		}

		//保存
		Integer id = 0;
		if (position != null) {
			id = relations.get(position).getId();
		}

		Integer color = null;
		if (color_dialog != null) {
			color = color_dialog.getColor();
			selected_color = color_dialog.getColor();
		} else {
			color = selected_color;
		}

		SQLiteDatabase db = helper.getWritableDatabase();
		relationDao.save(db, id, name, color, 1);			//TODO ソート値は一時的
		db.close();

		Utils.showToast(getApplication(), "保存しました。");

		//表示面も変えておく
		changeDisp(position, name, selected_color);

		input_dialog.dismiss();
	}

	/**
	 * 削除
	 * 
	 * @return void
	 * @access private
	 */
	private void deleteRelation()
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		Integer id = relations.get(selected_position).getId();
		relationDao.delete(db, id);
		db.close();

		adapter.remove(relations.get(selected_position));

		Utils.showToast(getApplication(), "削除しました。");
	}

	/**
	 * 名称とラベル色セット TODO　ダイアログクラスの方に持っていく
	 * 
	 * @param String name
	 * @param Integer color
	 * @return void
	 * @access private
	 */
	private void setNameAndLabelColor(String name, Integer color)
	{
		TextView textEditName = (TextView) input_dialog.findViewById(R.id.textEditName);
		textEditName.setText(name);

		selected_color = color;
		setLabelColor(color);
	}

	/**
	 * ラベル色セット
	 * 
	 * @param Integer color 色コード
	 * @return void
	 * @access private
	 */
	private void setLabelColor(Integer color)
	{
		TextView buttonColorPick = (TextView) input_dialog.findViewById(R.id.buttonColorPick);
		GradientDrawable shape = (GradientDrawable) buttonColorPick.getBackground();
		buttonColorPick.setBackgroundDrawable(shape);
		shape.setColor(color);
	}

	/**
	 * 追加・変更によって画面をリフレッシュする
	 * 
	 * @param Integer position アダプターの該当位置
	 * @param String name グループ名
	 * @param Integer color 色コード
	 * @return void
	 * @access private
	 */
	private void changeDisp(Integer position, String name, Integer color)
	{
		//追加の場合
		if (position == null) {
			SQLiteDatabase db = helper.getWritableDatabase();
			adapter.add(relationDao.selectNewstData(db));		//IDが分らないのでDBから取得する
			db.close();
		}
		//変更の場合
		else {
			RelationEntity item = new RelationEntity();
			item.setId(relations.get(position).getId());
			item.setName(name);
			item.setLabel(color);

			adapter.remove(relations.get(position));
			adapter.insert(item, position);
		}

		//更新したことをアダプタに伝える
		adapter.notifyDataSetChanged();
	}

	/**
	 * ヘッダのボタンセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setHeaderButton()
	{
		Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
		Button buttonAdd = (Button) findViewById(R.id.buttonAdd);

		//戻る
		buttonReturn.setVisibility(View.VISIBLE);
		buttonReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		//追加
		buttonAdd.setVisibility(View.VISIBLE);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showAddDialog();
			}
		});
	}

	/**
	 * 入力用ダイアログ作成
	 * 
	 * @return void
	 * @access private
	 */
	private void createInputDialog()
	{
		input_dialog = new RelationDialog(this);

		//色選択ボタン
		Button buttonColorPick = (Button) input_dialog.findViewById(R.id.buttonColorPick);
		buttonColorPick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showColorPicker();
			}
		});

		//作成ボタン
		Button buttonCreate = (Button) input_dialog.findViewById(R.id.buttonCreate);
		buttonCreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveRelation(selected_position);
			}
		});

		//変更ボタン
		Button buttonEdit = (Button) input_dialog.findViewById(R.id.buttonEdit);
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveRelation(selected_position);
			}
		});

		//削除ボタン
		Button buttonDelete = (Button) input_dialog.findViewById(R.id.buttonDelete);
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createConfirmDialog();
			}
		});
	}

	/**
	 * 削除確認用ダイアログ表示
	 * 
	 * @return void
	 * @access private
	 */
	private void createConfirmDialog()
	{
		if (confirm_dialog == null) {
			confirm_dialog = new ConfirmDialog(this, "削除してよろしいですか？");

			Button confirmButtonOk = (Button) confirm_dialog.findViewById(R.id.buttonOk);
			confirmButtonOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					deleteRelation();
					confirm_dialog.dismiss();
					input_dialog.dismiss();
				}
			});
		}

		confirm_dialog.show();
	}

	/**
	 * エラー用ダイアログ表示
	 * 
	 * @param ArrayList<String> msgs エラー配列
	 * @return void
	 * @access private
	 */
	private void showErrorDialog(ArrayList<String> msgs)
	{
		if (error_dialog == null) {
			error_dialog = new ErrorDialog(this, "");
		}
		error_dialog.setMsg(Utils.implode(msgs, "\n"));
		error_dialog.show();
	}

	/**
	 * カラーピッカー
	 * 
	 * @return void
	 * @access private
	 */
	private void showColorPicker()
	{
		if (color_dialog == null) {
			color_dialog = new ColorPickerDialog(this, null, selected_color);
		}
		Button buttonColorPick = (Button) input_dialog.findViewById(R.id.buttonColorPick);
		color_dialog.setButton(buttonColorPick);
		color_dialog.show();
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
		input_dialog = null;
		confirm_dialog = null;
		error_dialog = null;
		color_dialog = null;
		relationDao = null;
		relations = null;
		selected_position = null;
		selected_color = null;
	}
}