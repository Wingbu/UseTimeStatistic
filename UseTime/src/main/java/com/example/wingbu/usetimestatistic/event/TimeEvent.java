package com.example.wingbu.usetimestatistic.event;

/**
 * Created by Wingbu on 2017/7/18.
 */

public class TimeEvent {
    private long mStartTime;
    private long mEndTime;

    public TimeEvent(long mStartTime, long mEndTime) {
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

}
