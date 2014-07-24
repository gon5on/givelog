package jp.co.e2.givelog.activity;

import jp.co.e2.givelog.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * スプラッシュアクテビティ
 * 
 * @access public
 */
public class SplashActivity extends BaseActivity
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
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(), 1000);
    }

    /**
     * スプラッシュから遷移する
     * 
     * @access private
     */
    private class splashHandler implements Runnable
    {
        public void run() {
            //バックキーか何かで終了されていなければ遷移
            if (isFinishing() == false) {
                Intent i = new Intent(SplashActivity.this, MemberListActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                finish();
            }
        }
    }
}
