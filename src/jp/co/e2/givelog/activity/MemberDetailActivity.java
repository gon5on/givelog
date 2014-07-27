package jp.co.e2.givelog.activity;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.adapter.PresentListAdapter;
import jp.co.e2.givelog.common.AndroidUtils;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.MediaUtils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.PresentDetailDialog;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.entity.PresentEntity;
import jp.co.e2.givelog.model.BaseSQLiteOpenHelper;
import jp.co.e2.givelog.model.MemberDao;
import jp.co.e2.givelog.model.PresentDao;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * メンバー詳細アクテビティ
 * 
 * @access public
 */
public class MemberDetailActivity extends BaseActivity
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
        setContentView(R.layout.activity_member_detail);

        Integer memberId = (Integer) getIntent().getIntExtra("MEMBER_ID", 0);

        //ヘッダのボタンセット
        setHeaderButton(memberId);

        if (savedInstanceState == null) {
            MemberDetailFragment fragment = new MemberDetailFragment();
            Bundle args = new Bundle();
            args.putInt("MEMBER_ID", memberId);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    /**
     * ヘッダのボタンセット
     * 
     * @return void
     * @access private
     */
    private void setHeaderButton(final Integer memberId)
    {
        //戻る
        Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //削除
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO 削除ダイアログ
            }
        });

        //編集
        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberDetailActivity.this, MemberAddActivity.class);
                intent.putExtra("MEMBER_ID", memberId);
                startActivity(intent);
            }
        });

        //プレゼント追加
        Button buttonPresentAdd = (Button) findViewById(R.id.buttonPresentAdd);
        buttonPresentAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MemberDetailActivity.this, PresentAddActivity.class);
                i.putExtra("MEMBER_ID", memberId);
                i.putExtra("TYPE", type);
                startActivity(i);
            }
        });
    }

    /**
     * MemberDetailFragment
     * 
     * @access public
     */
    public static class MemberDetailFragment extends Fragment
    {
        private View mView = null;

        private Integer mMemberId;                              //メンバーID
        private Integer mType = Config.GIVE_TYPE;               //あげる・もらうフラグ
        private Integer mPresentId;                             //選択したプレゼントID
        private Integer mPosition;                              //選択したポジション
        private PresentListAdapter adapter;                     //プレゼント一覧アダプター
        private MemberEntity mMember;                           //メンバーデータ
        private ArrayList<PresentEntity> mPresentList;          //プレゼントリスト

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

            mView = inflater.inflate(R.layout.fragment_member_detail, container, false);

            mMemberId = (Integer) getArguments().getInt("MEMBER_ID", 0);

            // fragment再生成抑止
            setRetainInstance(true);

            //メンバーデータ取得
            getMemberData();

            //プレゼントリスト取得
            getPresentList();

            return mView;
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

            //背景画像消える対策
            ListView listViewPresent = (ListView) mView.findViewById(R.id.listViewPresent);
            listViewPresent.setScrollingCacheEnabled(false);

            //人物情報をビューにセット
            setMemberData();

            //プレゼント情報取得、ビューにセット
            setPresentData();

            //あげるボタンセット
            Button buttonGive = (Button) mView.findViewById(R.id.buttonGive);
            buttonGive.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mType = Config.GIVE_TYPE;
                    setPresentData();
                    setTab();
                }
            });

            //もらうボタンセット
            Button buttonGave = (Button) mView.findViewById(R.id.buttonGave);
            buttonGave.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mType = Config.GAVE_TYPE;
                    setPresentData();
                    setTab();
                }
            });
        }

        /**
         * 人物情報をビューにセット
         * 
         * @return void
         * @access private
         */
        private void setMemberData()
        {
            //写真セット
            if (mMember.getPhoto() != null) {
                Integer height = getResources().getDimensionPixelSize(R.dimen.tmp_photo_height);
                Integer width = getResources().getDimensionPixelSize(R.dimen.tmp_photo_width);

                String imgPath = Config.getImgDirPath(getActivity()) + "/" + mMember.getPhoto();
                ImgUtils imgUtils = new ImgUtils(imgPath);

                ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
                imageViewPhoto.setEnabled(true);

                //タップ時の画像拡大
                imageViewPhoto.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), ShowPhotoActivity.class);
                        i.putExtra("PHOTO", mMember.getPhoto());
                        startActivity(i);
                    }
                });
            } else {
                ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setImageResource(R.drawable.no_image);
                imageViewPhoto.setEnabled(false);
            }

            //名前
            TextView textViewName = (TextView) mView.findViewById(R.id.textViewName);
            textViewName.setText(mMember.getName());

            //ふりがな
            TextView textViewKana = (TextView) mView.findViewById(R.id.textViewKana);

            if (mMember.getKana().length() != 0) {
                textViewKana.setText(mMember.getKana());
                textViewKana.setVisibility(View.VISIBLE);
            } else {
                textViewKana.setVisibility(View.GONE);
            }

            //関係性
            TextView textViewRelation = (TextView) mView.findViewById(R.id.textViewRelation);
            textViewRelation.setText(mMember.getRelationName());

            GradientDrawable shape = (GradientDrawable) textViewRelation.getBackground();
            textViewRelation.setBackgroundDrawable(shape);
            shape.setColor(mMember.getLabel());

            //誕生日
            TextView textViewBirth = (TextView) mView.findViewById(R.id.textViewBirth);

            if (mMember.getBirth() != null) {
                textViewBirth.setText(mMember.getBirthAge());
                textViewBirth.setVisibility(View.VISIBLE);
            } else {
                textViewBirth.setVisibility(View.GONE);
            }

            //メモ
            Button buttonMemo = (Button) mView.findViewById(R.id.buttonMemo);

            if (mMember.getMemo().length() != 0) {
                buttonMemo.setVisibility(View.VISIBLE);
                buttonMemo.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //TODO メモダイアログ
                    }
                });
            } else {
                buttonMemo.setVisibility(View.GONE);
            }
        }

        /**
         * プレゼント情報取得、ビューにセット
         * 
         * @return void
         * @access private
         */
        private void setPresentData()
        {
            //プレゼント情報をビューにセット
            ListView listViewPresent = (ListView) mView.findViewById(R.id.listViewPresent);

            adapter = new PresentListAdapter(getActivity(), mPresentList, mType);
            listViewPresent.setAdapter(adapter);

            //イベントリスナーセット
            listViewPresent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int l_position, long id) {
                    mPresentId = (int) adapter.getItemId(l_position);
                    showDetailDialog();
                }
            });
        }

        /**
         * タブの見た目を切り替える
         * 
         * @return void
         * @access private
         */
        private void setTab()
        {
            Button buttonOn;
            Button buttonOff;

            if (mType == Config.GAVE_TYPE) {
                buttonOn = (Button) mView.findViewById(R.id.buttonGive);
                buttonOff = (Button) mView.findViewById(R.id.buttonGave);

            } else {
                buttonOn = (Button) mView.findViewById(R.id.buttonGave);
                buttonOff = (Button) mView.findViewById(R.id.buttonGive);
            }

            buttonOn.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_on));
            buttonOff.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_off));
        }

        /**
         * 編集処理
         * 
         * @return void
         * @access private
         */
        private void editPresent()
        {
            Intent intent = new Intent(getActivity(), PresentAddActivity.class);
            intent.putExtra("ID", mPresentId);
            startActivity(intent);
        }

        /**
         * 詳細ダイアログを開く
         * 
         * @return void
         * @access private
         */
        private void showDetailDialog()
        {
            if (detail_dialog == null) {
                detail_dialog = new PresentDetailDialog(this, file_dir);

                //編集ボタン
                Button detailButtonEdit = (Button) detail_dialog.findViewById(R.id.buttonEdit);
                detailButtonEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editPresent();
                    }
                });

                //削除ボタン
                Button detailButtonDelete = (Button) detail_dialog.findViewById(R.id.buttonDelete);
                detailButtonDelete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showPresentDeleteDialog();
                    }
                });

                //画像拡大表示
                ImageView imageViewPhoto = (ImageView) detail_dialog.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), ShowPhotoActivity.class);
                        i.putExtra("PHOTO", presents.get(position).getPhoto());
                        startActivity(i);
                    }
                });
            }

            detail_dialog.setContent(presents.get(position));
            detail_dialog.show();
        }

        /**
         * 人物削除処理
         * 
         * @return void
         * @access private
         */
        private void deleteMember()
        {
            SQLiteDatabase db = null;

            try {
                //DB削除
                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity().getApplicationContext());
                db = helper.getWritableDatabase();

                MemberDao memberDao = new MemberDao();
                memberDao.delete(db, mMemberId, mMember.getName());

                //画像削除
                String fileDir = Config.getImgDirPath(getActivity());
                String fileName = Config.getImgFileName(Config.MEMBER_IMG_FLG, mMemberId);
                MediaUtils.deleteDirFile(fileDir + "/" + fileName);

                AndroidUtils.showToastL(getActivity(), "削除しました。");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }

        /**
         * プレゼント削除
         * 
         * @return void
         * @access private
         */
        private void deletePresent()
        {
            SQLiteDatabase db = null;

            try {
                //DB削除
                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity().getApplicationContext());
                db = helper.getWritableDatabase();

                PresentDao presentDao = new PresentDao();
                presentDao.delete(db, mPresentId);

                //画像削除
                String fileDir = Config.getImgDirPath(getActivity());
                String fileName = Config.getImgFileName(Config.PRESENT_IMG_FLG, mPresentId);
                MediaUtils.deleteDirFile(fileDir + "/" + fileName);

                //表示更新
                adapter.remove(mPosition);

                AndroidUtils.showToastL(getActivity(), "削除しました。");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }

        /**
         * メンバーデータを取得
         * 
         * @return void
         * @access private
         */
        private void getMemberData()
        {
            SQLiteDatabase db = null;

            try {
                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity().getApplicationContext());
                db = helper.getWritableDatabase();

                MemberDao memberDao = new MemberDao();
                mMember = memberDao.selectMemberDetail(db, mMemberId);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }

        /**
         * プレゼントリストを取得
         * 
         * @return void
         * @access private
         */
        private void getPresentList()
        {
            SQLiteDatabase db = null;

            try {
                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity().getApplicationContext());
                db = helper.getWritableDatabase();

                PresentDao presentDao = new PresentDao();
                mPresentList = presentDao.selectPresentList(db, mMemberId, mType);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }
    }
}