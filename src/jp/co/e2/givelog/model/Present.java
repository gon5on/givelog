package jp.co.e2.givelog.model;

/**
 * プレゼントクラス（セッタとゲッタ）
 * 
 * @access public
 */
public class Present
{
	// テーブル名
	public static final String TABLE_NAME = "present";

	// カラム名
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_EVENT = "event";
	public static final String COLUMN_PRESENT = "present";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_MEMO = "memo";

	//値
	private Integer id;
	private String date;
	private String event;
	private String present;
	private String price;
	private Integer photo;
	private String memo;
	private String give = "";
	private String gave = "";
	private String give_full = "";
	private String gave_full = "";
	private Integer give_more_flg = 0;
	private Integer gave_more_flg = 0;

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
	 * 日付をセット
	 * 
	 * @param String value 日付
	 * @return void
	 * @access public
	 */
	public void setDate(String value)
	{
		date = value;
	}

	/**
	 * 日付を返す
	 * 
	 * @return String date 日付
	 * @access public
	 */
	public String getDate()
	{
		return date;
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
		event = value;
	}

	/**
	 * イベントを返す
	 * 
	 * @return String event イベント
	 * @access public
	 */
	public String getEvent()
	{
		return event;
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
		present = value;
	}

	/**
	 * プレゼントを返す
	 * 
	 * @return String present プレゼント
	 * @access public
	 */
	public String getPresent()
	{
		return present;
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
		price = value;
	}

	/**
	 * 値段を返す
	 * 
	 * @return String price 値段
	 * @access public
	 */
	public String getPrice()
	{
		return price;
	}

	/**
	 * 単位付きの表示用値段を返す
	 * 
	 * @param Integer frame_flg 値段に()を付けるかどうかフラグ
	 * @return String 単位付きの表示用値段
	 * @access public
	 */
	public String getPriceUnit(Integer frame_flg)
	{
		if (price.length() == 0) {
			return "";
		}

		String str_price;

		if (price.length() > 3) {
			str_price = String.format("%1$,3d", Integer.parseInt(price));
		} else {
			str_price = price;
		}

		if (frame_flg == 1) {
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
	 * あげた人物名をセット
	 * 
	 * @param String value あげた人物名
	 * @return void
	 * @access public
	 */
	public void setGive(String value)
	{
		//詳細用
		if (give_full != "") {
			give_full = give_full + ", ";
		}
		give_full = give_full + value;

		//一覧用
		if (give_more_flg == 1) {
			return;
		}
		if (give.length() >= 10) {
			give = give + "...";
			give_more_flg = 1;
			return;
		}
		if (give != "") {
			give = give + ", ";
		}
		give = give + value;
	}

	/**
	 * あげた人を返す
	 * 
	 * @return String give あげた人
	 * @access public
	 */
	public String getGive()
	{
		return give;
	}

	/**
	 * あげた人（頭から10文字）を返す
	 * 
	 * @return String give_full あげた人（頭から10文字）を返す
	 * @access public
	 */
	public String getGiveFull()
	{
		return give_full;
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
		if (gave_full != "") {
			gave_full = gave_full + ", ";
		}
		gave_full = gave_full + value;

		//一覧用
		if (gave_more_flg == 1) {
			return;
		}
		if (gave.length() >= 10) {
			gave = gave + "...";
			gave_more_flg = 1;
			return;
		}
		if (gave != "") {
			gave = gave + ", ";
		}
		gave = gave + value;
	}

	/**
	 * もらった人を返す
	 * 
	 * @return String gave もらった人
	 * @access public
	 */
	public String getGave()
	{
		return gave;
	}

	/**
	 * もらった人（頭から10文字）を返す
	 * 
	 * @return String gave_full もらった人（頭から10文字）を返す
	 * @access public
	 */
	public String getGaveFull()
	{
		return gave_full;
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