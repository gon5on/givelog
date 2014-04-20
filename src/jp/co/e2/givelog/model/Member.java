package jp.co.e2.givelog.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * メンバークラス（セッタとゲッタ）
 * 
 * @access public
 */
public class Member
{
	// テーブル名
	public static final String TABLE_NAME = "member";

	// カラム名
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_KANA = "kana";
	public static final String COLUMN_RELATION_ID = "relation_id";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_MEMO = "memo";

	//値
	private Integer id;
	private String name;
	private String kana;
	private String birth;
	private Integer relation_id;
	private Integer photo;
	private String memo;
	private String relation_name;
	private Integer label;

	/**
	 * IDをセット
	 * 
	 * @param Integer value ID
	 * @return void
	 * @access public
	 */
	public void setId(Integer value)
	{
		id = value;
	}

	/**
	 * IDを返す
	 * 
	 * @return Integer id ID
	 * @access public
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * ふりがなをセット
	 * 
	 * @param String value ふりがな
	 * @return void
	 * @access public
	 */
	public void setKana(String value)
	{
		kana = value;
	}

	/**
	 * ふりがなを返す
	 * 
	 * @return String kana ふりがな
	 * @access public
	 */
	public String getKana()
	{
		return kana;
	}

	/**
	 * 名前をセット
	 * 
	 * @param String value 名前
	 * @return void
	 * @access public
	 */
	public void setName(String value)
	{
		name = value;
	}

	/**
	 * 名前を返す
	 * 
	 * @return String name 名前
	 * @access public
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 誕生日をセット
	 * 
	 * @param String 誕生日
	 * @return void
	 * @access public
	 */
	public void setBirth(String value)
	{
		birth = value;
	}

	/**
	 * 誕生日を返す
	 * 
	 * @return String birth 誕生日
	 * @access public
	 */
	public String getBirth()
	{
		return birth;
	}

	/**
	 * 誕生日と年齢を返す
	 * 
	 * @return String 年齢
	 * @access public
	 */
	public String getBirthAge()
	{
		if (birth == null) {
			return null;
		}

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer now = Integer.parseInt(sdf.format(date));

		Integer culc_birth = Integer.parseInt(birth.replaceAll("/", ""));
		int age = (int) (now - culc_birth) / 10000;

		return getBirth() + " (" + age + "歳)";
	}

	/**
	 * グループIDをセット
	 * 
	 * @param Integer value グループID
	 * @return void
	 * @access public
	 */
	public void setRelationId(Integer value)
	{
		relation_id = value;
	}

	/**
	 * グループIDを返す
	 * 
	 * @return Integer relation_id グループID
	 * @access public
	 */
	public Integer getRelationId()
	{
		return relation_id;
	}

	/**
	 * 画像の有無フラグをセット
	 * 
	 * @param Integer value 画像の有無フラグ
	 * @return void
	 * @access public
	 */
	public void setPhoto(int value)
	{
		photo = value;
	}

	/**
	 * 画像ファイル名を返す
	 * 
	 * @return String 画像ファイル名
	 * @access public
	 */
	public String getPhoto()
	{
		if (photo == 0) {
			return null;
		}

		return "member_" + getId() + ".jpg";
	}

	/**
	 * メモをセット
	 * 
	 * @param String value メモ
	 * @return void
	 * @access public
	 */
	public void setMemo(String value)
	{
		memo = value;
	}

	/**
	 * メモを返す
	 * 
	 * @return String memo メモ
	 * @access public
	 */
	public String getMemo()
	{
		return memo;
	}

	/**
	 * グループ名をセット
	 * 
	 * @param String value メモ
	 * @return void
	 * @access public
	 */
	public void setRelationName(String value)
	{
		relation_name = value;
	}

	/**
	 * グループ名を取得
	 * 
	 * @return String relation_name グループ名
	 * @access public
	 */
	public String getRelationName()
	{
		return relation_name;
	}

	/**
	 * グループラベル色をセット
	 * 
	 * @param Integer value グループラベル色
	 * @return void
	 * @access public
	 */
	public void setLabel(Integer value)
	{
		label = value;
	}

	/**
	 * グループラベル色を返す
	 * 
	 * @return String label グループラベル色
	 * @access public
	 */
	public Integer getLabel()
	{
		return label;
	}
}
