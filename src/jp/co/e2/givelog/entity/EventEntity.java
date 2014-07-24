package jp.co.e2.givelog.entity;

/**
 * メンバークラス（セッタとゲッタ）
 * 
 * @access public
 */
public class EventEntity
{
    //値
    private Integer mId;
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
     * 名前をセット
     * 
     * @param String value 名前
     * @return void
     * @access public
     */
    public void setName(String value)
    {
        mName = value;
    }

    /**
     * 名前を返す
     * 
     * @return String mName 名前
     * @access public
     */
    public String getName()
    {
        return mName;
    }
}
