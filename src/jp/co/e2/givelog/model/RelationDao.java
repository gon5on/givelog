package jp.co.e2.givelog.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * グループテーブルのデータアクセスオブジェクト
 * 
 * @access
 */
public class RelationDao
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public RelationDao(Context context)
	{
	}

	/**
	 * テーブル作成
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @return void
	 * @access public
	 */
	public void createTable(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE relation (" +
				"id    INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name  TEXT," +
				"label INTEGER," +
				"sort  INTEGER" +
				")";
		db.execSQL(sql);
	}

	/**
	 * グループ配列を返す
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @return ArrayList<Relation> list グループ配列
	 * @access public
	 */
	public ArrayList<Relation> selectRelationList(SQLiteDatabase db)
	{
		ArrayList<Relation> list = new ArrayList<Relation>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM relation ORDER BY sort,id");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Relation relation = new Relation();
				relation.setId(cursor.getInt(0));
				relation.setName(cursor.getString(1));
				relation.setLabel(cursor.getInt(2));

				list.add(relation);
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("RelationDao.selectRelationList", ex.toString());
		}

		return list;
	}

	/**
	 * スピナー用一覧取得
	 * 
	 * @param ArrayList<Relation> items グループクラス配列
	 * @return String[] list スピナー用配列
	 * @access public
	 */
	public String[] getRelationSpinnerList(ArrayList<Relation> items)
	{
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < items.size(); i++) {
			Relation item = items.get(i);
			list.add(item.getName());
		}

		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * IDからスピナー用一覧の何番目に相当するか返す
	 * 
	 * @param ArrayList<Relation> items グループクラス配列
	 * @param Integer id グループID
	 * @return Integer position 何番目
	 * @access public
	 */
	public Integer getRelationSpinnerPosition(ArrayList<Relation> items, Integer id)
	{
		Integer position = null;

		for (int i = 0; i < items.size(); i++) {
			Relation item = items.get(i);

			if (item.getId() == id) {
				position = i;
				break;
			}
		}

		return position;
	}

	/**
	 * 保存
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer id グループID
	 * @param String name グループ名
	 * @param Integer label グループラベル色
	 * @param Integer sort ソート値
	 * @return boolean 成功/失敗
	 * @access public
	 */
	public Boolean save(SQLiteDatabase db, Integer id, String name, Integer label, Integer sort)
	{
		sort = (id == 1) ? 9999 : sort;		//TODO　カテゴリは一番下一時的

		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("label", label);
			cv.put("sort", sort);

			if (id == 0) {
				db.insert("relation", "", cv);
			} else {
				db.update("relation", cv, "id=?", new String[] { Integer.toString(id) });
			}
		} catch (Exception ex) {
			Log.v("RelationDao.save", ex.toString());
		}

		return true;
	}

	/**
	 * 削除
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer id グループID
	 * @return boolean 成功/失敗
	 * @access public
	 */
	public Boolean delete(SQLiteDatabase db, Integer id)
	{
		try {
			//この関係性を使用している人物の関係性を強制的に未分類（ID=1）にする
			ContentValues cv = new ContentValues();
			cv.put("relation_id", 1);
			db.update("member", cv, "relation_id=?", new String[] { Integer.toString(id) });

			//削除
			db.delete("relation", "id=?", new String[] { Integer.toString(id) });
		} catch (Exception ex) {
			Log.v("RelationDao.delete", ex.toString());
		}

		return true;
	}

	/**
	 * 最新データ1件を取得
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @return Relation relation グループクラス
	 * @access public
	 */
	public Relation selectNewstData(SQLiteDatabase db)
	{
		Relation relation = new Relation();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM relation ORDER BY id DESC limit 1");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				relation.setId(cursor.getInt(0));
				relation.setName(cursor.getString(1));
				relation.setLabel(cursor.getInt(2));
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("RelationDao.selectNewstData", ex.toString());
		}

		return relation;
	}
}
