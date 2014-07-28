package jp.co.e2.givelog.adapter;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.entity.RelationEntity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * グループ一覧アダプター
 * 
 * @access public
 */
public class RelationListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<RelationEntity> mData;         //グループデータ

    /**
     * コンストラクタ
     * 
     * @param Context context
     * @param Integer layoutId
     * @param List<Member> data
     * @param Integer type だれからだれへフラグ
     */
    public RelationListAdapter(Context context, ArrayList<RelationEntity> data, Integer type)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
    }

    /**
     * getView
     * 
     * @param int position
     * @param View convertView
     * @param ViewGroup parent
     * @return View convertView
     * @access public
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // 特定の行(position)のデータを得る
        RelationEntity item = (RelationEntity) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.part_relation_list, null);
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

    /**
     * getCount
     * 
     * @return Integer
     * @access public
     */
    @Override
    public int getCount()
    {
        return mData.size();
    }

    /**
     * getItem
     * 
     * @param int position
     * @return Integer
     * @access public
     */
    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    /**
     * getItemId
     * 
     * @param int position
     * @return Integer
     * @access public
     */
    @Override
    public long getItemId(int position)
    {
        return mData.get(position).getId();
    }
}