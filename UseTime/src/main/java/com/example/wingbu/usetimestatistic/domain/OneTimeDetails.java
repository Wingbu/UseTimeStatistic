package com.example.wingbu.usetimestatistic.domain;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.os.Build;

import com.example.wingbu.usetimestatistic.utils.DateTransUtils;

import java.util.ArrayList;

/**
 * 记录打开一次应用的时候，其中的详情，包括这次打开的应用名，使用时长，以及打开了哪些activity，及各个activity的使用详情
 *
 * Created by Wingbu on 2017/7/18.
 */

public class OneTimeDetails {
    private String                        pkgName;
    private long                          useTime;
    private ArrayList<UsageEvents.Event>  OneTimeDetailEventList;

    public OneTimeDetails(String pkg,long useTime, ArrayList<UsageEvents.Event> oneTimeDetailList) {
        this.pkgName = pkg;
        this.useTime = useTime;
        OneTimeDetailEventList = oneTimeDetailList;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public ArrayList<UsageEvents.Event> getOneTimeDetailEventList() {
        return OneTimeDetailEventList;
    }

    public void setOneTimeDetailEventList(ArrayList<UsageEvents.Event> oneTimeDetailEventList) {
        OneTimeDetailEventList = oneTimeDetailEventList;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public String getStartTime(){
        String startTime = null;
        if(OneTimeDetailEventList.size() > 0){
            //startTime = DateUtils.formatSameDayTime(OneTimeDetailEventList.get(0).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString();
            startTime = DateTransUtils.stampToDate(OneTimeDetailEventList.get(0).getTimeStamp());
        }
        return startTime;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public String getStopTime(){
        String stopTime = null;
        if(OneTimeDetailEventList.size() > 0){
            //stopTime = DateUtils.formatSameDayTime(OneTimeDetailEventList.get(OneTimeDetailEventList.size()-1).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString();
            stopTime = DateTransUtils.stampToDate(OneTimeDetailEventList.get(OneTimeDetailEventList.size()-1).getTimeStamp());
        }
        return stopTime;
    }
}
