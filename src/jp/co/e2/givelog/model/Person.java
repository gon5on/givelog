package jp.co.e2.givelog.model;

/**
 * あげたもらったの人物クラス（セッタとゲッタ）
 * 
 * @access public
 */
public class Person
{
	// テーブル名
	public static final String TABLE_NAME = "person";

	// カラム名
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PRESENT_ID = "present_id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_MEMBER_ID = "member_id";
	public static final String COLUMN_NAME = "name";

	//値
	private Integer id;
	private Integer present_id;
	private Integer type;
	private Integer member_id;
	private String name;

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
	 * プレゼントIDをセット
	 * 
	 * @param Integer value プレゼントID
	 * @return void
	 * @access public
	 */
	public void setPresentId(Integer value)
	{
		present_id = value;
	}

	/**
	 * プレゼントIDを返す
	 * 
	 * @return Integer present_id プレゼントID
	 * @access public
	 */
	public Integer getPresentId()
	{
		return present_id;
	}

	/**
	 * あげたかもらったかのタイプをセット
	 * 
	 * @param Integer value あげたかもらったかのタイプ
	 * @return void
	 * @access public
	 */
	public void setType(Integer value)
	{
		type = value;
	}

	/**
	 * あげたかもらったかのタイプを返す
	 * 
	 * @return Integer type あげたかもらったかのタイプ
	 * @access public
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * メンバーIDをセット
	 * 
	 * @param Integer value メンバーID
	 * @return void
	 * @access public
	 */
	public void setMemberId(Integer value)
	{
		member_id = value;
	}

	/**
	 * メンバーIDを返す
	 * 
	 * @return Integer member_id メンバーID
	 * @access public
	 */
	public Integer getMemberId()
	{
		return member_id;
	}

	/**
	 * カスタム人物名をセット
	 * 
	 * @param String value カスタム人物名
	 * @return void
	 * @access public
	 */
	public void setName(String value)
	{
		name = value;
	}

	/**
	 * カスタム人物名を返す
	 * 
	 * @return String name カスタム人物名
	 * @access public
	 */
	public String getName()
	{
		return name;
	}
}
