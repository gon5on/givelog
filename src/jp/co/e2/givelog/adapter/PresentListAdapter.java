package jp.co.e2.givelog.adapter;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.entity.PresentEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * プレゼント一覧アダプター
 * 
 * @access public
 */
public class PresentListAdapter extends ArrayAdapter<PresentEntity>
{
	private LayoutInflater inflater;
	private View convertView;

	private Integer type = 1;					//だれからだれへフラグ

	/**
	 * コンストラクタ
	 * 
	 * @param Context contex
	 * @param Integer layoutId
	 * @param List<Member> objects
	 */
	public PresentListAdapter(Context context, int layoutId, ArrayList<PresentEntity> objects)
	{
		super(context, 0, objects);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * getView
	 * 
	 * @param int position
	 * @param View convertView
	 * @params ViewGroup parent
	 * @return View convertView
	 * @access public
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// 特定の行(position)のデータを得る
		PresentEntity item = getItem(position);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.part_present_list, null);
		}

		//日付
		TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
		textViewDate.setText(item.getDate());

		//イベント
		TextView textViewEvent = (TextView) convertView.findViewById(R.id.textViewEvent);
		textViewEvent.setText(item.getEvent());

		//だれからだれへと品物
		TextView textViewPresent = (TextView) convertView.findViewById(R.id.textViewPresent);
		textViewPresent.setText(item.getPresentForList(type));

		return convertView;
	}

	/**
	 * だれからだれへフラグをセット
	 * 
	 * @param Integer value
	 * @return void
	 * @access public
	 */
	public void setType(int value)
	{
		type = value;
	}
}