package jp.co.e2.givelog.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * 画像処理に関してのものをまとめたクラス
 * 
 * @access public
 */
public class ImgUtils
{
	private static Context context;					//コンテキスト
	private static Uri uri;							//画像URI
	private static String path;						//画像ファイルパス

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @param Uri uri 画像のURI
	 */
	public ImgUtils(Context context, Uri uri)
	{
		this.context = context;
		this.uri = uri;
		this.path = Utils.getPathFromUri(context, uri);		//URIをファイルパスに変換
	}

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @param String path 画像のパス
	 */
	public ImgUtils(Context context, String path)
	{
		this.context = context;
		this.path = path;
	}

	/**
	 * オリジナルのビットマップ画像を返す
	 * 
	 * @return Bitmap
	 * @access public
	 */
	public Bitmap getOriginImg()
	{
		Bitmap origin_bitmap = null;

		try {
			FileInputStream in = new FileInputStream(path);
			origin_bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return origin_bitmap;
	}

	/**
	 * リサイズしたビットマップ画像を返す
	 * 
	 * @param Integer height 高さピクセル
	 * @param Integer weight 幅ピクセル
	 * @return Bitmap
	 */
	public Bitmap getResizeImg(Integer height, Integer weight)
	{
		//画像ファイル自体は読み込まずに、高さなどのプロパティのみを取得する
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		//縮小比率を取得する
		options.inSampleSize = calceInSampleSize(options, height, weight);

		//リサイズしたビットマップを作成
		options.inJustDecodeBounds = false;
		Bitmap resize_bitmap = BitmapFactory.decodeFile(path, options);

		return resize_bitmap;
	}

	/**
	 * オリジナルとリサイズ後の画像高さ・幅から縮小比率を取得する
	 * 
	 * @param BitmapFactory.Options options オリジナル画像の縦横幅セット
	 * @param Integer re_height リサイズ後の高さ
	 * @param Integer re_width リサイズ後の幅
	 * @return Integer inSampleSize 縮小比率
	 * @access public
	 */
	public static int calceInSampleSize(BitmapFactory.Options options, Integer re_height, Integer re_width)
	{
		Integer ori_height = options.outHeight;
		Integer ori_width = options.outWidth;
		Integer inSampleSize = 1;

		if (ori_height > re_height || ori_width > re_width) {
			if (ori_width > ori_height) {
				inSampleSize = Math.round((float) ori_height / (float) re_height);
			} else {
				inSampleSize = Math.round((float) ori_width / (float) re_width);
			}
		}

		return inSampleSize;
	}

	/**
	 * リサイズして画像を保存する
	 * 
	 * @param String dir_path 保存先パス
	 * @param String name 保存画像名
	 * @param Integer height 高さピクセル
	 * @param Integer weight 幅ピクセル
	 * @return boolean 成功/失敗
	 */
	public Boolean saveResizeImg(String dir_path, String name, Integer height, Integer weight)
	{
		//画像読み込み
		Bitmap img = getResizeImg(height, weight);

		//画像保存
		return saveImg(dir_path, name, img);
	}

	/**
	 * オリジナル画像を保存する
	 * 
	 * @param String dir_path 保存先パス
	 * @param String name 保存画像名
	 * @return boolean 成功/失敗
	 */
	public Boolean saveOrgImg(String dir_path, String name)
	{
		//画像読み込み
		Bitmap img = getOriginImg();

		//画像保存
		return saveImg(dir_path, name, img);
	}

	/**
	 * 実際の保存処理
	 * 
	 * @param String dir_path 保存先パス
	 * @param String name 保存画像名
	 * @param Bitmap img ビットマップ画像
	 * @return boolean 成功/失敗
	 */
	private Boolean saveImg(String dir_path, String name, Bitmap img)
	{
		try {
			//保存先のフォルダ存在確認
			File dir = new File(dir_path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			//jpgで保存
			String file_path = dir.getAbsolutePath() + "/" + name;
			FileOutputStream out = new FileOutputStream(file_path, false);
			img.compress(CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
