package jp.co.e2.givelog.activity;

import java.io.File;
import java.io.IOException;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.Config;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.model.DBOpenHelper;
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
	public DBOpenHelper helper;
	public String file_dir = null;
	public String tmp_file_dir = null;

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

		if (helper == null) {
			helper = new DBOpenHelper(getApplicationContext());

			setFileDirPath();
		}
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
		String file_name = prefix + "_" + id + ".jpg";

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
		String path = getTmpFileDirPath() + "/" + file_name;

		ContentValues values = new ContentValues();
		values.put(Images.Media.DISPLAY_NAME, file_name);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.DATA, path);
		values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

		return getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
	}

	/**
	 * 一時画像保存ディレクトリを返す
	 * 
	 * @return String 一時保存ディレクトリのパス
	 * @access public
	 */
	public String getTmpFileDirPath()
	{
		File dir_path = new File(tmp_file_dir);

		if (!dir_path.exists()) {
			dir_path.mkdirs();
		}

		return dir_path.getPath();
	}

	/**
	 * 画像一時保存フォルダを空にする（実際にはフォルダ自体を削除）
	 * 
	 * @return void
	 * @access public
	 */
	public void deleteTmpDir()
	{
		Utils.deleteDir(new File(tmp_file_dir));

		Utils.mediaScan(getApplicationContext());	//メディアスキャンを実行
	}

	/**
	 * 画像ファイル保存フォルダパスをセットする
	 * 
	 * @return void
	 * @access public
	 */
	public void setFileDirPath()
	{
		//外部ストレージが使用可能
		if (Utils.IsExternalStorageAvailableAndWriteable() == true) {
			file_dir = getExternalFilesDir(null).toString();
			tmp_file_dir = getExternalFilesDir("temp").toString();
		}
		//外部ストレージは使用不可
		else {
			file_dir = getFilesDir().toString();
			tmp_file_dir = getFilesDir().toString() + "/tmp";

			File tmp_file_dir_file = new File(tmp_file_dir);
			tmp_file_dir_file.mkdir();
		}

		//tempフォルダはメディアとして認識されないようにする
		try {
			File no_media = new File(tmp_file_dir + "/.nomedia");
			no_media.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
