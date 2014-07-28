package jp.co.e2.givelog.adapter;

import java.util.ArrayList;

import jp.co.e2.givelog.R;
import jp.co.e2.givelog.entity.PresentEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * プレゼント一覧アダプター
 * 
 * @access public
 */
public class PresentListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<PresentEntity> mData;         //プレゼントデータ
    private Integer mType = 1;                      //だれからだれへフラグ

    /**
     * コンストラクタ
     * 
     * @param Context context
     * @param Integer layoutId
     * @param List<Member> data
     * @param Integer type だれからだれへフラグ
     */
    public PresentListAdapter(Context context, ArrayList<PresentEntity> data, Integer type)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
        mType = type;
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
        PresentEntity item = (PresentEntity) mData.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.part_present_list, null);
        }

        //日付
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
        textViewDate.setText(item.getDate());

        //イベント
        TextView textViewEvent = (TextView) convertView.findViewById(R.id.textViewEvent);
        textViewEvent.setText(item.getEvent());

        //だれからだれへと品物
        TextView textViewPresent = (TextView) convertView.findViewById(R.id.textViewPresent);
        textViewPresent.setText(item.getPresentForList(mType));

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