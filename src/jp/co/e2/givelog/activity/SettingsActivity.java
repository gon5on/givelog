package jp.co.e2.givelog.activity;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.dialog.AboutDialog;
import jp.co.e2.givelog.dialog.GuideDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 設定アクテビティ
 * 
 * @access public
 */
public class SettingsActivity extends BaseActivity
{
    /**
     * onCreate
     * 
     * @param Bundle savedInstanceState
     * @return void
     * @access protected
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_btn);

        if (savedInstanceState == null) {
            //ヘッダのボタンセット
            setHeaderReturnButton();

            getSupportFragmentManager().beginTransaction().add(R.id.container, new SettingFragment()).commit();
        }
    }

    /**
     * SettingFragment
     * 
     * @access public
     */
    public static class SettingFragment extends Fragment
    {
        private View mView = null;

        /**
         * onCreateView
         * 
         * @param LayoutInflater inflater
         * @param ViewGroup container
         * @param Bundle savedInstanceState
         * @return View
         * @access public
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // fragment再生成抑止
            setRetainInstance(true);

            mView = inflater.inflate(R.layout.fragment_settings, container, false);

            //グループ設定ボタン
            Button buttonGroup = (Button) mView.findViewById(R.id.buttonGroup);
            buttonGroup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RelationListActivity.class);
                    startActivity(intent);
                }
            });

            //ヘルプ設定ボタン
            Button helpGroup = (Button) mView.findViewById(R.id.buttonHelp);
            helpGroup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new GuideDialog(getActivity()).show();
                }
            });

            //アバウト設定ボタン
            Button aboutGroup = (Button) mView.findViewById(R.id.buttonAbout);
            aboutGroup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new AboutDialog(getActivity()).show();
                }
            });

            return mView;
        }
    }
}