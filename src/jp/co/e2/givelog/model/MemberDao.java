package jp.co.e2.givelog.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Memberテーブルのデータアクセスオブジェクト
 * 
 * @access public
 */
public class MemberDao
{
	private Context context;				//コンテキスト
	private long inserted_id;				// インサート時のID

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public MemberDao(Context context)
	{
		this.context = context;
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
		String sql = "CREATE TABLE member (" +
				"id          INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name        TEXT," +
				"kana        TEXT," +
				"birthday    TEXT," +
				"relation_id INTEGER," +
				"photo       INTEGER," +
				"memo        TEXT" +
				")";
		db.execSQL(sql);
	}

	/**
	 * 人物一覧取得
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @return ArrayList<Member> list 人物クラスの配列
	 * @access public
	 */
	public ArrayList<Member> selectMemberList(SQLiteDatabase db)
	{
		ArrayList<Member> list = new ArrayList<Member>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM member LEFT JOIN relation ON member.relation_id = relation.id ORDER BY relation.sort, relation.id, member.kana ASC");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			Integer relation_id = 0;	//関係性が前と変わったかどうか確認用変数

			while (cursor.moveToNext()) {
				//関係性ラベルの場合
				if (cursor.getInt(4) != relation_id) {
					Member member = new Member();
					member.setRelationName(cursor.getString(8));
					member.setLabel(cursor.getInt(9));
					list.add(member);
				}

				Member member = new Member();
				member.setId(cursor.getInt(0));
				member.setName(cursor.getString(1));
				member.setBirth(cursor.getString(3));
				member.setRelationId(cursor.getInt(4));
				member.setLabel(cursor.getInt(9));

				list.add(member);

				relation_id = member.getRelationId();
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("MemberDao.selectMemberList", ex.toString());
		}

		return list;
	}

	/**
	 * 人物取得
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer id メンバーID
	 * @return Member メンバークラス
	 * @access public
	 */
	public Member selectMemberDetail(SQLiteDatabase db, int id)
	{
		Member member = new Member();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM member LEFT JOIN relation ON member.relation_id = relation.id WHERE member.id = " + id);

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				member.setId(cursor.getInt(0));
				member.setName(cursor.getString(1));
				member.setKana(cursor.getString(2));
				member.setBirth(cursor.getString(3));
				member.setRelationId(cursor.getInt(4));
				member.setPhoto(cursor.getInt(5));
				member.setMemo(cursor.getString(6));
				member.setRelationName(cursor.getString(8));
				member.setLabel(cursor.getInt(9));
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("MemberDao.selectMemberDetail", ex.toString());
		}

		return member;
	}

	/**
	 * 名前とIDのメンバークラス配列を返す
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @return ArrayList<Member> 名前とIDのメンバークラス配列
	 * @access public
	 */
	public ArrayList<Member> selectMemberIdNameList(SQLiteDatabase db)
	{
		ArrayList<Member> list = new ArrayList<Member>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM member ORDER BY kana");

			Cursor cursor = db.rawQuery(sql.toString(), null);

			while (cursor.moveToNext()) {
				Member member = new Member();
				member.setId(cursor.getInt(0));
				member.setName(cursor.getString(1));

				list.add(member);
			}

			cursor.close();
		} catch (Exception ex) {
			Log.v("MemberDao.selectMemberIdNameList", ex.toString());
		}

		return list;
	}

	/**
	 * メンバークラス配列をスピナー用に成形する
	 * 
	 * @param ArrayList<Member> items メンバークラス配列
	 * @return String[] スピナー用のメンバー配列
	 * @access public
	 */
	public String[] getMemberSpinnerList(ArrayList<Member> items)
	{
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < items.size(); i++) {
			Member item = items.get(i);
			list.add(item.getName());
		}

		list.add("カスタム(自由記入)");

		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 保存
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param Integer id
	 * @param String name
	 * @param String kana
	 * @param String birthday
	 * @param Integer relation_id
	 * @param String photo
	 * @param String memo
	 * @return boolen 成功/失敗
	 * @access public
	 */
	public Boolean save(SQLiteDatabase db, Integer id, String name, String kana, String birthday, int relation_id, Integer photo, String memo)
	{
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("kana", kana);
			cv.put("birthday", birthday);
			cv.put("relation_id", relation_id);
			cv.put("photo", photo);
			cv.put("memo", memo);

			if (id == 0) {
				inserted_id = db.insert("member", "", cv);
			} else {
				db.update("member", cv, "id=?", new String[] { Integer.toString(id) });
			}
		} catch (Exception ex) {
			Log.v("MemberDao.save", ex.toString());
		} finally {
			//db.close();
		}

		return true;
	}

	/**
	 * メンバー削除
	 * 
	 * @param SQLiteDatabase db データベースオブジェクト
	 * @param String file_dir 画像ファイル名
	 * @param Integer id メンバーID
	 * @param String name メンバー名
	 * @return boolean 成功/失敗
	 * @access public
	 */
	public Boolean delete(SQLiteDatabase db, String file_dir, Integer id, String name)
	{
		try {
			//メンバー削除
			db.delete("member", "id=?", new String[] { Integer.toString(id) });

			//削除対象メンバーが含まれている人物データにひと手間加える
			PersonDao personDao = new PersonDao(context);
			personDao.slideMemberIdToName(db, id, name, file_dir);
		} catch (Exception ex) {
			Log.v("MemberDao.delete", ex.toString());
		} finally {
			//db.close();
		}

		return true;
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
