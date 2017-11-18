package com.example.wingbu.usetimestatistic.event;

import org.greenrobot.eventbus.EventBus;

/**
 * 消息传递工具（暂未使用）
 *
 * Created by Wingbu on 2017/7/18.
 */

public class MsgEventBus {

    private static EventBus mEventBus;

    public static EventBus getInstance()
    {
        if (mEventBus == null)
        {
            mEventBus = new EventBus();
        }
        return mEventBus;
    }

}
