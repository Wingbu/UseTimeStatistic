package com.example.wingbu.usetimestatistic.adapter;

import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wingbu.usetimestatistic.R;
import com.example.wingbu.usetimestatistic.domain.OneTimeDetails;

import java.util.ArrayList;

/**
 * Created by Wingbu on 2017/7/20.
 */

public class UseTimeDetailAdapter extends  RecyclerView.Adapter<UseTimeDetailAdapter.UseTimeDetailViewHolder>{

    private ArrayList<OneTimeDetails> mOneTimeDetailInfoList;
    private PackageManager packageManager;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , OneTimeDetails details);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public UseTimeDetailAdapter(ArrayList<OneTimeDetails> mOneTimeDetailInfoList) {
        this.mOneTimeDetailInfoList = mOneTimeDetailInfoList;
    }

    @Override
    public UseTimeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        packageManager = parent.getContext().getPackageManager();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.use_time_detail_item_layout, parent, false);
        UseTimeDetailViewHolder holder = new UseTimeDetailViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UseTimeDetailViewHolder holder, final int position) {
        holder.tv_index.setText("" + (position+1) );
        try {
            holder.iv_icon.setImageDrawable(packageManager.getApplicationIcon(mOneTimeDetailInfoList.get(position).getPkgName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        holder.tv_start_used_time.setText(mOneTimeDetailInfoList.get(position).getStartTime());
        holder.tv_stop_used_time.setText(mOneTimeDetailInfoList.get(position).getStopTime());
        holder.tv_total_used_time.setText( (mOneTimeDetailInfoList.get(position).getUseTime()/1000) +"s / " + mOneTimeDetailInfoList.get(position).getUseTime()+" ms");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v,mOneTimeDetailInfoList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOneTimeDetailInfoList.size();
    }

    public class UseTimeDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView   tv_index;
        public ImageView  iv_icon;
        public TextView   tv_start_used_time;
        public TextView   tv_stop_used_time;
        public TextView   tv_total_used_time;


        public UseTimeDetailViewHolder(View itemView) {
            super(itemView);
            tv_index = (TextView) itemView.findViewById(R.id.index);
            iv_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            tv_start_used_time = (TextView) itemView.findViewById(R.id.start_use_time);
            tv_stop_used_time = (TextView) itemView.findViewById(R.id.stop_use_time);
            tv_total_used_time = (TextView) itemView.findViewById(R.id.total_use_time);
        }
    }
}
