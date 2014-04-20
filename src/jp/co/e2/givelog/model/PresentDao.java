package jp.co.e2.givelog.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * プレゼントテーブルのデータアクセスオブジェクト
 * 
 * @access public
 */
public class PresentDao
{
	private long inserted_id;
	private ArrayList<Member> members;
	private ArrayList<Integer> from_list = new ArrayList<Integer>();
	private ArrayList<Integer> to_list = new ArrayList<Integer>();
	private String from_custom = "";
	private String to_custom = "";

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access publivc
	 */
	public PresentDao(Context context)
	{
		this.members = null;
		this.from_list = new ArrayList<Integer>();
		this.to_list = new ArrayList<Integer>();
		this.from_custom = null;
		this.to_custom = null;
	}

	/**
	 * テーブル作成
	 * 
	 * @return void
	 * @access public
	 */
	public void createTable(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE present (" +
				"id                INTEGER PRIMARY KEY AUTOINCREMENT," +
				"date              TEXT," +
				"event             TEXT," +
				"present           TEXT," +
				"price             INTEGER," +
				"photo             INTEGER," +
				"memo              TEXT" +
				")";
		db.execSQL(sql);
	}

	/**
	 * 人物詳細で閲覧するプレゼントリストを取得
	 * 
	 * @param Integer member_id メンバーID
	 * @param Integer type あげたかもらったかのタイプ
	 * @return ArrayList<Present> presents プレゼントクラスの配列
	 * @access public
	 */
	public ArrayList<Present> selectPresentList(SQLiteDatabase db, Integer member_id, Integer type)
	{
		//対象の人物がかかわっているプレゼントデータを取得
		ArrayList<Present> presents = selectMembersPresentList(db, member_id, type);

		//プレゼントデータから関わっている人物を取得
		for (int i = 0; i < presents.size(); i++) {
			Present present = presents.get(i);
			ArrayList<Person> persons = selectPersonList(db, present.getId());

			for (int j = 0; j < persons.size(); j++) {
				Person person = persons.get(j);

				//あげた
				if (person.getType() == 1) {
					presents.get(i).setGive(person.getName());
				}
				//もらった
				else {
					presents.get(i).setGave(person.getName());
				}
			}
		}
		return presents;
	}

	/**
	 * プレゼントIDを主体にして、プレゼントクラスを返す
	 * 
	 * @param Integer present_id プレゼントID
	 * @return Present present プレゼントクラス
	 * @access public
	 */
	public Present selectPresentData(SQLiteDatabase db, Integer present_id)
	{
		Present present = new Present();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM present WHERE id=" + present_id);

			Cursor cursor = db.rawQuery(sql.toString(), null);
			cursor.moveToFirst();

			present.setId(cursor.getInt(0));
			present.setDate(cursor.getString(1));
			present.setEvent(cursor.getString(2));
			present.setPresent(cursor.getString(3));
			present.setPrice(cursor.getString(4));
			present.setPhoto(cursor.getInt(5));
			present.setMemo(cursor.getString(6));

			cursor.close();
		} catch (Exception ex) {
			Log.v("PresentDao.selectPresentData", ex.toString());
		} finally {
			//db.close();
		}

		return present;
	}

	/**
	 * 人物IDを主体にして、プレセントクラス配列を返す
	 * 
	 * @param member_id メンバーID
	 * @param Integer type あげたかもらったかのタイプ
	 * @return ArrayList<Present> list プレゼントクラス
	 * @access public
	 */
	public ArrayList<Present> selectMembersPresentList(SQLiteDatabase db, Integer member_id, Integer type)
	{
		ArrayList<Present> list = new ArrayList<Present>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM person ");
			sql.append("LEFT JOIN present ON present.id = person.present_id ");
			sql.append("WHERE person.member_id = " + member_id + " ");
			sql.append("AND person.type = " + type + " ");
			sql.append("ORDER BY present.date DESC, present.id DESC");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Present present = new Present();
				present.setId(cursor.getInt(1));
				present.setId(cursor.getInt(5));
				present.setDate(cursor.getString(6));
				present.setEvent(cursor.getString(7));
				present.setPresent(cursor.getString(8));
				present.setPrice(cursor.getString(9));
				present.setPhoto(cursor.getInt(10));
				present.setMemo(cursor.getString(11));
				list.add(present);
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("PresentDao.selectMembersPresentList", ex.toString());
		}

		return list;
	}

	/**
	 * プレゼントIDを主体にして、誰から誰へクラスを取得する
	 * 
	 * @param Integer present_id プレゼントID
	 * @return ArrayList<Person> 人物クラス配列
	 * @access public
	 */
	public ArrayList<Person> selectPersonList(SQLiteDatabase db, Integer present_id)
	{
		ArrayList<Person> list = new ArrayList<Person>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM person ");
			sql.append("LEFT JOIN member ON member.id = person.member_id ");
			sql.append("WHERE person.present_id = " + present_id + " ");
			sql.append("ORDER BY member.kana IS NULL ASC, member.kana, member.id ASC");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Person person = new Person();
				person.setPresentId(cursor.getInt(1));
				person.setType(cursor.getInt(2));
				person.setMemberId(cursor.getInt(3));
				if (cursor.getString(4).length() > 0) {
					person.setName(cursor.getString(4));
				} else {
					person.setName(cursor.getString(6));
				}
				list.add(person);
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("PresentDao.selectPersonList", ex.toString());
		}

		return list;
	}

	/**
	 * 保存
	 * 
	 * @param Integer id ID
	 * @param Integer photo 写真の有無フラグ
	 * @param boolean[] from だれからスピナーのチェック済配列
	 * @param boolean[] to だれへスピナーのチェック済配列
	 * @param String date 日付
	 * @param String event イベント
	 * @param String present プレゼント
	 * @param String price 値段
	 * @param String memo メモ
	 * @param String add_from あげたカスタム人物
	 * @param String add_to もらったカスタム人物
	 * @return boolean 成功/失敗
	 * @access public
	 */
	public Boolean save(SQLiteDatabase db, int id, Integer photo, boolean[] from, boolean[] to, String date, String event, String present, String price, String memo, String add_from, String add_to)
	{
		try {
			//プレゼント保存
			ContentValues cv = new ContentValues();
			cv.put("date", date);
			cv.put("event", event);
			cv.put("present", present);
			cv.put("price", price);
			cv.put("photo", photo);
			cv.put("memo", memo);

			if (id == 0) {
				id = (int) db.insert("present", "", cv);
				inserted_id = id;
			} else {
				db.update("present", cv, "id=?", new String[] { Integer.toString(id) });
			}

			//だれからだれへデータをすべて削除する
			db.delete("person", "present_id=?", new String[] { Integer.toString(id) });

			//だれから保存
			for (int i = 0; i < from.length - 1; i++) {
				if (from[i] == true) {
					ContentValues cv2 = new ContentValues();
					cv2.put("present_id", id);
					cv2.put("type", 1);
					cv2.put("member_id", members.get(i).getId());
					cv2.put("name", "");
					db.insert("person", "", cv2);
				}
			}

			//だれへ保存
			for (int i = 0; i < to.length - 1; i++) {
				if (to[i] == true) {
					ContentValues cv2 = new ContentValues();
					cv2.put("present_id", id);
					cv2.put("type", 2);
					cv2.put("member_id", members.get(i).getId());
					cv2.put("name", "");
					db.insert("person", "", cv2);
				}
			}

			//だれからカスタムフィールド保存
			if (from[(from.length - 1)] == true && add_from != null && add_from.length() != 0) {
				ContentValues cv2 = new ContentValues();
				cv2.put("present_id", id);
				cv2.put("type", 1);
				cv2.put("member_id", "");
				cv2.put("name", add_from);
				db.insert("person", "", cv2);
			}

			//だれへカスタムフィールド保存
			if (to[(to.length - 1)] == true && add_to != null && add_to.length() != 0) {
				ContentValues cv2 = new ContentValues();
				cv2.put("present_id", id);
				cv2.put("type", 2);
				cv2.put("member_id", "");
				cv2.put("name", add_to);
				db.insert("person", "", cv2);
			}
		} catch (Exception ex) {
			Log.v("PresentDao.save", ex.toString());
		}

		return true;
	}

	/**
	 * メンバー配列をセット
	 * 
	 * @param ArrayList<Member> list メンバー配列
	 * @return void
	 * @access public
	 */
	public void setMembers(ArrayList<Member> list)
	{
		members = list;
	}

	/**
	 * 削除
	 * 
	 * @param Integer id プレゼントID
	 * @return boolean
	 * @access public
	 */
	public Boolean delete(SQLiteDatabase db, Integer id)
	{
		try {
			db.delete("present", "id=?", new String[] { Integer.toString(id) });
			db.delete("person", "present_id=?", new String[] { Integer.toString(id) });
		} catch (Exception ex) {
			Log.v("PresentDao.delete", ex.toString());
		}

		return true;
	}

	/**
	 * プレゼントIDを主にして、だれからだれへ配列（メンバーIDそのまま）を取得
	 * 
	 * @param Integer present_id プレゼントID
	 * @return void
	 * @access public
	 */
	public void selectPresentPerson(SQLiteDatabase db, Integer present_id)
	{
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM person WHERE present_id = " + present_id);

			Cursor cursor = db.rawQuery(sql.toString(), null);
			cursor.moveToFirst();

			do {
				//カスタムの場合
				if (cursor.getString(4).length() != 0) {
					if (cursor.getInt(2) == 1) {
						from_custom = cursor.getString(4);
					} else {
						to_custom = cursor.getString(4);
					}
				}
				//メンバーIDが存在する場合
				else {
					if (cursor.getInt(2) == 1) {
						from_list.add(cursor.getInt(3));
					} else {
						to_list.add(cursor.getInt(3));
					}
				}
			} while (cursor.moveToNext());

			cursor.close();
		} catch (Exception ex) {
			Log.v("PresentDao.selectPresentPerson", ex.toString());
		}
	}

	/**
	 * プレゼントIDを主にして、だれから配列（メンバーIDそのまま）を取得
	 * 
	 * @param Integer present_id プレゼントID
	 * @return ArrayList<Integer> from_list 誰から配列
	 * @access public
	 */
	public ArrayList<Integer> selectFromList(SQLiteDatabase db, Integer present_id)
	{
		if (from_list.size() == 0 && from_custom == null) {
			selectPresentPerson(db, present_id);
		}

		return from_list;
	}

	/**
	 * プレゼントIDを主にして、だれへ配列（メンバーIDそのまま）を取得
	 * 
	 * @param Integer present_id プレゼントID
	 * @return ArrayList<Integer> to_list 誰へ配列
	 * @access public
	 */
	public ArrayList<Integer> selectToList(SQLiteDatabase db, Integer present_id)
	{
		if (to_list.size() == 0 && to_custom == null) {
			selectPresentPerson(db, present_id);
		}

		return to_list;
	}

	/**
	 * プレゼントIDを主にして、だれからのカスタム値を取得
	 * 
	 * @param Integer present_id プレゼントID
	 * @return String from_custom 誰からカスタム値
	 * @access public
	 */
	public String getFromCustom(SQLiteDatabase db, Integer present_id)
	{
		if (from_list.size() == 0 && from_custom == null) {
			selectPresentPerson(db, present_id);
		}

		return from_custom;
	}

	/**
	 * プレゼントIDを主にして、だれへのカスタム値を取得
	 * 
	 * @param Integer present_id プレゼントID
	 * @return String to_custom だれへカスタム値
	 * @access public
	 */
	public String getToCustom(SQLiteDatabase db, Integer present_id)
	{
		if (to_list.size() == 0 && to_custom == null) {
			selectPresentPerson(db, present_id);
		}

		return to_custom;
	}

	/**
	 * インサートしたIDを返す
	 * 
	 * @return Integer インサートしたID
	 * @access public
	 */
	public Integer getInsertedId()
	{
		return Integer.valueOf(String.valueOf(inserted_id));
	}
}
