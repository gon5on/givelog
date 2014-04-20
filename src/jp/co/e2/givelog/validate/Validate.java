package jp.co.e2.givelog.validate;

import java.util.ArrayList;

/**
 * バリデートクラス
 * 
 * こんな感じで使います ↓
 * バリデート対象の値を変えるたびにset()を忘れず呼ぶこと。
 * 同じ値に複数のバリデートをかける場合、エラーがあったらその後のバリデートは実行されません。
 * 各バリデートへの引数は、各バリデートクラス参照。
 * 
 * Validate validate = new Validate();
 * validate.set();
 * validate.require.check("山田太郎", "名前", "");
 * validate.max.check("山田太郎", "名前", "", 30);
 * 
 * validate.set();
 * validate.require.check("", "", "備考は必須になってますので、ご入力ください。");
 * 
 * @access public
 */
public class Validate
{
	private static Boolean result = true;			//全体のバリデート結果
	private static Boolean result_value = true;		//各値ごとのバリデート結果
	private static ArrayList<String> error_msg;		//エラーメッセージ

	public ValidateRequire require;					//必須系バリデーションクラス
	public ValidateIsInt isInt;						//int系バリデーションクラス
	public ValidateIsDouble isDouble;				//double系バリデーションクラス
	public ValidateMax max;							//最大値系バリデーションクラス
	public ValidateMin min;							//最小値系バリデーションクラス

	/**
	 * コンストラクタ
	 * 
	 * @access public
	 */
	public Validate()
	{
		result = true;
		result_value = true;
		error_msg = new ArrayList<String>();

		require = new ValidateRequire(this);
		isInt = new ValidateIsInt(this);
		isDouble = new ValidateIsDouble(this);
		max = new ValidateMax(this);
		min = new ValidateMin(this);
	}

	/**
	 * 値をバリデート
	 * 新たな値に対してバリデートを行う際に呼ぶこと
	 * 
	 * @return void
	 * @access public
	 */
	public void set()
	{
		result_value = true;
	}

	/**
	 * バリデート結果がエラーのため、エラーメッセージを追加する
	 * 
	 * @param msg
	 * @param msg_full
	 */
	public void error(String msg)
	{
		result = false;
		result_value = false;

		error_msg.add(msg);
	}

	/**
	 * 現在バリデート中の値に対しての結果を返す
	 * 
	 * @return boolean result_value バリデート結果
	 * @access public
	 */
	public Boolean getValueResult()
	{
		return result_value;
	}

	/**
	 * 全体のバリデート結果を返す
	 * 
	 * @return boolean result バリデート結果
	 * @access public
	 */
	public Boolean getResult()
	{
		return result;
	}

	/**
	 * エラ―文言を返す
	 * 
	 * @return ArrayList<String> error_msg
	 * @access public
	 */
	public ArrayList<String> getErrorMsg()
	{
		return error_msg;
	}

	/**
	 * 結果をセット（独自バリデートの結果を追加する場合に使用）
	 * 
	 * @param Boolean add_result 結果
	 * @return void
	 * @access public
	 */
	public void setResult(Boolean add_result)
	{
		if (add_result == false) {
			result_value = false;
			result = false;
		}
	}

	/**
	 * エラ―文言を追加（独自バリデートの結果を追加する場合に使用）
	 * 
	 * @param String add_msg 独自バリデートのメッセージ
	 * @return void
	 * @access public
	 */
	public void addErrorMsg(String add_msg)
	{
		error_msg.add(add_msg);
	}
}
