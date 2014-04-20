package jp.co.e2.givelog.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * int系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msg_full … デフォルトではないエラーメッセージを使用したい場合に指定
 * point … 小数点第何位を指定したい場合に指定、指定しない場合はnull
 */
public class ValidateIsDouble
{
	Validate validate;		//バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @param Validate validate バリデーションクラス
	 */
	public ValidateIsDouble(Validate validate)
	{
		this.validate = validate;
	}

	/**
	 * 少数形式チェック（String）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full)
	{
		String pattern = "^([0-9]\\d*|0)(\\.\\d+)?$";

		if (check(value, pattern) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "を正しい形式で入力してください。");
			}
		}
	}

	/**
	 * 少数形式チェック（Int）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full)
	{
		check(String.valueOf(value), name, msg_full);
	}

	/**
	 * 少数形式チェック（Float）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full)
	{
		check(String.valueOf(value), name, msg_full);
	}

	/**
	 * 少数形式チェック（Double）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Double value, String name, String msg_full)
	{
		check(String.valueOf(value), name, msg_full);
	}

	/**
	 * 少数形式チェック
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer point 小数点第何位
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full, Integer point)
	{
		String pattern = "^([0-9]\\d*|0)(\\.\\d{" + point + "})?$";

		if (check(value, pattern) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は小数点第" + point + "位までで入力してください。");
			}
		}
	}

	/**
	 * 少数形式チェック（Int）
	 * 
	 * @param Int value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer point 小数点第何位
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full, Integer point)
	{
		check(String.valueOf(value), name, msg_full, point);
	}

	/**
	 * 少数形式チェック（Float）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer point 小数点第何位
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full, Integer point)
	{
		check(String.valueOf(value), name, msg_full, point);
	}

	/**
	 * 正規表現でチェック
	 * 
	 * @param String value 値
	 * @param String pattern_str パターン
	 * @return Boolean バリデート結果
	 * @access private
	 */
	private Boolean check(String value, String pattern_str)
	{
		if (validate.getValueResult() == false) {
			return true;
		}
		if (value == null || value.length() == 0) {
			return true;
		}

		Pattern pattern = java.util.regex.Pattern.compile(pattern_str);
		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}
}
