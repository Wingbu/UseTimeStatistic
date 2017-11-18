package com.example.wingbu.usetimestatistic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.wingbu.usetimestatistic.R;
import com.example.wingbu.usetimestatistic.adapter.SelectDateAdapter;
import com.example.wingbu.usetimestatistic.adapter.UseTimeAdapter;
import com.example.wingbu.usetimestatistic.domain.UseTimeDataManager;
import com.example.wingbu.usetimestatistic.event.MessageEvent;
import com.example.wingbu.usetimestatistic.utils.DateTransUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


/***
 *  主界面 -- 可以选择日期，然后查询所选日期的统计后的应用使用情况
 *  Created by Wingbu on 2017/8/11.
 */
public class MainActivity extends BaseActivity {

    private LinearLayout       mLlSelectDate;
    private Button             mBtnDate;
    private PopupWindow        mPopupWindow;
    private RecyclerView       mRvSelectDate;

    private RecyclerView       mRecyclerView;
    private UseTimeAdapter     mUseTimeAdapter;

    private ArrayList<String>  mDateList;
    private UseTimeDataManager mUseTimeDataManager;

    private int dayNum = 0;
    private boolean isShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData(dayNum);
        initView();
    }

    @Subscribe
    public void showMessageEvent(MessageEvent event){
        Toast.makeText(getBaseContext(),event.getmMessage(),Toast.LENGTH_SHORT).show();
    }

    private void initView(){
        mLlSelectDate = (LinearLayout) findViewById(R.id.ll_select_date);
        mBtnDate = (Button) findViewById(R.id.tv_date);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_show_statistics);
        showView(dayNum);
    }

    private void initData(int dayNum){
        mDateList = DateTransUtils.getSearchDays();
        mUseTimeDataManager = UseTimeDataManager.getInstance(getApplicationContext());
        mUseTimeDataManager.refreshData(dayNum);
    }

    private void showView(int dayNumber){

        mBtnDate.setText(mDateList.get(dayNumber));
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isShowing) {
                    showPopWindow();
                }
            }
        });

        mUseTimeAdapter = new UseTimeAdapter(this,mUseTimeDataManager.getmPackageInfoListOrderByTime());
        mUseTimeAdapter.setOnItemClickListener(new UseTimeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String pkg) {
                showDetail(pkg);
            }
        });
        mRecyclerView.setAdapter(mUseTimeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showDetail(String pkg){
        Intent i = new Intent();
        i.setClassName(this,"com.example.wingbu.usetimestatistic.ui.UseTimeDetailActivity");
        i.putExtra("type","times");
        i.putExtra("pkg",pkg);
        startActivity(i);
    }

    private void showPopWindow(){
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popuplayout, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mRvSelectDate = contentView.findViewById(R.id.rv_select_date);
        SelectDateAdapter adapter = new SelectDateAdapter(mDateList);
        adapter.setOnItemClickListener(new SelectDateAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mUseTimeDataManager.refreshData(position);
                showView(position);
                mPopupWindow.dismiss();
                isShowing = false;
            }
        });
        mRvSelectDate.setAdapter(adapter);
        mRvSelectDate.setLayoutManager(new LinearLayoutManager(this));

        mPopupWindow.showAsDropDown(mBtnDate);
        isShowing = true;
    }
}
