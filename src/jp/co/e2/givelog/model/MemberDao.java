package jp.co.e2.givelog.model;

import java.util.ArrayList;

import jp.co.e2.givelog.entity.MemberEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Memberテーブルのデータアクセスオブジェクト
 * 
 * @access public
 */
public class MemberDao extends BaseDao
{
    // テーブル名
    public static final String TABLE_NAME = "member";

    // カラム名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_KANA = "kana";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_RELATION_ID = "relation_id";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_MEMO = "memo";

    private long mInsertedId;                                           // インサート時のID

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
                COLUMN_ID + "               INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + "             TEXT," +
                COLUMN_KANA + "             TEXT," +
                COLUMN_BIRTHDAY + "         TEXT," +
                COLUMN_RELATION_ID + "      INTEGER," +
                COLUMN_PHOTO + "            INTEGER," +
                COLUMN_MEMO + "             TEXT" +
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
    public ArrayList<MemberEntity> selectMemberList(SQLiteDatabase db)
    {
        ArrayList<MemberEntity> list = new ArrayList<MemberEntity>();

        String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
        sql += String.format("LEFT JOIN %s ON ", RelationDao.TABLE_NAME);
        sql += String.format("%s.%s = %s.%s ",
                TABLE_NAME, COLUMN_RELATION_ID, RelationDao.TABLE_NAME, RelationDao.COLUMN_ID);
        sql += String.format("ORDER BY %s.%s, %s.%s, %s.%s ASC",
                RelationDao.TABLE_NAME, RelationDao.COLUMN_SORT, RelationDao.TABLE_NAME,
                RelationDao.COLUMN_ID, TABLE_NAME, COLUMN_KANA);

        Cursor cursor = db.rawQuery(sql, null);

        Integer relationId = 0;         //関係性が前と変わったかどうか確認用変数

        if (cursor.moveToFirst()) {
            do {
                //関係性が変わった場合は、関係性ラベルを追加
                if (cursor.getInt(4) != relationId) {
                    MemberEntity member = new MemberEntity();
                    member.setRelationName(getString(cursor, RelationDao.COLUMN_NAME));
                    member.setLabel(getInteger(cursor, RelationDao.COLUMN_LABEL));
                    list.add(member);
                }

                MemberEntity member = new MemberEntity();
                member.setId(getInteger(cursor, COLUMN_ID));
                member.setName(getString(cursor, COLUMN_NAME));
                member.setBirth(getString(cursor, COLUMN_BIRTHDAY));
                member.setRelationId(getInteger(cursor, COLUMN_RELATION_ID));
                member.setLabel(getInteger(cursor, RelationDao.COLUMN_LABEL));

                list.add(member);

                relationId = member.getRelationId();
            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    /**
     * 人物取得
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param Integer id メンバーID
     * @return Member メンバーオブジェクト
     * @access public
     */
    public MemberEntity selectMemberDetail(SQLiteDatabase db, int id)
    {
        MemberEntity member = new MemberEntity();

        String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
        sql += String.format("LEFT JOIN %s ", RelationDao.TABLE_NAME);
        sql += String.format("ON %s.%s = %s.%s ",
                TABLE_NAME, COLUMN_RELATION_ID, RelationDao.TABLE_NAME, RelationDao.COLUMN_ID);
        sql += String.format("WHERE %s.%s = ?", TABLE_NAME, COLUMN_ID);

        String[] param = { Integer.toString(id) };

        Cursor cursor = db.rawQuery(sql, param);

        if (cursor.moveToFirst()) {
            do {
                member.setId(getInteger(cursor, COLUMN_ID));
                member.setName(getString(cursor, COLUMN_NAME));
                member.setKana(getString(cursor, COLUMN_KANA));
                member.setBirth(getString(cursor, COLUMN_BIRTHDAY));
                member.setRelationId(getInteger(cursor, COLUMN_RELATION_ID));
                member.setPhoto(getInteger(cursor, COLUMN_PHOTO));
                member.setMemo(getString(cursor, COLUMN_MEMO));
                member.setRelationName(getString(cursor, RelationDao.COLUMN_NAME));
                member.setLabel(getInteger(cursor, RelationDao.COLUMN_LABEL));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return member;
    }

    /**
     * 名前とIDのメンバークラス配列を返す
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @return ArrayList<Member> 名前とIDのメンバークラス配列
     * @access public
     */
    public ArrayList<MemberEntity> selectMemberIdNameList(SQLiteDatabase db)
    {
        ArrayList<MemberEntity> list = new ArrayList<MemberEntity>();

        String sql = String.format("SELECT * FROM %s ORDER BY %s", TABLE_NAME, COLUMN_KANA);

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                MemberEntity member = new MemberEntity();
                member.setId(getInteger(cursor, COLUMN_ID));
                member.setName(getString(cursor, COLUMN_NAME));

                list.add(member);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    /**
     * メンバークラス配列をスピナー用に成形する
     * 
     * @param ArrayList<Member> items メンバークラス配列
     * @return String[] スピナー用のメンバー配列
     * @access public
     */
    public String[] getMemberSpinnerList(ArrayList<MemberEntity> items)
    {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < items.size(); i++) {
            MemberEntity item = items.get(i);
            list.add(item.getName());
        }

        list.add("カスタム(自由記入)");

        return (String[]) list.toArray(new String[0]);
    }

    /**
     * 保存
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param MemberEntity data
     * @return boolen 成功/失敗
     * @access public
     */
    public Boolean save(SQLiteDatabase db, MemberEntity data)
    {
        long ret = 0;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, data.getName());
        cv.put(COLUMN_KANA, data.getKana());
        cv.put(COLUMN_BIRTHDAY, data.getBirth());
        cv.put(COLUMN_RELATION_ID, data.getRelationId());
        cv.put(COLUMN_PHOTO, data.getPhoto());
        cv.put(COLUMN_MEMO, data.getMemo());

        if (data.getId() == null || data.getId() == 0) {
            ret = db.insert(TABLE_NAME, "", cv);
            mInsertedId = ret;
        } else {
            String[] param = { Integer.toString(data.getId()) };
            db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", param);
        }

        return (ret != -1) ? true : false;
    }

    /**
     * メンバー削除
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param String fileDir 画像ファイル名
     * @param Integer id メンバーID
     * @param String name メンバー名
     * @return boolean 成功/失敗
     * @access public
     */
    public Boolean delete(SQLiteDatabase db, Integer id, String name)
    {
        //メンバー削除
        long ret = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { Integer.toString(id) });

        //削除対象メンバーが含まれている人物データにひと手間加える
        PersonDao personDao = new PersonDao();
        Boolean ret2 = personDao.slideMemberIdToName(db, id, name);

        return (ret != -1 && ret2 == true) ? true : false;
    }

    /**
     * インサートしたIDを返す
     * 
     * @return Integer インサートしたID
     * @access public
     */
    public Integer getInsertedId()
    {
        return Integer.valueOf(String.valueOf(mInsertedId));
    }
}
