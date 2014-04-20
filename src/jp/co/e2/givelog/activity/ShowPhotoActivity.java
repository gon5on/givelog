package jp.co.e2.givelog.activity;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

/**
 * 画像表示アクテビティ
 * 
 * @access public
 */
public class ShowPhotoActivity extends BaseActivity
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_photo);

		//引数のID取得
		Intent i = getIntent();
		String file_name = i.getStringExtra("PHOTO");

		//背景を半透明にする
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		//画像セット
		String img_path = file_dir + "/" + file_name;
		ImgUtils imgUtils = new ImgUtils(getApplicationContext(), img_path);

		ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
		imageViewPhoto.setImageBitmap(imgUtils.getOriginImg());
	}
}