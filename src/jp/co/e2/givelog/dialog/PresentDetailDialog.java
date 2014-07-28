package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.common.ImgUtils;
import jp.co.e2.givelog.config.Config;
import jp.co.e2.givelog.dialog.ConfirmDialog.CallbackListener;
import jp.co.e2.givelog.entity.PresentEntity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * プレゼント詳細ダイアログ
 * 
 * @access public
 */
public class PresentDetailDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @param PresentDetailDialog data プレゼントデータ
     * @return ConfirmDialog
     * @access public
     */
    public static PresentDetailDialog getInstance(PresentEntity data)
    {
        PresentDetailDialog dialog = new PresentDetailDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * onCreateDialog
     * 
     * @param Bundle savedInstanceState
     * @return Dialog
     * @access public
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //bundleから値を取り出す
        PresentEntity data = (PresentEntity) getArguments().getSerializable("data");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_present_detail);

        //ボタンセット
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        //プレゼントデータセット
        setPresentData(dialog, data);

        return dialog;
    }

    /**
     * プレゼント情報を画面にセットする
     * 
     * @param Dialog dialog
     * @param PresentEntity data プレゼントクオブジェクト
     * @return void
     * @access public
     */
    public void setPresentData(Dialog dialog, PresentEntity data)
    {
        //日付とイベント
        TextView textViewDate = (TextView) dialog.findViewById(R.id.textViewDate);
        textViewDate.setText(data.getDate() + "  " + data.getEvent());

        //だれから
        TextView textViewFrom = (TextView) dialog.findViewById(R.id.textViewFrom);
        textViewFrom.setText(data.getGiveFull());

        //だれへ
        TextView textViewTo = (TextView) dialog.findViewById(R.id.textViewTo);
        textViewTo.setText(data.getGaveFull());

        //プレゼント
        TextView textViewPresent = (TextView) dialog.findViewById(R.id.textViewPresent);
        textViewPresent.setText(data.getPresent() + data.getPriceUnit(1));

        //メモ
        TextView textViewMemo = (TextView) dialog.findViewById(R.id.textViewMemo);
        TextView textViewMemoLabel = (TextView) dialog.findViewById(R.id.textViewMemoLabel);

        if (data.getMemo().length() != 0) {
            textViewMemo.setText(data.getMemo());
            textViewMemo.setVisibility(View.VISIBLE);
            textViewMemoLabel.setVisibility(View.VISIBLE);
        } else {
            textViewMemo.setVisibility(View.GONE);
            textViewMemoLabel.setVisibility(View.GONE);
        }

        //写真
        ImageView imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);

        if (data.getPhoto() != null) {
            imageViewPhoto.setVisibility(View.VISIBLE);

            Resources res = getResources();
            Integer height = res.getDimensionPixelSize(R.dimen.present_photo_height);
            Integer width = res.getDimensionPixelSize(R.dimen.present_photo_width);

            String imgPath = Config.getImgDirPath(getActivity()) + "/" + data.getPhoto();
            ImgUtils imgUtils = new ImgUtils(imgPath);

            imageViewPhoto.setImageBitmap(imgUtils.getResizeImg(height, width));
            imageViewPhoto.setEnabled(true);
        } else {
            imageViewPhoto.setVisibility(View.GONE);
        }
    }
}