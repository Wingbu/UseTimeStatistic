package com.example.wingbu.usetimestatistic.domain;

import java.util.ArrayList;

/**
 * 统计数据---记录一天当中的统计数据
 *           mStartTimeStamp :统计开始的时间
 *           mEndTimeStamp : 统计结束的时间
 *           mFlag : event获取的数据 和 usage获取的数据 是否匹配的标志位，
 *                      example: "equal" : 统计Usage数据 和 event数据，但是两者大致匹配
 *                               "unequal" : 统计Usage数据 和 event数据，但是两者大致匹配
 *                               "usage" : 仅查询并统计Usage数据，无event统计数据
 *           mPackageInfoListByEvent : 根据event获取的统计数据
 *           mPackageInfoListByUsage : 根据Usage获取的统计数据
 *
 * Created by Wingbu on 2017/7/18.
 */

public class AppUsageDaily {
    private long     mStartTimeStamp;
    private long     mEndTimeStamp;
    private String   mFlag;
    private ArrayList<PackageInfo> mPackageInfoListByEvent;
    private ArrayList<PackageInfo>  mPackageInfoListByUsage;

    public AppUsageDaily(long mStartTimeStamp, long mEndTimeStamp, String mFlag, ArrayList<PackageInfo> mPackageInfoListByEvent, ArrayList<PackageInfo> mPackageInfoListByUsage) {
        this.mStartTimeStamp = mStartTimeStamp;
        this.mEndTimeStamp = mEndTimeStamp;
        this.mFlag = mFlag;
        this.mPackageInfoListByEvent = mPackageInfoListByEvent;
        this.mPackageInfoListByUsage = mPackageInfoListByUsage;
    }

    public long getmStartTimeStamp() {
        return mStartTimeStamp;
    }

    public void setmStartTimeStamp(long mStartTimeStamp) {
        this.mStartTimeStamp = mStartTimeStamp;
    }

    public long getmEndTimeStamp() {
        return mEndTimeStamp;
    }

    public void setmEndTimeStamp(long mEndTimeStamp) {
        this.mEndTimeStamp = mEndTimeStamp;
    }

    public String getmFlag() {
        return mFlag;
    }

    public void setmFlag(String mFlag) {
        this.mFlag = mFlag;
    }

    public ArrayList<PackageInfo> getmPackageInfoListByEvent() {
        return mPackageInfoListByEvent;
    }

    public void setmPackageInfoListByEvent(ArrayList<PackageInfo> mPackageInfoListByEvent) {
        this.mPackageInfoListByEvent = mPackageInfoListByEvent;
    }

    public ArrayList<PackageInfo> getmPackageInfoListByUsage() {
        return mPackageInfoListByUsage;
    }

    public void setmPackageInfoListByUsage(ArrayList<PackageInfo> mPackageInfoListByUsage) {
        this.mPackageInfoListByUsage = mPackageInfoListByUsage;
    }
}
