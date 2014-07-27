package jp.co.e2.givelog.model;

import java.util.ArrayList;

import jp.co.e2.givelog.entity.RelationEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * グループテーブルのデータアクセスオブジェクト
 * 
 * @access
 */
public class RelationDao extends BaseDao
{
    // テーブル名
    public static final String TABLE_NAME = "relation";

    // カラム名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_SORT = "sort";

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
                COLUMN_ID + "       INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + "     TEXT," +
                COLUMN_LABEL + "    INTEGER," +
                COLUMN_SORT + "     INTEGER" +
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
    public ArrayList<RelationEntity> selectRelationList(SQLiteDatabase db)
    {
        ArrayList<RelationEntity> list = new ArrayList<RelationEntity>();

        String sql = String.format("SELECT * FROM %s ORDER BY %s, %s", TABLE_NAME, COLUMN_SORT, COLUMN_ID);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                RelationEntity relation = new RelationEntity();
                relation.setId(getInteger(cursor, COLUMN_ID));
                relation.setName(getString(cursor, COLUMN_NAME));
                relation.setLabel(getInteger(cursor, COLUMN_LABEL));

                list.add(relation);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    /**
     * スピナー用一覧取得
     * 
     * @param ArrayList<Relation> items グループクラス配列
     * @return String[] list スピナー用配列
     * @access public
     */
    public String[] getRelationSpinnerList(ArrayList<RelationEntity> items)
    {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < items.size(); i++) {
            RelationEntity item = items.get(i);
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
    public Integer getRelationSpinnerPosition(ArrayList<RelationEntity> items, Integer id)
    {
        Integer position = null;

        for (int i = 0; i < items.size(); i++) {
            RelationEntity item = items.get(i);

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
     * @param RelationEntity data
     * @return boolean 成功/失敗
     * @access public
     */
    public Boolean save(SQLiteDatabase db, RelationEntity data)
    {
        long ret;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, data.getName());
        cv.put(COLUMN_LABEL, data.getLabel());
        cv.put(COLUMN_SORT, data.getSort());

        if (data.getId() == null || data.getId() == 0) {
            ret = db.insert(TABLE_NAME, "", cv);
        } else {
            String[] param = { Integer.toString(data.getId()) };
            ret = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", param);
        }

        return (ret != -1) ? true : false;
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
        //TODO メンバーDaoにもってく
        //この関係性を使用している人物の関係性を強制的に未分類（ID=1）にする
        ContentValues cv = new ContentValues();
        cv.put("relation_id", 1);
        db.update("member", cv, "relation_id=?", new String[] { Integer.toString(id) });

        //削除
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { Integer.toString(id) });

        return true;
    }

    /**
     * 最新データ1件を取得
     * 
     * @param SQLiteDatabase db データベースオブジェクト
     * @return Relation relation グループクラス
     * @access public
     */
    public RelationEntity selectNewstData(SQLiteDatabase db)
    {
        RelationEntity relation = new RelationEntity();

        String sql = String.format("SELECT * FROM %s ORDER BY %s DESC limit 1", TABLE_NAME, COLUMN_ID);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                relation.setId(getInteger(cursor, COLUMN_ID));
                relation.setName(getString(cursor, COLUMN_NAME));
                relation.setLabel(getInteger(cursor, COLUMN_LABEL));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return relation;
    }
}
