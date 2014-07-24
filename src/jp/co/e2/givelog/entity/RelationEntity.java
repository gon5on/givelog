package jp.co.e2.givelog.entity;

/**
 * プレゼントクラス（セッタとゲッタ）
 * 
 * @access public
 */
public class RelationEntity
{
    //値
    private Integer mId;
    private String mName;
    private Integer mLabel;
    private Integer mSort;

    /**
     * IDをセット
     * 
     * @param Integer value ID
     * @return void
     * @access public
     */
    public void setId(Integer value)
    {
        mId = value;
    }

    /**
     * IDを返す
     * 
     * @return Integer mId ID
     * @access public
     */
    public Integer getId()
    {
        return mId;
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
        mName = value;
    }

    /**
     * グループ名を返す
     * 
     * @return String mName グループ名
     * @access public
     */
    public String getName()
    {
        return mName;
    }

    /**
     * グループラベル色をセットする
     * 
     * @return String mLabel グループラベル色
     * @access public
     */
    public void setLabel(Integer value)
    {
        mLabel = value;
    }

    /**
     * グループラベル色を返す
     * 
     * @return Integer mLabel グループラベル色
     * @access public
     */
    public Integer getLabel()
    {
        return mLabel;
    }

    /**
     * ソート値をセット
     * 
     * @param Integer value ソート値
     * @return void
     * @access public
     */
    public void setSort(Integer value)
    {
        mSort = value;
    }

    /**
     * ソート値を返す
     * 
     * @return Integer mSort ソート値
     * @access public
     */
    public Integer getSort()
    {
        return mSort;
    }
}
