package jp.co.e2.givelog.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * 便利なものをまとめたクラス
 * 
 * @access public
 */
public class Utils
{
	/**
	 * 今日の日付・時刻取得
	 * 
	 * @param String fotmat(例：yyyy/MM/dd　HH:mm:ss)
	 * @return String 今日の日付・時間
	 * @access public
	 */
	public static String getNow(String fotmat)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(fotmat);

		return sdf.format(date);
	}

	/**
	 * 年、月、日をフォーマットして返す
	 * 
	 * @param Integer year 年
	 * @param Integer month 月
	 * @param Integer day 日
	 * @param String delimiter デリミタ
	 * @return String フォーマットした日付
	 * @access public
	 */
	public static String dateFormat(int year, int month, int day, String delimiter)
	{
		String year_str = String.valueOf(year);
		String month_str = String.format("%02d", month + 1);
		String day_str = String.format("%02d", day);

		return year_str + delimiter + month_str + delimiter + day_str;
	}

	/**
	 * 日付を年月日にわけて返す
	 * 
	 * @param String date（例：2000/01/01, 2000.01.01）
	 * @param String delimiter デリミタ
	 * @return String[] 年月日配列
	 * @access public
	 */
	public static String[] separateDate(String date, String delimiter)
	{
		if (date == null) {
			return null;
		}

		return date.split(delimiter);
	}

	/**
	 * implode
	 * 
	 * @param ArrayList<String> list 配列
	 * @param String delimiter デリミタ
	 * @return String デリミタで結合した文字列
	 * @access public
	 */
	public static String implode(ArrayList<String> list, String delimiter)
	{
		String str = "";

		for (int i = 0; i < list.size(); i++) {
			if (str.equals("") == false) {
				str = str + delimiter;
			}
			str = str + list.get(i);
		}

		return str;
	}

	/**
	 * 年齢を取得する
	 * 
	 * @param String birth 日付（例：2000-01-01）
	 * @return Integer age 年齢
	 * @access public
	 */
	public static Integer getAge(String birth)
	{
		Integer today = Integer.parseInt(getNow("yyyyMMdd"));
		Integer culc_birth = Integer.parseInt(birth.replaceAll("-", ""));

		int age = (int) (today - culc_birth) / 10000;

		return age;
	}

	/**
	 * 一時スリープ
	 * 
	 * @param Integer sec スリープする秒数
	 * @return void
	 * @access public
	 */
	public static void sleep(Integer sec)
	{
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * URLエンコード
	 * 
	 * @param String str エンコード前のURL
	 * @return String エンコードしたURL
	 * @access public
	 */
	public static String url_encode(String str)
	{
		String en_str = "";

		try {
			en_str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return en_str;
	}

	/**
	 * URLデコード
	 * 
	 * @param String str デコード前のURL
	 * @return String デコードしたURL
	 * @access public
	 */
	public static String url_decode(String str)
	{
		String de_str = "";

		try {
			de_str = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return de_str;
	}

	/**
	 * テキスト読み込み
	 * 
	 * @param Context context コンテキスト
	 * @param Integer Integer リソースID
	 * @return String 読み込んだテキスト
	 * @access public
	 */
	public static String readTextFile(Context context, Integer res_id)
	{
		InputStream is = null;
		BufferedReader br = null;

		StringBuilder sb = new StringBuilder();
		try {
			try {
				is = context.getResources().openRawResource(res_id);
				br = new BufferedReader(new InputStreamReader(is));

				String str;
				while ((str = br.readLine()) != null) {
					sb.append(str + "\n");
				}
			} finally {
				if (br != null) {
					br.close();
				}
			}
		} catch (IOException e) {
		}

		return sb.toString();
	}

	/**
	 * URIからファイルパスを取得する
	 * 
	 * @param Context context コンテキスト
	 * @param Uri uri URI
	 * @return String path ファイルパス
	 * @access public
	 */
	public static String getPathFromUri(Context context, Uri uri)
	{
		String path = null;

		if (uri != null && "content".equals(uri.getScheme())) {
			Cursor cursor = context.getContentResolver().query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
			cursor.moveToFirst();
			path = cursor.getString(0);
			cursor.close();
		} else {
			path = uri.getPath();
		}

		return path;
	}

	/**
	 * ファイル/ディレクトリを削除する（中身があってもOK）
	 * 
	 * @param File file ファイル/ディレクトリ
	 * @return void
	 * @access public
	 */
	public static void deleteDir(File file)
	{
		if (file.exists() == false) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		}

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i]);
			}
			file.delete();
		}
	}

	/**
	 * 外部ストレージが使用できるかどうか
	 * 
	 * @return boolen 外部ストレージが使える/使えない
	 * @access public
	 */
	public static boolean IsExternalStorageAvailableAndWriteable()
	{
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			externalStorageAvailable = externalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		} else {
			externalStorageAvailable = externalStorageWriteable = false;
		}

		return externalStorageAvailable && externalStorageWriteable;
	}

	/**
	 * メディアスキャンを実行
	 * 
	 * @param Context context コンテキスト
	 * @return void
	 * @access public
	 */
	public static void mediaScan(Context context)
	{
		String _url = "file://" + Environment.getExternalStorageDirectory();
		Uri _uri = Uri.parse(_url);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, _uri));
	}

	/**
	 * トースト表示
	 * 
	 * @param Context context コンテキスト
	 * @return void
	 * @access public
	 */
	public static void showToast(Context context, String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * プリファレンスから数字を取得
	 * 
	 * @param Context context コンテキスト
	 * @param String name 保存名
	 * @param Integer defo デフォルト値
	 * @return Integer 値
	 * @access public
	 */
	public static Integer getIntFromPref(Context context, String name, Integer defo)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getInt(name, defo);
	}

	/**
	 * プリファレンスに数字を保存
	 * 
	 * @param Context context コンテキスト
	 * @param String name 保存名
	 * @param Integer value 値
	 * @return void
	 * @access public
	 */
	public static void saveIntToPref(Context context, String name, Integer value)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putInt(name, value).commit();
	}

	/**
	 * プリファレンスから文字列を取得
	 * 
	 * @param Context context コンテキスト
	 * @param String name 保存名
	 * @param String defo デフォルト値
	 * @return String 文字列
	 * @access public
	 */
	public static String getStringFromPref(Context context, String name, String defo)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getString(name, defo);
	}

	/**
	 * プリファレンスに文字列を保存
	 * 
	 * @param Context context コンテキスト
	 * @param String name 保存名
	 * @param String value 値
	 * @return void
	 * @access public
	 */
	public static void saveStringToPref(Context context, String name, String value)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putString(name, value).commit();
	}
}
