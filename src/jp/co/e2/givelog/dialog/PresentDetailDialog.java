package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.entity.PresentEntity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * プレゼント詳細ダイアログ
 * 
 * @access public
 */
public class PresentDetailDialog extends Dialog
{
	private Context context;	//コンテキスト
	private String file_dir;	//写真保存パス

	/**
	 * コンストラクタ
	 * 
	 * @param Context context コンテキスト
	 * @param String file_dir
	 * @access public
	 */
	public PresentDetailDialog(Context context, String file_dir)
	{
		super(context);

		this.context = context;
		this.file_dir = file_dir;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_present_detail);

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * プレゼント情報を画面にセットする
	 * 
	 * @param PresentEntity present プレゼントクラス
	 * @return void
	 * @access public
	 */
	public void setContent(final PresentEntity present)
	{
		//日付とイベント
		TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
		textViewDate.setText(present.getDate() + "  " + present.getEvent());

		//だれから
		TextView textViewFrom = (TextView) findViewById(R.id.textViewFrom);
		textViewFrom.setText(present.getGiveFull());

		//だれへ
		TextView textViewTo = (TextView) findViewById(R.id.textViewTo);
		textViewTo.setText(present.getGaveFull());

		//プレゼント
		TextView textViewPresent = (TextView) findViewById(R.id.textViewPresent);
		textViewPresent.setText(present.getPresent() + present.getPriceUnit(1));

		//メモ
		TextView textViewMemo = (TextView) findViewById(R.id.textViewMemo);
		TextView textViewMemoLabel = (TextView) findViewById(R.id.textViewMemoLabel);

		if (present.getMemo().length() != 0) {
			textViewMemo.setText(present.getMemo());
			textViewMemo.setVisibility(View.VISIBLE);
			textViewMemoLabel.setVisibility(View.VISIBLE);
		} else {
			textViewMemo.setVisibility(View.GONE);
			textViewMemoLabel.setVisibility(View.GONE);
		}

		//写真
		ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);

		if (present.getPhoto() != null) {
			imageViewPhoto.setVisibility(View.VISIBLE);

			Resources res = context.getResources();
			Integer height = res.getDimensionPixelSize(R.dimen.present_photo_height);
			Integer width = res.getDimensionPixelSize(R.dimen.present_photo_width);

			String img_path = file_dir + "/" + present.getPhoto();
			ImgUtils imgUtils = new ImgUtils(context, img_path);

			imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
			imageViewPhoto.setEnabled(true);
		} else {
			imageViewPhoto.setVisibility(View.GONE);
		}
	}
}