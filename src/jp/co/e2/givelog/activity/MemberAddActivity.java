package jp.co.e2.givelog.activity;

import java.util.ArrayList;
import java.util.Calendar;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.AndroidUtils;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.ErrorDialog;
import jp.co.e2.givelog.dialog.PhotoSelectDialog;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.entity.RelationEntity;
import jp.co.e2.givelog.model.MemberDao;
import jp.co.e2.givelog.model.RelationDao;
import jp.co.e2.givelog.validate.Validate;
import jp.co.e2.givelog.validate.ValidateRequire;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * メンバー追加アクテビティ
 * 
 * @access public
 */
public class MemberAddActivity extends BaseActivity
{
    private static ArrayList<RelationEntity> relations;			//関係性クラス配列
    private static String[] relations2;						//関係性文字列配列

    private static DatePickerDialog date_picker_dialog;		//datePickerダイアログ
    private static AlertDialog.Builder relation_dialog;		//関係性ダイアログ
    private static ErrorDialog error_dialog;				//入力エラーダイアログ
    private static PhotoSelectDialog photo_select_dialog;	//写真選択ダイアログ

    private static Integer id;								//引数のID
    private static Integer selected_year;					//選択済み年保持変数
    private static Integer selected_month;					//選択済み月保持変数
    private static Integer selected_day;					//選択済み日保持変数
    private static String save_birthday;					//保存用誕生日変数
    private static Integer relation_position;				//選択済み関係性ポジション変数
    private static Uri uri;									//選択された画像URL
    private static Uri camera_tmp_uri;						//カメラ起動時パス格納用変数
    private static Integer photo = 0;						//写真フラグ

    private static Integer height;							//画像表示ピクセル縦
    private static Integer width;							//画像表示ピクセル横

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
        setContentView(R.layout.activity_member_add);

        //引数のID取得
        Intent i = getIntent();
        id = i.getIntExtra("ID", 0);

        //ソフトウェアキーボードをデフォで出さない
        this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //ヘッダのボタン
        setHeaderReturnButton();

        //各ボタンのイベントをセットする
        setButton();

        //グループ一覧取得
        SQLiteDatabase db = helper.getWritableDatabase();
        RelationDao relationDao = new RelationDao();
        relations = relationDao.selectRelationList(db);
        relations2 = relationDao.getRelationSpinnerList(relations);
        db.close();

        //画像表示時のピクセルを計算しておく
        Resources res = getResources();
        height = res.getDimensionPixelSize(R.dimen.tmp_photo_height);
        width = res.getDimensionPixelSize(R.dimen.tmp_photo_width);

        //新規登録の場合
        if (id == 0) {
            add();
        }
        //編集の場合
        else {
            edit();
        }
    }

    /**
     * ギャラリー・カメラから取得した画像を表示
     * 
     * @param int requestCode
     * @param int resultCode
     * @param Intent data
     * @return void
     * @access protected
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //ギャラリー
        if (requestCode == Config.SELECT_GALLERY && resultCode == RESULT_OK) {
            photo = 1;
            uri = data.getData();

            //選択された画像をリサイズ
            ImgUtils ImgUtils = new ImgUtils(getApplicationContext(), data.getData());
            Bitmap re_img = ImgUtils.getResizeImg(height, width);

            //画像を表示
            ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(re_img);
        }
        //カメラ
        if (requestCode == Config.SELECT_CAMERA && resultCode == RESULT_OK) {
            photo = 1;
            uri = camera_tmp_uri;

            //選択された画像をリサイズ
            ImgUtils ImgUtils = new ImgUtils(getApplicationContext(), uri);
            Bitmap re_img = ImgUtils.getResizeImg(height, width);

            //画像を表示
            ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(re_img);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 新規登録
     * 
     * @return void
     * @access private
     */
    private void add()
    {
        //関係性の選択済みポジションに未分類を入れておく
        relation_position = (relations.size() - 1);

        //誕生日の削除ボタンを非表示にしておく
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setVisibility(View.GONE);
    }

    /**
     * 保存
     * 
     * @return void
     * @access private
     */
    private void edit()
    {
        //人物データ取得
        SQLiteDatabase db = helper.getWritableDatabase();
        MemberDao memberDao = new MemberDao();
        MemberEntity member = memberDao.selectMemberDetail(db, id);
        db.close();

        //写真セット
        if (member.getPhoto() != null) {
            photo = 1;

            String img_path = file_dir + "/" + member.getPhoto();
            ImgUtils imgUtils = new ImgUtils(getApplicationContext(), img_path);

            ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
        }

        //名前セット
        TextView editTextName = (TextView) findViewById(R.id.editTextName);
        editTextName.setText(member.getName());

        //ふりがなセット
        TextView editTextKana = (TextView) findViewById(R.id.editTextKana);
        editTextKana.setText(member.getKana());

        //関係性セット
        Button buttonRelation = (Button) findViewById(R.id.buttonRelation);
        buttonRelation.setText(member.getRelationName());

        //メモセット
        TextView editTextMemo = (TextView) findViewById(R.id.editTextMemo);
        editTextMemo.setText(member.getMemo());

        //誕生日セット
        Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
        if (member.getBirth() != null) {
            buttonDatePick.setText(member.getBirth());
        } else {
            buttonDatePick.setText("未選択");
            Button buttonClear = (Button) findViewById(R.id.buttonClear);
            buttonClear.setVisibility(View.GONE);
        }

        //誕生日の選択済み値格納変数に保存済みの日付を入れておく
        if (member.getBirth() != null) {
            save_birthday = member.getBirth();

            String[] date = member.getBirth().split("/");
            selected_year = Integer.valueOf(date[0]);
            selected_month = Integer.valueOf(date[1]) - 1;
            selected_day = Integer.valueOf(date[2]);
        }

        //関係性の選択済みポジションに値を入れておく
        relation_position = relationDao.getRelationSpinnerPosition(relations, member.getRelationId());
    }

    /**
     * ボタンにイベントをセットする
     * 
     * @return void
     * @access private
     */
    private void setButton()
    {
        //写真
        ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showPhotoSelectDialog();
            }
        });

        //日付選択ボタン
        Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
        buttonDatePick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //日付クリアボタン
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                clearBirthday();
            }
        });

        //関係性選択ボタン
        Button buttonRelation = (Button) findViewById(R.id.buttonRelation);
        buttonRelation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showRelationPicker(relations2);
            }
        });

        //保存ボタン
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                save(relations, id);
            }
        });
    }

    /**
     * 日付選択ボタンが押されたイベント
     * 
     * @return void
     * @access private
     */
    private void showDatePicker()
    {
        if (selected_year == null) {
            Calendar calendar = Calendar.getInstance();
            selected_year = calendar.get(Calendar.YEAR);
            selected_month = calendar.get(Calendar.MONTH);
            selected_day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        if (date_picker_dialog == null) {
            date_picker_dialog = new DatePickerDialog(this, new OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
                    buttonDatePick.setText(Utils.dateFormat(year, month, day, "/"));
                    selected_year = year;
                    selected_month = month;
                    selected_day = day;
                    save_birthday = Utils.dateFormat(year, month, day, "/");

                    Button buttonClear = (Button) findViewById(R.id.buttonClear);
                    buttonClear.setVisibility(View.VISIBLE);
                }
            }, selected_year, selected_month, selected_day);
        }

        date_picker_dialog.show();
    }

    /**
     * 日付クリアボタンが押されたイベント
     * 
     * @return void
     * @access private
     */
    private void clearBirthday()
    {
        Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
        buttonDatePick.setText("未選択");

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setVisibility(View.GONE);

        selected_year = null;
        selected_month = null;
        selected_day = null;
        save_birthday = null;
    }

    /**
     * グループ選択ボタンを表示
     * 
     * @param String[] items グループ一覧配列
     * @return void
     * @access private
     */
    private void showRelationPicker(final String[] items)
    {
        if (relation_dialog == null) {
            relation_dialog = new AlertDialog.Builder(this);
        }

        relation_dialog.setSingleChoiceItems(items, relation_position, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button buttonRelation = (Button) findViewById(R.id.buttonRelation);
                buttonRelation.setText(items[item]);
                relation_position = item;
                dialog.cancel();
            }
        });
        relation_dialog.show();
    }

    /**
     * 保存
     * 
     * @param ArrayList<Relation> relations グループクラス配列
     * @param Integer id メンバーID
     * @return void
     * @access private
     */
    private void save(ArrayList<RelationEntity> relations, Integer id)
    {
        TextView editTextName = (TextView) findViewById(R.id.editTextName);
        String name = editTextName.getText().toString();

        //バリデート
        Validate v = new Validate();
        ValidateRequire.check(v, name, "名前");

        if (v.getResult() == false) {
            showErrorDialog(v.getErrorMsgList());
            return;
        }

        //保存
        TextView editTextKana = (TextView) findViewById(R.id.editTextKana);
        String kana = editTextKana.getText().toString();

        String birthday = save_birthday;

        RelationEntity relation = relations.get(relation_position);
        int relation_id = relation.getId();

        if (uri != null) {
            photo = 1;
        }

        TextView editTextMemo = (TextView) findViewById(R.id.editTextMemo);
        String memo = editTextMemo.getText().toString();

        SQLiteDatabase db = helper.getWritableDatabase();
        MemberDao memberDao = new MemberDao();
        memberDao.save(db, id, name, kana, birthday, relation_id, photo, memo);

        //画像保存
        if (id == 0) {
            id = memberDao.getInsertedId();
        }
        saveImg(id, photo, uri, "member");
        db.close();

        //一時フォルダを削除
        deleteTmpDir();

        AndroidUtils.showToastS(getApplicationContext(), "保存しました。");
        finish();
    }

    /**
     * 写真選択用ダイアログ表示
     * 
     * @return void
     * @access private
     */
    private void showPhotoSelectDialog()
    {
        if (photo_select_dialog == null) {
            photo_select_dialog = new PhotoSelectDialog(this);

            //画像選択ボタン
            Button buttonGallery = (Button) photo_select_dialog.findViewById(R.id.buttonGallery);
            buttonGallery.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, Config.SELECT_GALLERY);
                    photo_select_dialog.dismiss();
                }
            });

            //カメラ選択ボタン
            camera_tmp_uri = getTmpPhotoUri();
            Button buttonCamera = (Button) photo_select_dialog.findViewById(R.id.buttonCamera);
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, camera_tmp_uri);
                    startActivityForResult(intent, Config.SELECT_CAMERA);
                    photo_select_dialog.dismiss();
                }
            });

            //画像削除ボタン
            Button buttonPhotoDelete = (Button) photo_select_dialog.findViewById(R.id.buttonPhotoDelete);
            buttonPhotoDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
                    imageViewPhoto.setImageResource(R.drawable.no_image);
                    uri = null;
                    photo = 0;
                    photo_select_dialog.dismiss();
                }
            });
        }

        photo_select_dialog.show();
    }
}