package jp.co.e2.givelog.validate;

/**
 * 最大値系バリデーションクラス
 * 
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * msg_full … デフォルトではないエラーメッセージを使用したい場合に指定
 * limit … MAX値
 */
public class ValidateMax
{
	Validate validate;		//バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @param Validate validate バリデーションクラス
	 */
	public ValidateMax(Validate validate) {
		this.validate = validate;
	}

	/**
	 * MAXチェック（値→String、MAX→String）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→String、MAX→Integer）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→String、MAX→Float）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→String、MAX→Double）
	 * 
	 * @param String value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Integer、MAX→String）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Integer、MAX→Integer）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Integer、MAX→Float）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Integer、MAX→Double）
	 * 
	 * @param Integer value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Float、MAX→String）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Float、MAX→Integer）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Float、MAX→Float）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Float、MAX→Double）
	 * 
	 * @param Float value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Double、MAX→String）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param String limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Double、MAX→Integer）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Integer limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Double、MAX→Float）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Float limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック（値→Double、MAX→Double）
	 * 
	 * @param Double value 値
	 * @param String name 変数名
	 * @param String msg_full エラーメッセージ全文
	 * @param Double limit MAX文字数
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
				validate.error(name + "は" + limit + "以下で入力してください");
			}
		}
	}

	/**
	 * MAXチェック
	 * 
	 * @param Double value 値
	 * @param Double limit MAX文字数
	 * @return boolen バリデート結果
	 * @access private
	 */
	private Boolean check(Double value, Double limit)
	{
		return value > limit;
	}
}
