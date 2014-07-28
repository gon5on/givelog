package jp.co.e2.givelog.validate;

/**
 * 文字長バリデーションクラス
 * 
 * validate … バリデートクラス
 * value … バリデート対象の値
 * name … 値の名前（誕生日、性別とか）
 * length … 文字長
 * msgFull … デフォルトではないエラーメッセージを使用したい場合に指定
 */
public class ValidateLength
{
    public static final String ERROR_MSG_LENGTH_MIN = "%sは%s文字以上で入力してください。";
    public static final String ERROR_MSG_LENGTH_MAX = "%sは%s文字以下で入力してください。";

    /**
     * 文字長MAXチェック（String）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer length 文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer length, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() > length) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_LENGTH_MAX, name, length));
            }
        }
    }

    /**
     * 文字長MAXチェック（String）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer length 文字長
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, String value, String name, Integer length)
    {
        maxCheck(validate, value, name, length, null);
    }

    /**
     * 文字長MAXチェック（Int）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer length 最長文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer length, String msgFull)
    {
        maxCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MAXチェック（Int）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer length 最長文字長
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, Integer value, String name, Integer length)
    {
        maxCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MAXチェック（Double）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer length 最長文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, Double value, String name, Integer length, String msgFull)
    {
        maxCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MAXチェック（float）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer length 最長文字長
     * @return void
     * @access public
     */
    public static void maxCheck(ValidateHelper validate, float value, String name, Integer length)
    {
        maxCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MINチェック（String）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer length, String msgFull)
    {
        if (validate.getResult(name) == false) {
            return;
        }
        if (value == null || value.length() == 0) {
            return;
        }

        if (value.length() < length) {
            if (msgFull != null) {
                validate.error(name, msgFull);
            } else {
                validate.error(name, String.format(ERROR_MSG_LENGTH_MIN, name, length));
            }
        }
    }

    /**
     * 文字長MINチェック（String）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param String value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, String value, String name, Integer length)
    {
        minCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MINチェック（Int）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer length, String msgFull)
    {
        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Int）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Int value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Integer value, String name, Integer length)
    {
        minCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MINチェック（Double）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer length, String msgFull)
    {
        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Double）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Double value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Double value, String name, Integer length)
    {
        minCheck(validate, String.valueOf(value), name, length, null);
    }

    /**
     * 文字長MINチェック（Float）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Float value, String name, Integer length, String msgFull)
    {
        minCheck(validate, String.valueOf(value), name, length, msgFull);
    }

    /**
     * 文字長MINチェック（Float）
     * 
     * @param ValidateHelper validate バリデートクラス
     * @param Float value 値
     * @param String name 変数名
     * @param Integer length 最短文字長
     * @param String msgFull エラーメッセージ全文
     * @return void
     * @access public
     */
    public static void minCheck(ValidateHelper validate, Float value, String name, Integer length)
    {
        minCheck(validate, String.valueOf(value), name, length, null);
    }
}
