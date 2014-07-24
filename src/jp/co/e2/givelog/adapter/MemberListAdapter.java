package jp.co.e2.givelog.adapter;

import java.util.List;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.entity.MemberEntity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 人物一覧アダプター
 * 
 * @access public
 */
public class MemberListAdapter extends ArrayAdapter<MemberEntity>
{
	private LayoutInflater inflater;

	/**
	 * コンストラクタ
	 * 
	 * @param Context contex
	 * @param Integer layoutId
	 * @param List<Member> objects
	 */
	public MemberListAdapter(Context context, int layoutId, List<MemberEntity> objects)
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
		MemberEntity item = getItem(position);

		convertView = inflater.inflate(R.layout.part_member_list, null);

		//関係性ラベル
		if (item.getRelationName() != null) {
			//名前と誕生日欄を非表示に
			LinearLayout linearLayoutName = (LinearLayout) convertView.findViewById(R.id.linearLayoutName);
			linearLayoutName.setVisibility(View.GONE);

			//関係性を表示
			TextView textViewRelation = (TextView) convertView.findViewById(R.id.textViewRelation);
			textViewRelation.setVisibility(View.VISIBLE);

			//関係性文字列
			textViewRelation.setText(item.getRelationName());

			//背景色
			GradientDrawable shape = (GradientDrawable) textViewRelation.getBackground();
			textViewRelation.setBackgroundDrawable(shape);
			shape.setColor(item.getLabel());
			isEnabled(position);	//クリックできないように

		}
		//人物
		else {
			//名前と誕生日欄を表示
			LinearLayout linearLayoutName = (LinearLayout) convertView.findViewById(R.id.linearLayoutName);
			linearLayoutName.setVisibility(View.VISIBLE);

			//関係性を非表示
			TextView textViewRelation = (TextView) convertView.findViewById(R.id.textViewRelation);
			textViewRelation.setVisibility(View.GONE);

			//ポイント
			TextView textViewPoint = (TextView) convertView.findViewById(R.id.textViewPoint);
			GradientDrawable shape = (GradientDrawable) textViewPoint.getBackground();
			textViewPoint.setBackgroundDrawable(shape);
			shape.setColor(item.getLabel());
			textViewPoint.setText("");

			//名前
			TextView textViewName = (TextView) convertView.findViewById(R.id.textName);
			textViewName.setText(item.getName());

			//誕生日と年齢
			TextView textViewBirth = (TextView) convertView.findViewById(R.id.textBirthday);
			textViewBirth.setText(item.getBirthAge());
		}

		return convertView;
	}

	/**
	 * 行のクリックを無効にするかどうか
	 * 
	 * @param Integer position
	 * @return boolean
	 * @access public
	 */
	@Override
	public boolean isEnabled(int position)
	{
		MemberEntity item = getItem(position);

		if (item.getRelationName() != null) {
			return false;
		} else {
			return true;
		}
	}
}