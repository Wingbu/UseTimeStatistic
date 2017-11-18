package com.example.wingbu.usetimestatistic.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wingbu.usetimestatistic.R;
import com.example.wingbu.usetimestatistic.domain.UseTimeDataManager;
import com.example.wingbu.usetimestatistic.file.EventCopyToFileUtils;
import com.example.wingbu.usetimestatistic.file.ReadRecordFileUtils;
import com.example.wingbu.usetimestatistic.file.WriteRecordFileUtils;
import com.example.wingbu.usetimestatistic.utils.DateTransUtils;

/**
 * Created by Wingbu on 2017/9/13.
 */

public class BaseActivity extends Activity{

    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar(){
        mActionBar = getActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            mActionBar.setCustomView(R.layout.actionbar_custom);//设置自定义的布局：actionbar_custom
            TextView tv_action_title = mActionBar.getCustomView().findViewById(R.id.action_bar_title);
            tv_action_title.setText(R.string.action_bar_title_1);

            ImageView iv_setting = mActionBar.getCustomView().findViewById(R.id.iv_setting);
            iv_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jumpToSystemPermissionActivity();
                }
            });

            ImageView iv_write = mActionBar.getCustomView().findViewById(R.id.iv_write);
            iv_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    copyEventsToFile(UseTimeDataManager.getInstance(getApplicationContext()).getmDayNum());
                }
            });

            ImageView iv_content = mActionBar.getCustomView().findViewById(R.id.iv_content);
            iv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetail("all");
                }
            });
        }
    }

    private void jumpToSystemPermissionActivity(){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    private void showDetail(String pkg){
        Intent i = new Intent();
        i.setClassName(this,"com.example.wingbu.usetimestatistic.ui.UseTimeDetailActivity");
        i.putExtra("type","times");
        i.putExtra("pkg",pkg);
        startActivity(i);
    }

    private void copyEventsToFile(int dayNumber){
        long endTime = 0,startTime = 0;
        long time = System.currentTimeMillis() - dayNumber * DateTransUtils.DAY_IN_MILLIS;
        startTime = ReadRecordFileUtils.getRecordStartTime(WriteRecordFileUtils.BASE_FILE_PATH,time);
        endTime = ReadRecordFileUtils.getRecordEndTime(WriteRecordFileUtils.BASE_FILE_PATH,time) ;

        Toast.makeText(this,"已将系统数据写入本地文件",Toast.LENGTH_SHORT).show();
        Log.i("BaseActivity"," BaseActivity--copyEventsToFile()    startTime = " + startTime + "  endTime = " + endTime);

        EventCopyToFileUtils.write(this,startTime-1000,endTime);
    }

    protected void setActionBarTitle(String title){
        if (mActionBar != null) {
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            mActionBar.setCustomView(R.layout.actionbar_custom);//设置自定义的布局：actionbar_custom
            TextView tv_action_title = mActionBar.getCustomView().findViewById(R.id.action_bar_title);
            tv_action_title.setText(title);
        }
    }

    protected void setActionBarTitle(int stringId){
        setActionBarTitle(getString(stringId));
    }
}
