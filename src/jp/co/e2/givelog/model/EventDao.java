package jp.co.e2.givelog.model;

import jp.co.e2.givelog.entity.EventEntity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Eventテーブルへのデータアクセスオブジェクト
 * 
 * @access public
 */
public class EventDao extends AppDao
{
    //テーブル名
    public static final String TABLE_NAME = "event";

    //カラム名
    public static final String COLUMN_ID = "id";
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
                COLUMN_ID + "    INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + "  TEXT" +
                ")";
        db.execSQL(sql);
    }

    /**
     * 保存処理
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @param Event data イベントオブジェクト
     * @return boolean 成功/失敗
     */
    public Boolean save(SQLiteDatabase db, EventEntity data) throws Exception
    {
        long ret = 0;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, data.getName());

        if (data.getId() == 0 || data.getId() == null) {
            ret = db.insert(TABLE_NAME, "", cv);
        } else {
            String[] param = { Integer.toString(data.getId()) };
            ret = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", param);
        }

        return (ret != -1) ? true : false;
    }
}
