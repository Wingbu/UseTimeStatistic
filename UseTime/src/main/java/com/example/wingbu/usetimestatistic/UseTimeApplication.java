package com.example.wingbu.usetimestatistic;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.wingbu.usetimestatistic.file.WriteRecordFileUtils;
import com.example.wingbu.usetimestatistic.utils.StringUtils;

/**
 * 应用的Application，用于在生命周期中记录相关的"Events"数据
 *
 * Created by Wingbu on 2017/9/13.
 */

public class UseTimeApplication extends Application{

    public static UseTimeApplication sInstance;

    //以下三个属性用于记录上次events的属性
    private Long      lastTime = 0L;
    private String    lastClassName = null;
    private int       lastEventType = 0;

    public static UseTimeApplication getApplication()
    {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UseTimeApplication.sInstance = this;

        //自行记录本应用的event, 在onPause()记录 event （type = 1）,在onResumed记录 event （type = 2）
        //用此event数据和系统记录的event数据对比，用于检测系统数据的有效性
        //对比方法：
        //    步骤一：点击actionbar的写入按钮，将系统数据写入一份到 "/data/data/com.example.wingbu.usetimestatistic/files/event_copy"目录下
        //    步骤二：自行记录本应用的event数据写入到 "/data/data/com.example.wingbu.usetimestatistic/files/event_log"目录下
        //    步骤三：将两个目录下的同名文件取出，使用文本对比软件Beyond Compare 进行对比
        // 一般而言，在不出现闪退和强杀的情况下，两文件记录的数据应当大致相同，仅仅只有时间戳的末几位不一样，一般误差在1秒之内，即大概只有末三位不一致
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
                long activeTime = 0L;
                long time = System.currentTimeMillis();
                String className = activity.getComponentName().getClassName();
                if(lastTime > 0 && lastClassName != null && lastEventType > 0 && lastEventType == 1 && lastClassName.equals(className)){
                    activeTime = time - lastTime;
                }
                Log.i("ActivityLifecycle", StringUtils.getInputString(time,className,2,activeTime));
                WriteRecordFileUtils.write(time,className,2,activeTime);
                resetData(time,className,2);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                long time = System.currentTimeMillis();
                String className = activity.getComponentName().getClassName();
                Log.i("ActivityLifecycle", StringUtils.getInputString(time,className,1,0));
                WriteRecordFileUtils.write(time,className,1,0);
                resetData(time,className,1);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }
        });

    }

    private void resetData(long timeStamp , String className,int type){
        lastTime = timeStamp;
        lastClassName = className;
        lastEventType = type;
    }

}
