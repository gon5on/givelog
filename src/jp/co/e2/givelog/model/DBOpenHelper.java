package jp.co.e2.givelog.model;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.Config;
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
	private Context context;		//コンテキスト

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @access public
	 */
	public DBOpenHelper(Context context)
	{
		super(context, Config.DATABASE_NAME, null, 1);

		this.context = context;
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
			RelationDao relationDao = new RelationDao(context);
			relationDao.createTable(db);
			relationDao.save(db, 0, "未分類", context.getResources().getColor(R.color.group9), 9999);	//ID = 1は未分類とする
			relationDao.save(db, 0, "家族", context.getResources().getColor(R.color.group1), 1);
			relationDao.save(db, 0, "親戚", context.getResources().getColor(R.color.group2), 1);
			relationDao.save(db, 0, "友達", context.getResources().getColor(R.color.group5), 1);
			relationDao.save(db, 0, "会社", context.getResources().getColor(R.color.group7), 1);

			//イベントテーブル作成
			EventDao eventDao = new EventDao(context);
			eventDao.createTable(db);
			eventDao.save(db, 0, "誕生日");
			eventDao.save(db, 0, "母の日");
			eventDao.save(db, 0, "父の日");
			eventDao.save(db, 0, "こどもの日");
			eventDao.save(db, 0, "敬老の日");
			eventDao.save(db, 0, "勤労感謝の日");
			eventDao.save(db, 0, "バレンタインデー");
			eventDao.save(db, 0, "ホワイトデー");
			eventDao.save(db, 0, "クリスマス");
			eventDao.save(db, 0, "結婚記念日");
			eventDao.save(db, 0, "出産祝い");
			eventDao.save(db, 0, "結婚祝い");
			eventDao.save(db, 0, "新築祝い");
			eventDao.save(db, 0, "お中元");
			eventDao.save(db, 0, "お歳暮");

			//人物テーブル作成
			MemberDao memberDao = new MemberDao(context);
			memberDao.createTable(db);

			//プレゼントテーブル
			PresentDao presentDao = new PresentDao(context);
			presentDao.createTable(db);

			//誰から誰へテーブル
			PersonDao personDao = new PersonDao(context);
			personDao.createTable(db);

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
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