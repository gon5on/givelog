package jp.co.e2.givelog.validate;

/**
 * 必須チェッククラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msg_full … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateRequire
{
	Validate validate;		//バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @param Validate validate バリデーションクラス
	 */
	public ValidateRequire(Validate validate)
	{
		this.validate = validate;
	}

	/**
	 * String型必須チェック
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null || value.length() == 0) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "を入力してください。");
			}
		}
	}

	/**
	 * Int型必須チェック
	 * 
	 * @param Int value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "を入力してください。");
			}
		}
	}

	/**
	 * Float型必須チェック
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "を入力してください。");
			}
		}
	}

	/**
	 * セレクトボックス必須チェック
	 * 
	 * @param boolean[] value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(boolean[] value, String name, String msg_full)
	{
		Boolean flg = false;

		for (int i = 0; i < value.length; i++) {
			if (value[i] == true) {
				flg = true;
				break;
			}
		}

		if (flg == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "を入力してください。");
			}
		}
	}
}
