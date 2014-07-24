package jp.co.e2.givelog.entity;

/**
 * あげたもらったの人物クラス（セッタとゲッタ）
 * 
 * @access public
 */
public class PersonEntity
{
    //値
    private Integer mId;
    private Integer mPresentId;
    private Integer mType;
    private Integer mMemberId;
    private String mName;

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
     * プレゼントIDをセット
     * 
     * @param Integer value プレゼントID
     * @return void
     * @access public
     */
    public void setPresentId(Integer value)
    {
        mPresentId = value;
    }

    /**
     * プレゼントIDを返す
     * 
     * @return Integer mPresentId プレゼントID
     * @access public
     */
    public Integer getPresentId()
    {
        return mPresentId;
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
        mType = value;
    }

    /**
     * あげたかもらったかのタイプを返す
     * 
     * @return Integer mType あげたかもらったかのタイプ
     * @access public
     */
    public Integer getType()
    {
        return mType;
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
        mMemberId = value;
    }

    /**
     * メンバーIDを返す
     * 
     * @return Integer mMemberId メンバーID
     * @access public
     */
    public Integer getMemberId()
    {
        return mMemberId;
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
        mName = value;
    }

    /**
     * カスタム人物名を返す
     * 
     * @return String mName カスタム人物名
     * @access public
     */
    public String getName()
    {
        return mName;
    }
}
