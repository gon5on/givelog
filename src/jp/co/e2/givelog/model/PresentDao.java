package jp.co.e2.givelog.model;

import java.util.ArrayList;

import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.entity.PersonEntity;
import jp.co.e2.givelog.entity.PresentEntity;
import android.content.ContentValues;
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
    // テーブル名
    public static final String TABLE_NAME = "present";

    // カラム名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_PRESENT = "present";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_MEMO = "memo";

    private long mInsertedId;
    private ArrayList<MemberEntity> mMembers;
    private ArrayList<Integer> mFromList = new ArrayList<Integer>();
    private ArrayList<Integer> mToList = new ArrayList<Integer>();
    private String mFromCustom;
    private String mToCustom;

    /**
     * テーブル作成
     * 
     * @return void
     * @access public
     */
    public void createTable(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + "                INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATE + "              TEXT," +
                COLUMN_EVENT + "             TEXT," +
                COLUMN_PRESENT + "           TEXT," +
                COLUMN_PRICE + "             INTEGER," +
                COLUMN_PHOTO + "             INTEGER," +
                COLUMN_MEMO + "              TEXT" +
                ")";
        db.execSQL(sql);
    }

    /**
     * 人物詳細で閲覧するプレゼントリストを取得
     * 
     * @param Integer memberId メンバーID
     * @param Integer type あげたかもらったかのタイプ
     * @return ArrayList<Present> presents プレゼントクラスの配列
     * @access public
     */
    public ArrayList<PresentEntity> selectPresentList(SQLiteDatabase db, Integer memberId, Integer type)
    {
        //対象の人物がかかわっているプレゼントデータを取得
        ArrayList<PresentEntity> presents = selectMembersPresentList(db, memberId, type);

        //プレゼントデータから関わっている人物を取得
        for (int i = 0; i < presents.size(); i++) {
            PresentEntity present = presents.get(i);
            ArrayList<PersonEntity> persons = selectPersonList(db, present.getId());

            for (int j = 0; j < persons.size(); j++) {
                PersonEntity person = persons.get(j);

                //あげた
                if (person.getType() == Config.GIVE_FLG) {
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
     * @param Integer presenId プレゼントID
     * @return Present present プレゼントオブジェクト
     * @access public
     */
    public PresentEntity selectPresentData(SQLiteDatabase db, Integer presenId)
    {
        PresentEntity present = new PresentEntity();

        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);

        String[] param = { Integer.toString(presenId) };

        Cursor cursor = db.rawQuery(sql.toString(), param);

        if (cursor.moveToFirst()) {
            do {
                present.setId(cursor.getInt(0));
                present.setDate(cursor.getString(1));
                present.setEvent(cursor.getString(2));
                present.setPresent(cursor.getString(3));
                present.setPrice(cursor.getString(4));
                present.setPhoto(cursor.getInt(5));
                present.setMemo(cursor.getString(6));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return present;
    }

    /**
     * 人物IDを主体にして、プレセントクラス配列を返す
     * 
     * @param memberId メンバーID
     * @param Integer type あげたかもらったかのタイプ
     * @return ArrayList<Present> list プレゼントクラス
     * @access public
     */
    public ArrayList<PresentEntity> selectMembersPresentList(SQLiteDatabase db, Integer memberId, Integer type)
    {
        ArrayList<PresentEntity> list = new ArrayList<PresentEntity>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM person ");
        sql.append("LEFT JOIN present ON present.id = person.present_id ");
        sql.append("WHERE person.member_id = " + memberId + " ");
        sql.append("AND person.type = " + type + " ");
        sql.append("ORDER BY present.date DESC, present.id DESC");

        Cursor cursor = db.rawQuery(sql.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                PresentEntity present = new PresentEntity();
                present.setId(cursor.getInt(1));
                present.setId(cursor.getInt(5));
                present.setDate(cursor.getString(6));
                present.setEvent(cursor.getString(7));
                present.setPresent(cursor.getString(8));
                present.setPrice(cursor.getString(9));
                present.setPhoto(cursor.getInt(10));
                present.setMemo(cursor.getString(11));
                list.add(present);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    /**
     * プレゼントIDを主体にして、誰から誰へクラスを取得する
     * 
     * @param Integer present_id プレゼントID
     * @return ArrayList<Person> 人物クラス配列
     * @access public
     */
    public ArrayList<PersonEntity> selectPersonList(SQLiteDatabase db, Integer present_id)
    {
        ArrayList<PersonEntity> list = new ArrayList<PersonEntity>();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM person ");
            sql.append("LEFT JOIN member ON member.id = person.member_id ");
            sql.append("WHERE person.present_id = " + present_id + " ");
            sql.append("ORDER BY member.kana IS NULL ASC, member.kana, member.id ASC");

            Cursor cursor = db.rawQuery(sql.toString(), null);

            while (cursor.moveToNext()) {
                PersonEntity person = new PersonEntity();
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
     * @param PresentEntity data
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
    public Boolean save(SQLiteDatabase db, PresentEntity data, int id, Integer photo, boolean[] from, boolean[] to, String date, String event, String present, String price, String memo, String add_from, String add_to)
    {
        //プレゼント保存
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, data.getDate());
        cv.put(COLUMN_EVENT, data.getEvent());
        cv.put(COLUMN_PRESENT, data.getPresent());
        cv.put(COLUMN_PRICE, data.getPrice());
        cv.put(COLUMN_PHOTO, data.getPhoto());
        cv.put(COLUMN_MEMO, data.getMemo());

        if (data.getId() == null || data.getId() == 0) {
            id = (int) db.insert(TABLE_NAME, "", cv);
            mInsertedId = id;
        } else {
            db.update(TABLE_NAME, cv, "id=?", new String[] { Integer.toString(id) });
        }

        //だれからだれへデータをすべて削除する
        db.delete("person", "present_id=?", new String[] { Integer.toString(id) });

        //だれから保存
        for (int i = 0; i < from.length - 1; i++) {
            if (from[i] == true) {
                ContentValues cv2 = new ContentValues();
                cv2.put("present_id", id);
                cv2.put("type", 1);
                cv2.put("member_id", mMembers.get(i).getId());
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
                cv2.put("member_id", mMembers.get(i).getId());
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

        return true;
    }

    /**
     * メンバー配列をセット
     * 
     * @param ArrayList<Member> list メンバー配列
     * @return void
     * @access public
     */
    public void setMembers(ArrayList<MemberEntity> list)
    {
        mMembers = list;
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
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { Integer.toString(id) });
        db.delete("person", "present_id=?", new String[] { Integer.toString(id) });

        return true;
    }

    /**
     * プレゼントIDを主にして、だれからだれへ配列（メンバーIDそのまま）を取得
     * 
     * @param Integer presentId プレゼントID
     * @return void
     * @access public
     */
    public void selectPresentPerson(SQLiteDatabase db, Integer presentId)
    {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM person WHERE present_id = " + presentId);

            Cursor cursor = db.rawQuery(sql.toString(), null);
            cursor.moveToFirst();

            do {
                //カスタムの場合
                if (cursor.getString(4).length() != 0) {
                    if (cursor.getInt(2) == 1) {
                        mFromCustom = cursor.getString(4);
                    } else {
                        mToCustom = cursor.getString(4);
                    }
                }
                //メンバーIDが存在する場合
                else {
                    if (cursor.getInt(2) == 1) {
                        mFromList.add(cursor.getInt(3));
                    } else {
                        mToList.add(cursor.getInt(3));
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
     * @param Integer presentId プレゼントID
     * @return ArrayList<Integer> mFromList 誰から配列
     * @access public
     */
    public ArrayList<Integer> selectFromList(SQLiteDatabase db, Integer presentId)
    {
        if (mFromList.size() == 0 && mFromCustom == null) {
            selectPresentPerson(db, presentId);
        }

        return mFromList;
    }

    /**
     * プレゼントIDを主にして、だれへ配列（メンバーIDそのまま）を取得
     * 
     * @param Integer presentId プレゼントID
     * @return ArrayList<Integer> mToList 誰へ配列
     * @access public
     */
    public ArrayList<Integer> selectToList(SQLiteDatabase db, Integer presentId)
    {
        if (mToList.size() == 0 && mToCustom == null) {
            selectPresentPerson(db, presentId);
        }

        return mToList;
    }

    /**
     * プレゼントIDを主にして、だれからのカスタム値を取得
     * 
     * @param Integer presentId プレゼントID
     * @return String mFromCustom 誰からカスタム値
     * @access public
     */
    public String getFromCustom(SQLiteDatabase db, Integer presentId)
    {
        if (mFromList.size() == 0 && mFromCustom == null) {
            selectPresentPerson(db, presentId);
        }

        return mFromCustom;
    }

    /**
     * プレゼントIDを主にして、だれへのカスタム値を取得
     * 
     * @param Integer presentId プレゼントID
     * @return String mToCustom だれへカスタム値
     * @access public
     */
    public String getToCustom(SQLiteDatabase db, Integer presentId)
    {
        if (mToList.size() == 0 && mToCustom == null) {
            selectPresentPerson(db, presentId);
        }

        return mToCustom;
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
