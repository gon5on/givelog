package jp.co.e2.givelog.model;

/**
 * プレゼントクラス（セッタとゲッタ）
 * 
 * @access public
 */
public class Relation
{
	// テーブル名
	public static final String TABLE_NAME = "relation";

	// カラム名
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LABEL = "label";
	public static final String COLUMN_SORT = "sort";

	//値
	private Integer id;
	private String name;
	private Integer label;
	private Integer sort;

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
	 * グループ名をセット
	 * 
	 * @param String value グループ名
	 * @return void
	 * @access public
	 */
	public void setName(String value)
	{
		name = value;
	}

	public String getName()
	{
		return name;
	}

	/**
	 * グループ名を返す
	 * 
	 * @return String name グループ名
	 * @access public
	 */
	public void setLabel(Integer value)
	{
		label = value;
	}

	/**
	 * グループラベル色をセット
	 * 
	 * @param Integer value グループラベル色
	 * @return void
	 * @access public
	 */
	public Integer getLabel()
	{
		return label;
	}

	/**
	 * ソート値をセット
	 * 
	 * @param Integer value ID
	 * @return void
	 * @access public
	 */
	public void setSort(Integer value)
	{
		sort = value;
	}

	/**
	 * ソート値を返す
	 * 
	 * @return Integer sort ソート
	 * @access public
	 */
	public Integer getSort()
	{
		return sort;
	}
}
