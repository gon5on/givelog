package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.MemberListAdapter;
import jp.co.e2.givelog.common.PrefarenceUtils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.GuideDialog;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.model.BaseSQLiteOpenHelper;
import jp.co.e2.givelog.model.MemberDao;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * メンバー一覧アクテビティ
 * 
 * @access public
 */
public class MemberListActivity extends BaseActivity
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
            setHeaderButton();            //ヘッダのボタンセット

            getSupportFragmentManager().beginTransaction().add(R.id.container, new MemberListFragment()).commit();
        }
    }

    /**
     * ヘッダのボタンセット
     * 
     * @return void
     * @access private
     */
    private void setHeaderButton()
    {
        Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
        Button buttonMemberAdd = (Button) findViewById(R.id.buttonMemberAdd);
        Button buttonPresentAdd = (Button) findViewById(R.id.buttonPresentAdd);

        //設定
        buttonSettings.setVisibility(View.VISIBLE);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MemberListActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        //人物追加
        buttonMemberAdd.setVisibility(View.VISIBLE);
        buttonMemberAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MemberListActivity.this, MemberAddActivity.class);
                startActivity(i);
            }
        });

        //プレゼント追加
        buttonPresentAdd.setVisibility(View.VISIBLE);
        buttonPresentAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MemberListActivity.this, PresentAddActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * MemberListFragment
     * 
     * @access public
     */
    public static class MemberListFragment extends Fragment
    {
        private View mView = null;
        private ArrayList<MemberEntity> mMemberList;        //メンバー一覧

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

            mView = inflater.inflate(R.layout.fragment_listview, container, false);

            //初回起動判定
            if (PrefarenceUtils.get(getActivity().getApplicationContext(), Config.TWICE_FLAG, 0) == 0) {
                new GuideDialog(this).show();
                PrefarenceUtils.save(getActivity().getApplicationContext(), Config.TWICE_FLAG, 1);
            }

            //メンバー一覧取得
            getMemberList();
        }

        /**
         * onResume
         * 
         * @return void
         * @access public
         */
        @Override
        public void onResume()
        {
            super.onResume();

            //アダプターをセット
            ListView listViewMember = (ListView) mView.findViewById(R.id.listView);
            MemberListAdapter adapter = new MemberListAdapter(getActivity(), mMemberList);
            listViewMember.setAdapter(adapter);

            //背景画像消える対策
            listViewMember.setScrollingCacheEnabled(false);
        }

        /**
         * メンバー一覧を取得
         * 
         * @return void
         * @access private
         */
        private void getMemberList()
        {
            SQLiteDatabase db = null;

            try {
                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity().getApplicationContext());
                db = helper.getWritableDatabase();

                MemberDao memberDao = new MemberDao();
                mMemberList = memberDao.selectMemberList(db);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }
    }
}