package jp.co.e2.givelog.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Eventテーブルへのデータアクセスオブジェクト
 * 
 * @access public
 */
public class EventDao
{
	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public EventDao(Context context)
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
		String sql = "CREATE TABLE event (" +
				"id    INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name  TEXT" +
				")";
		db.execSQL(sql);
	}

	/**
	 * 保存処理
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer id イベントID
	 * @param String name イベント名
	 * @return boolean 成功/失敗
	 */
	public Boolean save(SQLiteDatabase db, Integer id, String name)
	{
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);

			if (id == 0) {
				db.insert("event", "", cv);
			} else {
				db.update("event", cv, "id=?", new String[] { Integer.toString(id) });
			}
		} catch (Exception ex) {
		}

		return true;
	}
}
