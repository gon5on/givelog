package jp.co.e2.givelog.activity;

import java.util.ArrayList;
import java.util.Calendar;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.common.Utils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.ErrorDialog;
import jp.co.e2.givelog.dialog.PersonAddDialog;
import jp.co.e2.givelog.dialog.PhotoSelectDialog;
import jp.co.e2.givelog.entity.MemberEntity;
import jp.co.e2.givelog.entity.PresentEntity;
import jp.co.e2.givelog.model.MemberDao;
import jp.co.e2.givelog.model.PresentDao;
import jp.co.e2.givelog.validate.Validate;
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
 * プレゼント追加アクテビティ
 * 
 * @access public
 */
public class PresentAddActivity extends BaseActivity
{
	private static ArrayList<MemberEntity> members;				//人物オブジェクト配列
	private static String[] member_from_items;				//だれから文字列格納配列
	private static String[] member_to_items;				//だれへ文字列格納配列

	private static DatePickerDialog dialog_date_picker;		//datePickerダイアログ
	private static AlertDialog.Builder dialog_from;			//だれからダイアログ
	private static AlertDialog.Builder dialog_to;			//だれへダイアログ
	private static PersonAddDialog dialog_from_add;			//だれから追加ダイアログ
	private static PersonAddDialog dialog_to_add;			//だれへ追加ダイアログ
	private static ErrorDialog dialog_error;				//エラーダイアログ
	private static PhotoSelectDialog photo_select_dialog;	//写真選択ダイアログ

	private static Integer id;								//引数のID
	private static boolean[] selected_from;					//だれから選択済値格納変数
	private static boolean[] selected_to;					//だれへ選択済値格納変数
	private static Integer selected_year;					//ダイアログ表示用年格納変数
	private static Integer selected_month;					//ダイアログ表示用月格納変数
	private static Integer selected_day;					//ダイアログ表示用日格納変数
	private static String save_date;						//保存用日付変数
	private static String add_from;							//だれから追加値格納変数
	private static String add_to;							//だれへ追加値格納変数
	private static Uri uri;									//画像パス格納用変数
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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_present_add);

		//初期化
		setNull();

		//ヘッダのボタン
		setHeaderReturnButton();

		//引数のID取得
		Intent i = getIntent();
		id = i.getIntExtra("ID", 0);
		Integer member_id = i.getIntExtra("MEMBER_ID", 0);
		Integer type = i.getIntExtra("TYPE", 0);

		//ソフトウェアキーボードをデフォで出さない
		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		//人物選択用一覧取得
		SQLiteDatabase db = helper.getWritableDatabase();
		MemberDao memberDao = new MemberDao(getApplicationContext());
		members = memberDao.selectMemberIdNameList(db);
		member_from_items = memberDao.getMemberSpinnerList(members);
		member_to_items = memberDao.getMemberSpinnerList(members);
		db.close();

		//だれからだれへ選択済み変数に配列要素を用意する
		selected_from = new boolean[member_from_items.length];
		selected_to = new boolean[member_to_items.length];

		//画像表示時のピクセルを計算しておく
		Resources res = getResources();
		height = res.getDimensionPixelSize(R.dimen.tmp_photo_height);
		width = res.getDimensionPixelSize(R.dimen.tmp_photo_width);

		//ボタンにイベントリスナーセット
		setButton();

		//編集の場合
		if (id != 0) {
			edit();
		}

		//人物詳細画面からプレゼント追加ボタンが押された場合
		if (member_id != 0) {
			setDefaultValue(member_id, type);
		}
	}

	/**
	 * onDestroy
	 * 
	 * @return void
	 * @access protected
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		//初期化
		setNull();
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
			ImgUtils ImgUtils = new ImgUtils(getApplicationContext(), uri);
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
	 * 編集の場合
	 * 
	 * @return void
	 * @access private
	 */
	private void edit()
	{
		//データ取得
		SQLiteDatabase db = helper.getWritableDatabase();
		PresentDao presentDao = new PresentDao(getApplicationContext());
		PresentEntity present = presentDao.selectPresentData(db, id);

		ArrayList<Integer> from = presentDao.selectFromList(db, id);
		ArrayList<Integer> to = presentDao.selectToList(db, id);

		//だれからセット
		selected_from = setFromToValueEdit(from, member_from_items, presentDao.getFromCustom(db, id));
		if (presentDao.getFromCustom(db, id) != null) {
			member_from_items[(member_from_items.length - 1)] = presentDao.getFromCustom(db, id);
			add_from = presentDao.getFromCustom(db, id);
		}

		Button buttonFrom = (Button) findViewById(R.id.buttonFrom);
		buttonFrom.setText(setFromToButtonName(selected_from, member_from_items));

		//だれへセット
		selected_to = setFromToValueEdit(to, member_to_items, presentDao.getToCustom(db, id));
		if (presentDao.getToCustom(db, id) != null) {
			member_to_items[(member_to_items.length - 1)] = presentDao.getToCustom(db, id);
			add_to = presentDao.getToCustom(db, id);
		}

		Button buttonTo = (Button) findViewById(R.id.buttonTo);
		buttonTo.setText(setFromToButtonName(selected_to, member_to_items));

		//日付
		Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
		buttonDatePick.setText(present.getDate());

		String[] date = Utils.separateDate(present.getDate(), "/");
		save_date = present.getDate();
		selected_year = Integer.valueOf(date[0]);
		selected_month = Integer.valueOf(date[1]) - 1;
		selected_day = Integer.valueOf(date[2]);

		//イベントセット
		TextView editTextEvent = (TextView) findViewById(R.id.editTextEvent);
		editTextEvent.setText(present.getEvent());

		//品物セット
		TextView editTextPresent = (TextView) findViewById(R.id.editTextPresent);
		editTextPresent.setText(present.getPresent());

		//金額セット
		TextView editTextPrice = (TextView) findViewById(R.id.editTextPrice);
		editTextPrice.setText(present.getPrice());

		//メモセット
		TextView editTextMemo = (TextView) findViewById(R.id.editTextMemo);
		editTextMemo.setText(present.getMemo());

		//写真セット
		if (present.getPhoto() != null) {
			photo = 1;

			String img_path = file_dir + "/" + present.getPhoto();
			ImgUtils imgUtils = new ImgUtils(getApplicationContext(), img_path);

			ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
			imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
		}

		db.close();
	}

	/**
	 * 人物詳細からプレゼント追加ボタンが押された場合
	 * 
	 * @param Integer member_id メンバーID
	 * @param Integer type 誰からだれへタイプ
	 * @return void
	 * @access private
	 */
	private void setDefaultValue(Integer member_id, Integer type)
	{
		ArrayList<Integer> person = new ArrayList<Integer>();
		person.add(member_id);

		//だれからセット
		if (type == 1) {
			selected_from = setFromToValueEdit(person, member_from_items, null);
			Button buttonFrom = (Button) findViewById(R.id.buttonFrom);
			buttonFrom.setText(setFromToButtonName(selected_from, member_from_items));
		}
		//だれへセット
		else {
			selected_to = setFromToValueEdit(person, member_to_items, null);
			Button buttonTo = (Button) findViewById(R.id.buttonTo);
			buttonTo.setText(setFromToButtonName(selected_to, member_to_items));
		}
	}

	/**
	 * 編集の場合、だれからだれへ選択済み変数に値を入れる
	 * 
	 * @param ArrayList<Integer> saved 保存済
	 * @param String[] member_items メンバー配列
	 * @param String custom カスタム人物名
	 * @return boolean[] check_list チェック済配列
	 * @access private
	 */
	private boolean[] setFromToValueEdit(ArrayList<Integer> saved, String[] member_items, String custom)
	{
		final boolean[] check_list = new boolean[member_items.length];

		for (int i = 0; i < saved.size(); i++) {
			for (int j = 0; j < member_items.length; j++) {
				MemberEntity tmp = members.get(j);

				if (saved.get(i) == tmp.getId()) {
					check_list[j] = true;
					break;
				}
			}
		}

		//カスタムに入力値があれば、配列の最後にチェックを入れる
		if (custom != null) {
			check_list[(member_items.length - 1)] = true;
		}

		return check_list;
	}

	/**
	 * ボタンにイベントをセット
	 * 
	 * @return void
	 * @access private
	 */
	private void setButton()
	{
		//だれから選択ボタン
		Button buttonFrom = (Button) findViewById(R.id.buttonFrom);
		buttonFrom.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createFromPicker();
			}
		});

		//だれへ選択ボタン
		Button buttonTo = (Button) findViewById(R.id.buttonTo);
		buttonTo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createToPicker();
			}
		});

		//日付選択ボタン
		Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
		buttonDatePick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDatePicker();
			}
		});

		//保存ボタン
		Button buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				save();
			}
		});

		//写真
		ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
		imageViewPhoto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showPhotoSelectDialog();
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

		if (dialog_date_picker == null) {
			dialog_date_picker = new DatePickerDialog(this, new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int day) {
					Button buttonDatePick = (Button) findViewById(R.id.buttonDatePick);
					buttonDatePick.setText(Utils.dateFormat(year, month, day, "/"));
					selected_year = year;
					selected_month = month;
					selected_day = day;
					save_date = Utils.dateFormat(year, month, day, "/");
				}
			}, selected_year, selected_month, selected_day);
		}

		dialog_date_picker.show();
	}

	/**
	 * だれから選択ダイアログ作成
	 * 
	 * @return void
	 * @access private
	 */
	private void createFromPicker()
	{
		if (dialog_from == null) {
			dialog_from = new AlertDialog.Builder(this);

			dialog_from.setMultiChoiceItems(member_from_items, selected_from, new DialogInterface.OnMultiChoiceClickListener() {
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					selected_from[which] = isChecked;
					if (which == (member_from_items.length - 1)) {
						showDialogFromAdd(isChecked);
					}
				}
			});
			dialog_from.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//カスタム値が空だったら、カスタムのチェックを外しておく
					if (add_from == null || add_from.length() == 0) {
						selected_from[member_from_items.length - 1] = false;
					}

					//ボタン名称差し替え
					Button button = (Button) findViewById(R.id.buttonFrom);
					button.setText(setFromToButtonName(selected_from, member_from_items));
					dialog.cancel();
				}
			});
		}

		dialog_from.show();
	}

	/**
	 * だれへ選択ダイアログ作成
	 * 
	 * @return void
	 * @access private
	 */
	private void createToPicker()
	{
		if (dialog_to == null) {
			dialog_to = new AlertDialog.Builder(this);

			dialog_to.setMultiChoiceItems(member_to_items, selected_to, new DialogInterface.OnMultiChoiceClickListener() {
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					selected_to[which] = isChecked;
					if (which == (member_to_items.length - 1)) {
						showDialogToAdd(isChecked);
					}
				}
			});
			dialog_to.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//カスタム値が空だったら、カスタムのチェックを外しておく
					if (add_to == null || add_to.length() == 0) {
						selected_to[member_to_items.length - 1] = false;
					}

					//ボタン名称差し替え
					Button button = (Button) findViewById(R.id.buttonTo);
					button.setText(setFromToButtonName(selected_to, member_to_items));
					dialog.cancel();
				}
			});
		}

		dialog_to.show();
	}

	/**
	 * だれからだれへボタンの名称を差し替える
	 * 
	 * @param boolean[] ckeck_list チェックボックスのチェック配列
	 * @param String[] member_items メンバー配列
	 * @return String ボタン名称
	 * @access private
	 */
	private String setFromToButtonName(boolean[] ckeck_list, String[] member_items)
	{
		String str = "";
		Integer cnt = 0;

		for (int i = 0; i < ckeck_list.length; i++) {
			if (ckeck_list[i] == true) {
				if (str.length() > 0) {
					str = str + ", ";
				}
				str = str + member_items[i];
				cnt += member_items[i].length();
			}
		}
		if (cnt >= 15) {
			String tmp = str.substring(0, 15);	//カンマ分、適当に13より2多い15にしている
			str = tmp + " …";
		} else if (str.length() == 0) {
			str = "選択";
		}

		return str;
	}

	/**
	 * 保存
	 * 
	 * @return void
	 * @access private
	 */
	private void save()
	{
		//バリデート
		if (validate() == false) {
			return;
		}

		TextView editTextPresent = (TextView) findViewById(R.id.editTextPresent);
		String present = String.valueOf(editTextPresent.getText());

		if (uri != null) {
			photo = 1;
		}

		TextView editTextEvent = (TextView) findViewById(R.id.editTextEvent);
		String event = String.valueOf(editTextEvent.getText());

		TextView editTextPrice = (TextView) findViewById(R.id.editTextPrice);
		String price = String.valueOf(editTextPrice.getText());

		TextView editTextMemo = (TextView) findViewById(R.id.editTextMemo);
		String memo = String.valueOf(editTextMemo.getText());

		SQLiteDatabase db = helper.getWritableDatabase();
		PresentDao presentDao = new PresentDao(getApplicationContext());
		presentDao.setMembers(members);
		presentDao.save(db, id, photo, selected_from, selected_to, save_date, event, present, price, memo, add_from, add_to);

		//画像保存
		if (id == 0) {
			id = presentDao.getInsertedId();
		}
		saveImg(id, photo, uri, "present");
		db.close();

		//一時フォルダを削除
		deleteTmpDir();

		Utils.showToast(getApplication(), "保存しました。");
		finish();
	}

	/**
	 * バリデート
	 * 
	 * @return void
	 * @access private
	 */
	private Boolean validate()
	{
		Validate validate = new Validate();

		//だれから
		validate.set();
		validate.require.check(selected_from, "", "だれからを選択してください。");

		//だれへ
		validate.set();
		validate.require.check(selected_to, "", "だれへを選択してください。");

		//誰から誰へ両方ともカスタムでないか確認
		if (checkBothCustom() == false) {
			validate.setResult(false);
			validate.addErrorMsg("だれからとだれへ、両方ともカスタム(自由記入)にすることはできません。");
		}

		//日付
		validate.set();
		validate.require.check(save_date, "", "日付を選択してください。");

		//プレゼント
		validate.set();
		TextView editTextPresent = (TextView) findViewById(R.id.editTextPresent);
		validate.require.check(editTextPresent.getText().toString(), "品物", "");

		//エラー
		if (validate.getResult() == false) {
			showErrorDialog(validate.getErrorMsg());
			return false;
		}

		return true;
	}

	/**
	 * 追加バリデート
	 * 
	 * @return void
	 * @access private
	 */
	private Boolean checkBothCustom()
	{
		Integer from_cnt = 0;
		Integer to_cnt = 0;

		for (int i = 0; i < selected_from.length; i++) {
			if (selected_from[i] == true) {
				from_cnt++;
			}
		}
		for (int i = 0; i < selected_to.length; i++) {
			if (selected_to[i] == true) {
				to_cnt++;
			}
		}

		if (from_cnt == 1 && to_cnt == 1 &&
				selected_from[selected_from.length - 1] == true &&
				selected_to[selected_to.length - 1] == true) {
			return false;
		}

		return true;
	}

	/**
	 * エラー用ダイアログ表示
	 * 
	 * @param ArrayList<String> msgs エラー文言配列
	 * @return void
	 * @access private
	 */
	private void showErrorDialog(ArrayList<String> msgs)
	{
		if (dialog_error == null) {
			dialog_error = new ErrorDialog(this, "");
		}
		dialog_error.setMsg(Utils.implode(msgs, "\n"));
		dialog_error.show();
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
					camera_tmp_uri = null;
					photo = 0;
					photo_select_dialog.dismiss();
				}
			});
		}

		photo_select_dialog.show();
	}

	/**
	 * 誰から追加ダイアログ表示
	 * 
	 * @param Boolean isChecked チェック済かどうか
	 * @return void
	 * @access private
	 */
	private void showDialogFromAdd(Boolean isChecked)
	{
		if (dialog_from_add == null) {
			dialog_from_add = new PersonAddDialog(this);

			//OKボタン
			Button buttonOk = (Button) dialog_from_add.findViewById(R.id.buttonOk);
			buttonOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					add_from = dialog_from_add.getText();
					if (add_from.length() > 0) {
						member_from_items[member_from_items.length - 1] = add_from;
					} else {
						member_from_items[member_from_items.length - 1] = "カスタム(自由記入)";
					}
					dialog_from_add.dismiss();
				}
			});
		}

		if (isChecked == true) {
			dialog_from_add.setText(add_from);
			dialog_from_add.show();
		}
	}

	/**
	 * 誰へ追加ダイアログ表示
	 * 
	 * @param Boolean isChecked チェック済かどうか
	 * @return void
	 * @access private
	 */
	private void showDialogToAdd(Boolean isChecked)
	{
		if (dialog_to_add == null) {
			dialog_to_add = new PersonAddDialog(this);

			//OKボタン
			Button buttonOk = (Button) dialog_to_add.findViewById(R.id.buttonOk);
			buttonOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					add_to = dialog_to_add.getText();
					if (add_to.length() > 0) {
						member_to_items[member_to_items.length - 1] = add_to;
					} else {
						member_to_items[member_to_items.length - 1] = "カスタム(自由記入)";
					}
					dialog_to_add.dismiss();
				}
			});
		}

		if (isChecked == true) {
			dialog_to_add.setText(add_to);
			dialog_to_add.show();
		}
	}

	/**
	 * 初期化
	 * 
	 * @return void
	 * @access private
	 */
	private void setNull()
	{
		members = null;
		member_from_items = null;
		member_to_items = null;
		dialog_date_picker = null;
		dialog_from = null;
		dialog_to = null;
		dialog_from_add = null;
		dialog_to_add = null;
		dialog_error = null;
		photo_select_dialog = null;
		id = null;
		selected_from = null;
		selected_to = null;
		selected_year = null;
		selected_month = null;
		selected_day = null;
		save_date = null;
		add_from = null;
		add_to = null;
		uri = null;
		camera_tmp_uri = null;
		width = null;
		height = null;
		photo = 0;
	}
}