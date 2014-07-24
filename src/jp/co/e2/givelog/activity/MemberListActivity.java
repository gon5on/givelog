package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.MemberListAdapter;
import jp.co.e2.givelog.common.PrefarenceUtils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.GuideDialog;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.model.MemberDao;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
     * @access public
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        //ヘッダのボタンセット
        setHeaderButton();

        //初回起動判定
        if (PrefarenceUtils.get(getApplicationContext(), Config.TWICE_FLAG, 0) == 0) {
            new GuideDialog(this).show();
            PrefarenceUtils.save(getApplicationContext(), Config.TWICE_FLAG, 1);
        }
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

        //人物一覧
        SQLiteDatabase db = helper.getWritableDatabase();
        MemberDao memberDao = new MemberDao();
        ArrayList<MemberEntity> memberList = memberDao.selectMemberList(db);
        db.close();

        //アダプターをセット
        ListView listViewMember = (ListView) findViewById(R.id.listViewMember);
        MemberListAdapter adapter = new MemberListAdapter(this, R.layout.part_member_list, memberList);
        listViewMember.setAdapter(adapter);

        //背景画像消える対策
        listViewMember.setScrollingCacheEnabled(false);

        //イベント
        listViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                MemberEntity item = (MemberEntity) listView.getAdapter().getItem(position);

                Intent i = new Intent(getApplicationContext(), MemberDetailActivity.class);
                i.putExtra("ID", item.getId());
                startActivity(i);
            }
        });
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
}