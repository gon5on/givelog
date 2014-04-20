package jp.co.e2.givelog.dialog;

import jp.co.e2.givelog.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * カラーピッカーダイアログクラス
 * 
 * @access public
 */
public class ColorPickerDialog extends Dialog
{
	private static Context context;		//コンテキスト
	private static Button button;		//グループ変更ダイアログの色選択ボタン
	private static Integer color;		//色コード

	/**
	 * コンテキスト
	 * 
	 * @param Context myContext
	 * @param Button myButton ボタン
	 * @param Integer defoColor デフォルト色コード
	 * @access public
	 */
	public ColorPickerDialog(Context myContext, Button myButton, Integer defoColor)
	{
		super(myContext);

		context = myContext;
		button = myButton;
		color = defoColor;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_color_picker);

		//閉じるボタン
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		Button button01 = (Button) findViewById(R.id.button01);
		GradientDrawable shape1 = (GradientDrawable) button01.getBackground();
		button01.setBackgroundDrawable(shape1);
		shape1.setColor(context.getResources().getColor(R.color.group1));

		button01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group1));
			}
		});

		Button button02 = (Button) findViewById(R.id.button02);
		GradientDrawable shape2 = (GradientDrawable) button02.getBackground();
		button02.setBackgroundDrawable(shape2);
		shape2.setColor(context.getResources().getColor(R.color.group2));

		button02.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group2));
			}
		});

		Button button03 = (Button) findViewById(R.id.button03);
		GradientDrawable shape3 = (GradientDrawable) button03.getBackground();
		button03.setBackgroundDrawable(shape3);
		shape3.setColor(context.getResources().getColor(R.color.group3));

		button03.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group3));
			}
		});

		Button button04 = (Button) findViewById(R.id.button04);
		GradientDrawable shape4 = (GradientDrawable) button04.getBackground();
		button04.setBackgroundDrawable(shape4);
		shape4.setColor(context.getResources().getColor(R.color.group4));

		button04.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group4));
			}
		});

		Button button05 = (Button) findViewById(R.id.button05);
		GradientDrawable shape5 = (GradientDrawable) button05.getBackground();
		button05.setBackgroundDrawable(shape5);
		shape5.setColor(context.getResources().getColor(R.color.group5));

		button05.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group5));
			}
		});

		Button button06 = (Button) findViewById(R.id.button06);
		GradientDrawable shape6 = (GradientDrawable) button06.getBackground();
		button06.setBackgroundDrawable(shape6);
		shape6.setColor(context.getResources().getColor(R.color.group6));

		button06.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group6));
			}
		});

		Button button07 = (Button) findViewById(R.id.button07);
		GradientDrawable shape7 = (GradientDrawable) button07.getBackground();
		button07.setBackgroundDrawable(shape7);
		shape7.setColor(context.getResources().getColor(R.color.group7));

		button07.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group7));
			}
		});

		Button button08 = (Button) findViewById(R.id.button08);
		GradientDrawable shape8 = (GradientDrawable) button08.getBackground();
		button08.setBackgroundDrawable(shape8);
		shape8.setColor(context.getResources().getColor(R.color.group8));

		button08.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group8));
			}
		});

		Button button09 = (Button) findViewById(R.id.button09);
		GradientDrawable shape9 = (GradientDrawable) button09.getBackground();
		button09.setBackgroundDrawable(shape9);
		shape9.setColor(context.getResources().getColor(R.color.group9));

		button09.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setBgColor(context.getResources().getColor(R.color.group9));
			}
		});
	}

	/**
	 * グループ変更ダイアログの色選択ボタン背景色を
	 * 選択された色で塗りつぶして、ダイアログを閉じる
	 * 
	 * @param Integer myColor 選択された色
	 * @return void
	 * @access private
	 */
	private void setBgColor(Integer myColor)
	{
		color = myColor;

		GradientDrawable shape = (GradientDrawable) button.getBackground();
		button.setBackgroundDrawable(shape);
		shape.setColor(myColor);

		dismiss();
	}

	/**
	 * グループ変更ダイアログの色選択ボタンをセット
	 * 
	 * @param Button myButton グループ変更ダイアログの色選択ボタン
	 * @return void
	 * @access public
	 */
	public void setButton(Button myButton)
	{
		button = myButton;
	}

	/**
	 * 選択された色コードをセット
	 * 
	 * @param Integer selected_color 選択された色コード
	 * @return void
	 * @access public
	 */
	public void setColor(Integer selected_color)
	{
		color = selected_color;
	}

	/**
	 * 選択された色コードを返す
	 * 
	 * @return Integer color 選択された色コード
	 * @access public
	 */
	public Integer getColor()
	{
		return color;
	}
}