package com.example.wingbu.usetimestatistic.adapter;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wingbu.usetimestatistic.R;
import com.example.wingbu.usetimestatistic.utils.DateTransUtils;

import java.util.ArrayList;

/**
 * Created by Wingbu on 2017/7/20.
 */

public class UseTimeEveryDetailAdapter extends RecyclerView.Adapter<UseTimeEveryDetailAdapter.UseTimeDetailViewHolder>{

    private ArrayList<UsageEvents.Event> mOneTimeDetailEventInfoList;
    private PackageManager packageManager;

    public UseTimeEveryDetailAdapter(ArrayList<UsageEvents.Event> mOneTimeDetailEventInfoList) {
        this.mOneTimeDetailEventInfoList = mOneTimeDetailEventInfoList;
    }

    @Override
    public UseTimeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        packageManager = parent.getContext().getPackageManager();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_pkg_use_time_detail_item_layout, parent, false);
        UseTimeDetailViewHolder holder = new UseTimeDetailViewHolder(v);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(UseTimeDetailViewHolder holder, int position) {
        holder.tv_index.setText("" + (position+1) );
        try {
            holder.iv_icon.setImageDrawable(packageManager.getApplicationIcon(mOneTimeDetailEventInfoList.get(position).getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        holder.tv_activity_name.setText(mOneTimeDetailEventInfoList.get( position * 2 ).getClassName());
        holder.tv_activity_total_use_time.setText((mOneTimeDetailEventInfoList.get( position * 2 + 1).getTimeStamp() - mOneTimeDetailEventInfoList.get( position * 2 ).getTimeStamp())/1000+"s / " + (mOneTimeDetailEventInfoList.get( position * 2 + 1).getTimeStamp() - mOneTimeDetailEventInfoList.get( position * 2 ).getTimeStamp())+" ms");
        holder.tv_start_used_time.setText(DateTransUtils.stampToDate(mOneTimeDetailEventInfoList.get(position * 2).getTimeStamp()));
        holder.tv_stop_used_time.setText(DateTransUtils.stampToDate(mOneTimeDetailEventInfoList.get( position * 2 + 1).getTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return mOneTimeDetailEventInfoList.size()/2;
    }

    public class UseTimeDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView   tv_index;
        public ImageView  iv_icon;
        public TextView   tv_activity_name;
        public TextView   tv_activity_total_use_time;
        public TextView   tv_start_used_time;
        public TextView   tv_stop_used_time;


        public UseTimeDetailViewHolder(View itemView) {
            super(itemView);
            tv_index = (TextView) itemView.findViewById(R.id.index);
            iv_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            tv_activity_name= (TextView) itemView.findViewById(R.id.activity_name);
            tv_activity_total_use_time= (TextView) itemView.findViewById(R.id.activity_total_use_time);
            tv_start_used_time = (TextView) itemView.findViewById(R.id.start_use_time);
            tv_stop_used_time = (TextView) itemView.findViewById(R.id.stop_use_time);
        }
    }
}
