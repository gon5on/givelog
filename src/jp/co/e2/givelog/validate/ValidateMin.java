package jp.co.e2.givelog.validate;

/**
 * 最小値系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msg_full … デフォルトではないエラーメッセージを使用したい場合に指定
 * limit … MIN値
 */
public class ValidateMin
{
	Validate validate;		//バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @param Validate validate バリデーションクラス
	 */
	public ValidateMin(Validate validate)
	{
		this.validate = validate;
	}

	/**
	 * MINチェック（値→String、MIN→String）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full, String limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null || value.length() == 0) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→String、MIN→Integer）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full, Integer limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null || value.length() == 0) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→String、MIN→Float）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full, Float limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null || value.length() == 0) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→String、MIN→Double）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(String value, String name, String msg_full, Double limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null || value.length() == 0) {
			return;
		}
		if (check(Double.valueOf(value), limit) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Integer、MIN→String）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full, String limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Integer、MIN→Integer）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full, Integer limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Integer、MIN→Float）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full, Float limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Integer、MIN→Double）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Integer value, String name, String msg_full, Double limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), limit) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Float、MIN→String）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full, String limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Float、MIN→Integer）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full, Integer limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Float、MIN→Float）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full, Float limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Float、MIN→Double）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Float value, String name, String msg_full, Double limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(Double.valueOf(value), limit) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Double、MIN→String）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Double value, String name, String msg_full, String limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(value, Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Double、MIN→Integer）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Double value, String name, String msg_full, Integer limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(value, Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Double、MIN→Float）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Double value, String name, String msg_full, Float limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(value, Double.valueOf(limit)) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック（値→Double、MIN→Double）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MIN文字数
	 * @return void
	 * @access public
	 */
	public void check(Double value, String name, String msg_full, Double limit)
	{
		if (validate.getValueResult() == false) {
			return;
		}
		if (value == null) {
			return;
		}
		if (check(value, limit) == false) {
			if (msg_full.length() != 0) {
				validate.error(msg_full);
			} else {
				validate.error(name + "は" + limit + "以上で入力してください");
			}
		}
	}

	/**
	 * MINチェック
	 * 
	 * @param Double value 値
	 * @param Double limit MIN文字数
	 * @return boolen バリデート結果
	 * @access private
	 */
	private Boolean check(Double value, Double limit)
	{
		return value < limit;
	}
}
