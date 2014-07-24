package jp.co.e2.givelog.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * メンバークラス（セッタとゲッタ）
 * 
 * @access public
 */
public class MemberEntity
{
    //値
    private Integer mId;
    private String mKana;
    private String mName;
    private String mBirth;
    private Integer mRelationId;
    private Integer mPhoto;
    private String mMemo;
    private String mRelationName;
    private Integer mLabel;

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
     * ふりがなをセット
     * 
     * @param String value ふりがな
     * @return void
     * @access public
     */
    public void setKana(String value)
    {
        mKana = value;
    }

    /**
     * ふりがなを返す
     * 
     * @return String mKana ふりがな
     * @access public
     */
    public String getKana()
    {
        return mKana;
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

    /**
     * 誕生日をセット
     * 
     * @param String 誕生日
     * @return void
     * @access public
     */
    public void setBirth(String value)
    {
        mBirth = value;
    }

    /**
     * 誕生日を返す
     * 
     * @return String mBirth 誕生日
     * @access public
     */
    public String getBirth()
    {
        return mBirth;
    }

    /**
     * 誕生日と年齢を返す
     * 
     * @return String 年齢
     * @access public
     */
    public String getBirthAge()
    {
        if (mBirth == null) {
            return null;
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Integer now = Integer.parseInt(sdf.format(date));

        Integer culc_birth = Integer.parseInt(mBirth.replaceAll("/", ""));
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
        mRelationId = value;
    }

    /**
     * グループIDを返す
     * 
     * @return Integer mRelationId グループID
     * @access public
     */
    public Integer getRelationId()
    {
        return mRelationId;
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
        mPhoto = value;
    }

    /**
     * 画像ファイル名を返す
     * 
     * @return String 画像ファイル名
     * @access public
     */
    public String getPhoto()
    {
        if (mPhoto == 0) {
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
        mMemo = value;
    }

    /**
     * メモを返す
     * 
     * @return String mMemo メモ
     * @access public
     */
    public String getMemo()
    {
        return mMemo;
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
        mRelationName = value;
    }

    /**
     * グループ名を取得
     * 
     * @return String mRelationName グループ名
     * @access public
     */
    public String getRelationName()
    {
        return mRelationName;
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
        mLabel = value;
    }

    /**
     * グループラベル色を返す
     * 
     * @return String mLabel グループラベル色
     * @access public
     */
    public Integer getLabel()
    {
        return mLabel;
    }
}
