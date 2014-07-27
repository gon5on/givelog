package jp.co.e2.givelog.config;

import java.io.File;
import java.io.IOException;

import jp.co.e2.givelog.common.MediaUtils;
import android.content.Context;

public class Config
{
    //データベース名
    public static final String DATABASE_NAME = "givelog.db";

    //ギャラリーインテントID
    public static final Integer SELECT_GALLERY = 1;

    //カメラインテントID
    public static final Integer SELECT_CAMERA = 2;

    //画像リサイズ
    public static final Integer RESIZE_WIDTH = 800;
    public static final Integer RESIZE_HEIGHT = 800;

    //あげた・もらったタイプ
    public static final Integer GIVE_TYPE = 1;                          //あげた
    public static final Integer GAVE_TYPE = 2;                          //もらった

    //画像tmpフォルダ名
    public static final String TMP_DIR_NAME = "tmp";

    //画像ファイルフラグ
    public static final Integer PRESENT_IMG_FLG = 1;                    //プレゼント画像
    public static final Integer MEMBER_IMG_FLG = 2;                     //メンバー画像

    //画像ファイルプレフィックス
    public static final String PRESENT_IMG_PREFIX = "present";          //プレゼント画像
    public static final String MEMBER_IMG_PREFIX = "person";            //メンバー画像

    //プリファレンス保存名
    public static final String TWICE_FLAG = "twice_flag";               //起動2回目以降フラグ

    /**
     * 画像保存パスを返す
     * 
     * @param Context context
     * @return String
     */
    public static String getImgDirPath(Context context)
    {
        String path = null;

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWriteable() == true) {
            path = context.getExternalFilesDir(null).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString();
        }

        return path;
    }

    /**
     * 画像保存tmpパスを返す
     * 
     * @param Context context
     * @return String
     * @throws IOException
     */
    public static String getImgTmpDirPath(Context context) throws IOException
    {
        String path = null;

        //外部ストレージが使用可能
        if (MediaUtils.IsExternalStorageAvailableAndWriteable() == true) {
            path = context.getExternalFilesDir(TMP_DIR_NAME).toString();
        }
        //外部ストレージは使用不可
        else {
            path = context.getFilesDir().toString() + "/" + TMP_DIR_NAME;

            //フォルダが存在しなければ作成して、メディアとして認識されないようにnomediaファイルを生成しておく
            if (new File(path).exists() == false) {
                (new File(path)).mkdir();

                File noMedia = new File(path + "/.nomedia");
                noMedia.createNewFile();
            }
        }

        return path;
    }

    /**
     * 画像ファイル名を生成
     * 
     * @param Integer flg
     * @param Integer id
     * @return String
     */
    public static String getImgFileName(Integer flg, Integer id)
    {
        String prefix = (flg == PRESENT_IMG_FLG) ? PRESENT_IMG_PREFIX : MEMBER_IMG_PREFIX;
        String path = prefix + "_" + id + ".jpg";

        return path;
    }
}
