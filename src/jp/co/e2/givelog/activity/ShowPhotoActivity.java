package jp.co.e2.givelog.activity;

import java.io.IOException;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.config.Config;
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
        String fileName = i.getStringExtra("PHOTO");

        //背景を半透明にする
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            //画像セット
            String imgPath = Config.getImgDirPath(getApplicationContext()) + "/" + fileName;
            ImgUtils imgUtils = new ImgUtils(imgPath);

            ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(imgUtils.getOriginImg());

        } catch (IOException e) {
            //TODO 何かしらのエラー処理
            e.printStackTrace();
        }
    }
}