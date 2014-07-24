package jp.co.e2.givelog.activity;

import java.io.File;
import java.io.IOException;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.MediaUtils;
import jp.co.e2.givelog.config.Config;
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.Button;

/**
 * アクテビティの親クラス
 * 
 * @access public
 */
public class BaseActivity extends Activity
{
    /**
     * onCreate
     * 
     * @param Bundle savedInstanceState
     * @return void
     * @access public
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * ヘッダの戻るボタンをセット
     * 
     * @return void
     * @access public
     */
    public void setHeaderReturnButton()
    {
        Button buttonReturn = (Button) findViewById(R.id.buttonReturn);

        //戻る
        buttonReturn.setVisibility(View.VISIBLE);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 画像保存
     * 
     * @param Integer id プレゼントID/人物ID
     * @param Integer photo 画像の有無フラグ
     * @param Uri uri 画像URI
     * @param String prefix プレフィックス
     * @return void
     * @access public
     */
    public void saveImg(Integer id, Integer photo, Uri uri, String prefix)
    {
        String fileName = prefix + "_" + id + ".jpg";

        //画像保存
        if (uri != null) {
            ImgUtils imgUtils = new ImgUtils(getApplicationContext(), uri);
            imgUtils.saveResizeImg(file_dir, file_name, Config.RESIZE_HEIGHT, Config.RESIZE_WIDTH);
        }
        //画像削除
        else if (photo != 1) {
            File file = new File(file_dir + "/" + file_name);
            if (file.exists() == true) {
                file.delete();
            }
        }
    }

    /**
     * 画像削除
     * 
     * @param Integer id プレゼントID/人物ID
     * @param String prefix プレフィックス
     * @return void
     * @access public
     */
    public void deleteImg(Integer id, String prefix)
    {
        String file_name = prefix + "_" + id + ".jpg";

        File file = new File(file_dir + "/" + file_name);
        if (file.exists() == true) {
            file.delete();
        }
    }

    /**
     * 一時画像保存Uriを取得
     * 
     * @return Uri uri 一時保存ディレクトリのURI
     * @access public
     */
    public Uri getTmpPhotoUri()
    {
        String file_name = System.currentTimeMillis() + ".jpg";
        String path = Config.getTmpFileDirPath() + "/" + file_name;

        ContentValues values = new ContentValues();
        values.put(Images.Media.DISPLAY_NAME, file_name);
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put(Images.Media.DATA, path);
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

        return getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 画像一時保存フォルダを空にする（実際にはフォルダ自体を削除）
     * 
     * @return void
     * @throws IOException
     * @access public
     */
    public void deleteTmpDir() throws IOException
    {
        MediaUtils.deleteDirFile(Config.getImgTmpDirPath(getApplicationContext()));

        MediaUtils.mediaScan(getApplicationContext());	//メディアスキャンを実行
    }
}
