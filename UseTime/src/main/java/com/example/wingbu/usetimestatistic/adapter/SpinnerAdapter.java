package com.example.wingbu.usetimestatistic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wingbu.usetimestatistic.R;

import java.util.ArrayList;

/**
 * 主界面中日期选择的SpinnerAdapter
 *
 * （不好用，所以弃坑了）
 *
 * Created by Wingbu on 2017/8/28.
 */

public class SpinnerAdapter extends BaseAdapter{

    private ArrayList<String> mList;
    private Context mContext;

    public SpinnerAdapter(ArrayList<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public SpinnerAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_date, null);
        if(view != null) {
            TextView TextView1 = (TextView) view.findViewById(R.id.tv_date);
            TextView1.setText(mList.get(i));
        }
        return view;
    }
}
