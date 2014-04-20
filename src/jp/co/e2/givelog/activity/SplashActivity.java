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

		Handler hdl = new Handler();
		hdl.postDelayed(new splashHandler(), 1500);
	}

	/**
	 * ハンドラ
	 * 
	 * @access private
	 */
	private class splashHandler implements Runnable
	{
		public void run() {
			Intent i = new Intent(getApplication(), MemberListActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
			SplashActivity.this.finish();
		}
	}
}
