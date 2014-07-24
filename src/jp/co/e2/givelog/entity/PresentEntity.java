package jp.co.e2.givelog.entity;

/**
 * プレゼントクラス（セッタとゲッタ）
 * 
 * @access public
 */
public class PresentEntity
{
    //値
    private Integer mId;
    private String mDate;
    private String mEvent;
    private String mPresent;
    private String mPrice;
    private Integer mPhoto;
    private String mMemo;
    private String mGive = "";
    private String mGave = "";
    private String mGiveFull = "";
    private String mGaveFull = "";
    private Integer mGiveMoreFlg = 0;
    private Integer mGaveMoreFlg = 0;

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
     * 日付をセット
     * 
     * @param String value 日付
     * @return void
     * @access public
     */
    public void setDate(String value)
    {
        mDate = value;
    }

    /**
     * 日付を返す
     * 
     * @return String mDate 日付
     * @access public
     */
    public String getDate()
    {
        return mDate;
    }

    /**
     * イベントをセット
     * 
     * @param String value イベント
     * @return void
     * @access public
     */
    public void setEvent(String value)
    {
        mEvent = value;
    }

    /**
     * イベントを返す
     * 
     * @return String mEvent イベント
     * @access public
     */
    public String getEvent()
    {
        return mEvent;
    }

    /**
     * プレゼントをセット
     * 
     * @param String value プレゼント
     * @return void
     * @access public
     */
    public void setPresent(String value)
    {
        mPresent = value;
    }

    /**
     * プレゼントを返す
     * 
     * @return String mPresent プレゼント
     * @access public
     */
    public String getPresent()
    {
        return mPresent;
    }

    /**
     * 値段をセット
     * 
     * @param String value 値段
     * @return void
     * @access public
     */
    public void setPrice(String value)
    {
        mPrice = value;
    }

    /**
     * 値段を返す
     * 
     * @return String mPrice 値段
     * @access public
     */
    public String getPrice()
    {
        return mPrice;
    }

    /**
     * 単位付きの表示用値段を返す
     * 
     * @param Integer frameFlg 値段に()を付けるかどうかフラグ
     * @return String 単位付きの表示用値段
     * @access public
     */
    public String getPriceUnit(Integer frameFlg)
    {
        if (mPrice.length() == 0) {
            return "";
        }

        String str_price;

        if (mPrice.length() > 3) {
            str_price = String.format("%1$,3d", Integer.parseInt(mPrice));
        } else {
            str_price = mPrice;
        }

        if (frameFlg == 1) {
            str_price = " (" + str_price + "円)";
        } else {
            str_price = str_price + "円";
        }

        return str_price;
    }

    /**
     * 写真の有無フラグをセット
     * 
     * @param Integer value 写真の有無フラグ
     * @return void
     * @access public
     */
    public void setPhoto(Integer value)
    {
        mPhoto = value;
    }

    /**
     * 画像ファイル名を返す
     * 
     * @return String mPhoto 画像ファイル名
     * @access public
     */
    public String getPhoto()
    {
        if (mPhoto == 0) {
            return null;
        }

        return "present_" + getId() + ".jpg";
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
     * あげた人物名をセット
     * 
     * @param String value あげた人物名
     * @return void
     * @access public
     */
    public void setGive(String value)
    {
        //詳細用
        if (mGiveFull != "") {
            mGiveFull = mGiveFull + ", ";
        }
        mGiveFull = mGiveFull + value;

        //一覧用
        if (mGiveMoreFlg == 1) {
            return;
        }
        if (mGive.length() >= 10) {
            mGive = mGive + "...";
            mGiveMoreFlg = 1;
            return;
        }
        if (mGive != "") {
            mGive = mGive + ", ";
        }

        mGive = mGive + value;
    }

    /**
     * あげた人を返す
     * 
     * @return String give あげた人
     * @access public
     */
    public String getGive()
    {
        return mGive;
    }

    /**
     * あげた人（頭から10文字）を返す
     * 
     * @return String give_full あげた人（頭から10文字）を返す
     * @access public
     */
    public String getGiveFull()
    {
        return mGiveFull;
    }

    /**
     * もらった人物名をセット
     * 
     * @param String value もらった人物名
     * @return void
     * @access public
     */
    public void setGave(String value)
    {
        //詳細用
        if (mGaveFull != "") {
            mGaveFull = mGaveFull + ", ";
        }
        mGaveFull = mGaveFull + value;

        //一覧用
        if (mGaveMoreFlg == 1) {
            return;
        }
        if (mGave.length() >= 10) {
            mGave = mGave + "...";
            mGaveMoreFlg = 1;
            return;
        }
        if (mGave != "") {
            mGave = mGave + ", ";
        }

        mGave = mGave + value;
    }

    /**
     * もらった人（頭から10文字）を返す
     * 
     * @return String mGaveFull もらった人（頭から10文字）を返す
     * @access public
     */
    public String getGave()
    {
        return mGave;
    }

    /**
     * もらった人を返す
     * 
     * @return String mGave もらった人
     * @access public
     */
    public String getGaveFull()
    {
        return mGaveFull;
    }

    /**
     * 一覧表示用のだれからだれへを返す
     * 
     * @param Integer type だれからかだれへかタイプ
     * @return String kana ふりがな
     * @access public
     */
    public String getPresentForList(Integer type)
    {
        String connect = "";
        String person = "";

        if (type == 1) {
            person = getGave();
            connect = " へ ";
        } else {
            person = getGive();
            connect = " から ";
        }

        return person + connect + getPresent();
    }
}