package jp.co.e2.givelog.model;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Personテーブルのデータアクセスオブジェクト
 * 
 * @access public
 */
public class PersonDao
{
	private Context context; 		//コンテキスト

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public PersonDao(Context context)
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
		String sql = "CREATE TABLE person (" +
				"id                INTEGER PRIMARY KEY AUTOINCREMENT," +
				"present_id        INTEGER," +
				"type              INTEGER," +		//1=もらう、2=あげる
				"member_id         INTEGER," +
				"name              TEXT" +
				")";
		db.execSQL(sql);
	}

	/**
	 * メンバー削除により、メンバーIDを保存時していた人物データを名前に書き変える
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer member_id メンバーID
	 * @param String name メンバー名
	 * @param String file_dir 画像フォルダパス
	 * @return void
	 * @access public
	 */
	public void slideMemberIdToName(SQLiteDatabase db, Integer member_id, String name, String file_dir)
	{
		PresentDao presentDao = new PresentDao(context);

		//削除対象メンバーに紐付く人物データを取得する
		ArrayList<Person> person_list = getPersonByMemberId(db, member_id);

		for (int i = 0; i < person_list.size(); i++) {
			Person person_data = person_list.get(i);

			//プレゼントIDに紐付く人物データを取得する（人物自身のデータは除外）
			ArrayList<Person> person_list2 = getPersonByPresentId(db, person_data.getPresentId(), member_id);

			Boolean member_flg = false;		//プレゼントデータが他のメンバーと紐付いているかフラグ
			Person cunstom_data = null;		//あげた・もらったタイプが同じ人物データ

			//該当のプレゼントデータに他にメンバーと紐付いてるか、
			for (int j = 0; j < person_list2.size(); j++) {
				Person person_data2 = person_list2.get(j);

				if (person_data2.getMemberId() != null) {
					member_flg = true;
				}

				if (person_data2.getName() != null && person_data2.getType() == person_data.getType()) {
					cunstom_data = person_data2;
				}
			}

			//他のメンバーと紐付いていないので、プレゼントデータ自体を削除する
			if (member_flg == false) {
				presentDao.delete(db, person_data.getPresentId());
				deleteImg(file_dir, person_data.getPresentId(), "present");		//画像削除
			}
			//他のメンバーと紐付いているので、プレゼントデータ自体は削除しない
			else {
				//カスタム人物データがない（メンバーIDを名前に書き変えて人物データをアップデート）
				if (cunstom_data == null) {
					ContentValues cv = new ContentValues();
					cv.put("present_id", person_data.getPresentId());
					cv.put("type", person_data.getType());
					cv.put("name", name);
					cv.putNull("member_id");

					db.update("person", cv, "id=?", new String[] { Integer.toString(person_data.getId()) });
				}
				//カスタム人物データがある（カスタム人物データに削除対象メンバー名を追加して、元の人物データは削除）
				else {
					ContentValues cv = new ContentValues();
					cv.put("present_id", cunstom_data.getPresentId());
					cv.put("type", cunstom_data.getType());
					cv.put("name", cunstom_data.getName() + ", " + name);
					cv.putNull("member_id");

					db.update("person", cv, "id=?", new String[] { Integer.toString(cunstom_data.getId()) });
					db.delete("person", "id=?", new String[] { Integer.toString(person_data.getId()) });
				}
			}
		}
	}

	/**
	 * メンバーIDに紐付く人物データを取得
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer member_id メンバーID
	 * @return ArrayList<Person> person_data
	 * @access public
	 */
	public ArrayList<Person> getPersonByMemberId(SQLiteDatabase db, Integer member_id)
	{
		ArrayList<Person> person_data = new ArrayList<Person>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM person WHERE member_id = " + member_id);

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Person data = new Person();
				data.setId(cursor.getInt(0));
				data.setPresentId(cursor.getInt(1));
				data.setType(cursor.getInt(2));
				data.setMemberId(cursor.getInt(3));
				data.setName(cursor.getString(4));
				person_data.add(data);
			}
			cursor.close();
		} catch (Exception ex) {
			Log.v("PersonDao.getPersonByMemberId", ex.toString());
		} finally {
			//db.close();
		}

		return person_data;
	}

	/**
	 * プレゼントIDに紐付く人物データを取得
	 * （ただし、引数で渡したmember_idは除く）
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer present_id プレゼントID
	 * @param Integer member_id メンバーID
	 * @return ArrayList<Person> person_data プレゼントIDに紐付く人物データ
	 * @access public
	 */
	public ArrayList<Person> getPersonByPresentId(SQLiteDatabase db, int present_id, int member_id)
	{
		ArrayList<Person> person_data = new ArrayList<Person>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM person ");
			sql.append("WHERE present_id = " + present_id + " ");
			sql.append("AND member_id != " + member_id);
			//sql.append("AND type = " + person_data.getType() + " ");
			//sql.append("AND name != ''");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Person data = new Person();
				data.setId(cursor.getInt(0));
				data.setPresentId(cursor.getInt(1));
				data.setType(cursor.getInt(2));
				data.setMemberId(cursor.getInt(3));
				data.setName(cursor.getString(4));
				person_data.add(data);
			}
			cursor.close();
		} catch (Exception ex) {
			Log.v("PersonDao.getCustomDataByPresentId", ex.toString());
		}

		return person_data;
	}

	/**
	 * 画像削除（BaseActivityのものを移植）
	 * 
	 * @param String file_dir 画像フォルダパス
	 * @param Integer present_id プレゼントID
	 * @param String prefix プレフィックス
	 * @return void
	 * @access public
	 */
	public void deleteImg(String file_dir, Integer present_id, String prefix)
	{
		String file_name = prefix + "_" + present_id + ".jpg";

		File file = new File(file_dir + "/" + file_name);
		if (file.exists() == true) {
			file.delete();
		}
	}
}
