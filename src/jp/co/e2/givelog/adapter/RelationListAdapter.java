package jp.co.e2.givelog.adapter;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.model.Relation;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * グループ一覧アダプター
 * 
 * @access public
 */
public class RelationListAdapter extends ArrayAdapter<Relation>
{
	private LayoutInflater inflater;
	private View convertView;

	/**
	 * コンストラクタ
	 * 
	 * @param Context contex
	 * @param Integer layoutId
	 * @param List<Member> objects
	 */
	public RelationListAdapter(Context context, int layoutId, ArrayList<Relation> objects)
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
		Relation item = getItem(position);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.part_relation_list, null);
		}

		//名前
		TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
		textViewName.setText(item.getName());

		//背景色
		TextView textViewColor = (TextView) convertView.findViewById(R.id.textViewColor);
		GradientDrawable shape = (GradientDrawable) textViewColor.getBackground();
		textViewColor.setBackgroundDrawable(shape);
		shape.setColor(item.getLabel());

		return convertView;
	}
}