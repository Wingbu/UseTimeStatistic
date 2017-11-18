package com.example.wingbu.usetimestatistic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.wingbu.usetimestatistic.R;
import com.example.wingbu.usetimestatistic.adapter.UseTimeDetailAdapter;
import com.example.wingbu.usetimestatistic.adapter.UseTimeEveryDetailAdapter;
import com.example.wingbu.usetimestatistic.domain.OneTimeDetails;
import com.example.wingbu.usetimestatistic.domain.UseTimeDataManager;

/**
 * 应用使用次数详情统计 （内容：当天，每次打开一个应用的使用信息）
 *      和 应用使用activity详情统计（内容：使用app一次，打开了哪些activity）
 *  以上两者公用此activity
 *
 * Created by Wingbu on 2017/9/11.
 */

public class UseTimeDetailActivity extends BaseActivity{

    private RecyclerView mRecyclerView;

    private UseTimeDetailAdapter mUseTimeDetailAdapter;

    private UseTimeEveryDetailAdapter mUseTimeEveryDetailAdapter;

    private UseTimeDataManager mUseTimeDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_time_detail);

        Log.i(UseTimeDataManager.TAG,"  UseTimeDetailActivity      ");
        mUseTimeDataManager = UseTimeDataManager.getInstance(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_use_time_detail);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        initView(type,intent);
    }

    private void initView(String type,Intent intent){
        if("times".equals(type)){
            //显示为次数统计信息
            showAppOpenTimes(intent.getStringExtra("pkg"));
            setActionBarTitle(R.string.action_bar_title_2);
        }else if("details".equals(type)){
            //显示为activity统计信息
            showAppOpenDetails(intent.getStringExtra("pkg"));
            setActionBarTitle(R.string.action_bar_title_3);
        }else {
            Log.i(UseTimeDataManager.TAG,"   未知类型    ");
        }
    }

    private void showAppOpenTimes(String pkg){
        mUseTimeDetailAdapter = new UseTimeDetailAdapter(mUseTimeDataManager.getPkgOneTimeDetailList(pkg));
        mUseTimeDetailAdapter.setOnItemClickListener(new UseTimeDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OneTimeDetails details) {
                mUseTimeDataManager.setmOneTimeDetails(details);
                showDetail(details.getPkgName());
            }
        });
        mRecyclerView.setAdapter(mUseTimeDetailAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showAppOpenDetails(String pkg){
        if(!pkg.equals(mUseTimeDataManager.getmOneTimeDetails().getPkgName())){
            Log.i(UseTimeDataManager.TAG,"  showAppOpenDetails()    包名不一致 ");
        }
        mUseTimeEveryDetailAdapter = new UseTimeEveryDetailAdapter(mUseTimeDataManager.getmOneTimeDetails().getOneTimeDetailEventList());
        mRecyclerView.setAdapter(mUseTimeEveryDetailAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showDetail(String pkg){
        Intent i = new Intent();
        i.setClassName(this,"com.example.wingbu.usetimestatistic.ui.UseTimeDetailActivity");
        i.putExtra("type","details");
        i.putExtra("pkg",pkg);
        startActivity(i);
    }
}
