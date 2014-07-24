package jp.co.e2.givelog.model;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.entity.EventEntity;
import jp.co.e2.givelog.entity.RelationEntity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelperのラッパークラス
 * 
 * @access public
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
    private Context mContext;       //コンテキスト

    /**
     * コンストラクタ
     * 
     * @param Context context コンテキスト
     * @access public
     */
    public DBOpenHelper(Context context)
    {
        super(context, Config.DATABASE_NAME, null, 1);

        mContext = context;
    }

    /**
     * onCreate
     * 
     * @param SQLiteDatabase db
     * @return void
     * @access public
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.beginTransaction();

        try {
            //関係性テーブル作成
            RelationDao relationDao = new RelationDao();
            relationDao.createTable(db);
            relationDao.save(db, setRelationEntity("未分類", R.color.group9, 9999));	//ID = 1は未分類とする
            relationDao.save(db, setRelationEntity("家族", R.color.group1, 1));
            relationDao.save(db, setRelationEntity("親戚", R.color.group2, 1));
            relationDao.save(db, setRelationEntity("友達", R.color.group5, 1));
            relationDao.save(db, setRelationEntity("会社", R.color.group7, 1));

            //イベントテーブル作成
            EventDao eventDao = new EventDao();
            eventDao.createTable(db);
            eventDao.save(db, setEventEntity("誕生日"));
            eventDao.save(db, setEventEntity("母の日"));
            eventDao.save(db, setEventEntity("父の日"));
            eventDao.save(db, setEventEntity("こどもの日"));
            eventDao.save(db, setEventEntity("敬老の日"));
            eventDao.save(db, setEventEntity("勤労感謝の日"));
            eventDao.save(db, setEventEntity("バレンタインデー"));
            eventDao.save(db, setEventEntity("ホワイトデー"));
            eventDao.save(db, setEventEntity("クリスマス"));
            eventDao.save(db, setEventEntity("結婚記念日"));
            eventDao.save(db, setEventEntity("出産祝い"));
            eventDao.save(db, setEventEntity("結婚祝い"));
            eventDao.save(db, setEventEntity("新築祝い"));
            eventDao.save(db, setEventEntity("お中元"));
            eventDao.save(db, setEventEntity("お歳暮"));

            //人物テーブル作成
            MemberDao memberDao = new MemberDao();
            memberDao.createTable(db);

            //プレゼントテーブル
            PresentDao presentDao = new PresentDao();
            presentDao.createTable(db);

            //誰から誰へテーブル
            PersonDao personDao = new PersonDao();
            personDao.createTable(db);

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * イベントエンティティにデータを入れる
     * 
     * @param SQLiteDatabase db
     * @param String name
     * @return EventEntity
     * @access private
     */
    private EventEntity setEventEntity(String name)
    {
        EventEntity data = new EventEntity();
        data.setName(name);

        return data;
    }

    /**
     * リレーションエンティティにデータを入れる
     * 
     * @param SQLiteDatabase db
     * @param String name
     * @return RelationEntity
     * @access private
     */
    private RelationEntity setRelationEntity(String name, Integer label, Integer sort)
    {
        RelationEntity data = new RelationEntity();
        data.setName(name);
        data.setLabel(mContext.getResources().getColor(label));
        data.setSort(sort);

        return data;
    }

    /**
     * onCreate
     * 
     * @param SQLiteDatabase db
     * @param Integer oldVersion 前のバージョン
     * @param Integer newVersion 新しいバージョン
     * @return void
     * @access public
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //upgrade時に使用
    }
}