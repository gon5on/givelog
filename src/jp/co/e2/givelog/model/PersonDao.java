package jp.co.e2.givelog.model;

import java.util.ArrayList;

import jp.co.e2.givelog.common.MediaUtils;
import jp.co.e2.givelog.entity.PersonEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Personテーブルのデータアクセスオブジェクト
 * 
 * @access public
 */
public class PersonDao extends AppDao
{
    // テーブル名
    public static final String TABLE_NAME = "person";

    // カラム名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRESENT_ID = "present_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MEMBER_ID = "member_id";
    public static final String COLUMN_NAME = "name";

    /**
     * テーブル作成
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @return void
     * @access public
     */
    public void createTable(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + "                INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRESENT_ID + "        INTEGER," +
                COLUMN_TYPE + "              INTEGER," +          //1=もらう、2=あげる
                COLUMN_MEMBER_ID + "         INTEGER," +
                COLUMN_NAME + "              TEXT" +
                ")";
        db.execSQL(sql);
    }

    /**
     * メンバー削除により、メンバーIDを保存時していた人物データを名前に書き変える
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param Integer memberId メンバーID
     * @param String name メンバー名
     * @param String fileDir 画像フォルダパス
     * @return Boolean 成功/失敗
     * @access public
     */
    public Boolean slideMemberIdToName(SQLiteDatabase db, Integer memberId, String name, String fileDir)
    {
        PresentDao presentDao = new PresentDao();

        //削除対象メンバーが属する人物データを取得する
        ArrayList<PersonEntity> personList = getPersonByMemberId(db, memberId);

        for (int i = 0; i < personList.size(); i++) {
            PersonEntity personData = personList.get(i);

            //プレゼントIDに紐付く人物データを取得する（人物自身のデータは除外）
            ArrayList<PersonEntity> personList2 = getPersonByPresentId(db, personData.getPresentId(), memberId);

            Boolean memberFlg = false;                  //プレゼントデータが他のメンバーと紐付いているかフラグ
            PersonEntity cunstomData = null;            //あげた・もらったタイプが同じ人物データ

            //該当のプレゼントデータに他にメンバーと紐付いてるか、
            for (int j = 0; j < personList2.size(); j++) {
                PersonEntity personData2 = personList2.get(j);

                if (personData2.getMemberId() != null) {
                    memberFlg = true;
                }

                if (personData2.getName() != null && personData2.getType() == personData.getType()) {
                    cunstomData = personData2;
                }
            }

            //他のメンバーと紐付いていないので、プレゼントデータ自体を削除する
            if (memberFlg == false) {
                presentDao.delete(db, personData.getPresentId());
                MediaUtils.deleteDirFile(fileDir + "/" + "present_" + personData.getPresentId() + ".jpg");
            }
            //他のメンバーと紐付いているので、プレゼントデータ自体は削除しない
            else {
                //カスタム人物データがない（メンバーIDを名前に書き変えて人物データをアップデート）
                if (cunstomData == null) {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_PRESENT_ID, personData.getPresentId());
                    cv.put(COLUMN_TYPE, personData.getType());
                    cv.put(COLUMN_NAME, name);
                    cv.putNull(COLUMN_MEMBER_ID);

                    String[] param = { Integer.toString(personData.getId()) };
                    long ret = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", param);

                    if (ret == -1) {
                        return false;
                    }
                }
                //カスタム人物データがある（カスタム人物データに削除対象メンバー名を追加して、元の人物データは削除）
                else {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_PRESENT_ID, cunstomData.getPresentId());
                    cv.put(COLUMN_TYPE, cunstomData.getType());
                    cv.put(COLUMN_NAME, cunstomData.getName() + ", " + name);
                    cv.putNull(COLUMN_MEMBER_ID);

                    String[] param = { Integer.toString(cunstomData.getId()) };
                    long ret = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", param);

                    String[] param2 = { Integer.toString(cunstomData.getId()) };
                    long ret2 = db.delete(TABLE_NAME, COLUMN_ID + " = ?", param2);

                    if (ret == -1 || ret2 == -1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * メンバーIDに紐付く人物データを取得
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param Integer memberId メンバーID
     * @return ArrayList<Person> personData
     * @access public
     */
    public ArrayList<PersonEntity> getPersonByMemberId(SQLiteDatabase db, Integer memberId)
    {
        ArrayList<PersonEntity> personData = new ArrayList<PersonEntity>();

        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_MEMBER_ID);

        String[] param = { Integer.toString(memberId) };

        Cursor cursor = db.rawQuery(sql.toString(), param);

        if (cursor.moveToFirst()) {
            do {
                PersonEntity data = new PersonEntity();
                data.setId(getInteger(cursor, COLUMN_ID));
                data.setPresentId(getInteger(cursor, COLUMN_PRESENT_ID));
                data.setType(getInteger(cursor, COLUMN_TYPE));
                data.setMemberId(getInteger(cursor, COLUMN_MEMBER_ID));
                data.setName(getString(cursor, COLUMN_NAME));

                personData.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return personData;
    }

    /**
     * プレゼントIDに紐付く人物データを取得
     * ただし、引数で渡したmemberIdは除く
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param Integer presentId プレゼントID
     * @param Integer memberId メンバーID
     * @return ArrayList<Person> personData プレゼントIDに紐付く人物データ
     * @access public
     */
    public ArrayList<PersonEntity> getPersonByPresentId(SQLiteDatabase db, int presentId, int memberId)
    {
        ArrayList<PersonEntity> personData = new ArrayList<PersonEntity>();

        String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
        sql += String.format("WHERE %s = ? ", COLUMN_PRESENT_ID);
        sql += String.format("AND %s != ?", COLUMN_MEMBER_ID);

        String[] param = { Integer.toString(presentId), Integer.toString(memberId) };

        Cursor cursor = db.rawQuery(sql, param);

        if (cursor.moveToFirst()) {
            do {
                PersonEntity data = new PersonEntity();
                data.setId(getInteger(cursor, COLUMN_ID));
                data.setPresentId(getInteger(cursor, COLUMN_PRESENT_ID));
                data.setType(getInteger(cursor, COLUMN_TYPE));
                data.setMemberId(getInteger(cursor, COLUMN_MEMBER_ID));
                data.setName(getString(cursor, COLUMN_NAME));

                personData.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return personData;
    }
}
