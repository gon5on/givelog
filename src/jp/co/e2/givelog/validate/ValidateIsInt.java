package jp.co.e2.givelog.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 整数チェッククラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msg_full … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateIsInt
{
	Validate validate;		//バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @param Validate validate バリデーションクラス
	 */
	public ValidateIsInt(Validate validate)
	{
		this.validate = validate;
	}

	/**
	 * 整数チェック（String）
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
			return;
		}

		Pattern pattern = java.util.regex.Pattern.compile("^[+-]?[0-9]+$");
		Matcher matcher = pattern.matcher(value);

		if (matcher.matches() == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は整数で入力してください。");
			}
		}
	}

	/**
	 * 整数チェック（Int）
	 * 
	 * @param Int value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full)
	{
		check(String.valueOf(value), name, msg_full);
	}
}
