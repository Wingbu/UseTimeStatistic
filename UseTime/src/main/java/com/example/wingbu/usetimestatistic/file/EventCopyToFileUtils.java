package com.example.wingbu.usetimestatistic.file;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.wingbu.usetimestatistic.utils.DateTransUtils;
import com.example.wingbu.usetimestatistic.utils.EventUtils;
import com.example.wingbu.usetimestatistic.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 将系统记录的events数据取出，按照一定的格式记录于文件中
 *
 * Created by Wingbu on 2017/7/18.
 */

public class EventCopyToFileUtils {
    public  static final String TAG             = "EventUtils";
    public  static final String BASE_FILE_PATH  = "/data/data/com.example.wingbu.usetimestatistic/files/event_copy";
    public  static final int    MAX_FILE_NUMBER = 7;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void write(Context context, long startTime, long endTime){

        ArrayList<UsageEvents.Event> eventList = EventUtils.getEventList(context, startTime, endTime);

        if(eventList == null || eventList.size() == 0){
            return;
        }

        long fileName = DateTransUtils.getZeroClockTimestamp(startTime);
        String filePath = BASE_FILE_PATH + "/" + fileName + ".txt";
        try {
            checkFile(filePath);

            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(filePath, true);

            UsageEvents.Event lastEvent = null;

            for (int i = 0 ; i < eventList.size() ; i++){
                if(context.getPackageName().equals(eventList.get(i).getPackageName())){
                    Log.i(TAG,"   "+eventList.get(i).getClassName());
                    UsageEvents.Event thisEvent = eventList.get(i);
                    if(lastEvent != null && lastEvent.getEventType() == 1 && thisEvent.getEventType() == 2 && lastEvent.getClassName().equals(thisEvent.getClassName())){
                        writer.write(StringUtils.getInputString(thisEvent.getTimeStamp(),thisEvent.getClassName(),thisEvent.getEventType(),thisEvent.getTimeStamp()-lastEvent.getTimeStamp()));
                    }else {
                        writer.write(StringUtils.getInputString(thisEvent.getTimeStamp(),thisEvent.getClassName(),thisEvent.getEventType(),0));
                    }
                    lastEvent = thisEvent;
                }
            }
            writer.close();
            Log.i(TAG," WriteRecordFileUtils--writeToFile()  写入文件成功 " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFile(String filePath){
        File baseFile = new File(BASE_FILE_PATH );
        if(!baseFile.exists()){
            baseFile.mkdirs();
        }

        File file = new File(filePath);
        if(!file.exists()){
            //如果文件不存在，则创建文件
            try
            {
                if(file.createNewFile()){
                    Log.i(TAG, "  EventCopyToFileUtils--checkFile()    文件创建成功 : "  + filePath);
                }else {
                    Log.i(TAG, "  EventCopyToFileUtils--checkFile()   文件创建失败 : "  + filePath);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "  EventCopyToFileUtils--checkFile()   文件创建失败！ : " + e.getMessage());
            }
        }else {
            //如果文件已经存在，则清空文件,以便重新写入
            try {
                // 打开一个写文件器，构造函数中的第二个参数false表示以覆盖形式写文件
                FileWriter writer = new FileWriter(filePath, false);
                writer.write("");
                writer.close();
                Log.i(TAG, "  EventCopyToFileUtils--checkFile()   文件已经存在，则清空文件 " );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //每次新建文件，都会检查是否需要删除多余文件
        deleteRedundantFile();
    }

    private static void deleteRedundantFile(){
        File baseFile = new File(BASE_FILE_PATH);
        String[] files = baseFile.list();

        if(files == null || files.length == 0){
            Log.i(TAG, "  EventCopyToFileUtils--deleteRedundantFile()     没有可以删除的文件  " );
            return ;
        }
        if(files.length <= MAX_FILE_NUMBER){
            return;
        }

        long fileName = getOldestFileName();
        File file = new File(BASE_FILE_PATH + "/" + fileName + ".txt");
        if(file.exists()){
            try
            {
                if(file.delete()){
                    Log.i(TAG, "  EventCopyToFileUtils--deleteRedundantFile()     文件删除成功 : " + fileName);
                }else {
                    Log.i(TAG, "  EventCopyToFileUtils--deleteRedundantFile()     文件删除失败 : "  + fileName);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "  EventCopyToFileUtils--deleteRedundantFile()     文件删除失败！ : " + e.getMessage());
            }
        }
    }

    private static long getOldestFileName(){
        try
        {
            long timeStamp = 999999999999999999L;
            File file = new File(BASE_FILE_PATH);
            String[] fileName = file.list();
            if(fileName == null || fileName.length == 0){
                Log.i(TAG, "  EventCopyToFileUtils--getOldestFileName()   没有之前最久写入的文件  " );
                return 0;
            }
            for(int i = 0 ; i < fileName.length ; i++){
                if( timeStamp > Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i])) ){
                    timeStamp = Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i]));
                }
            }
            Log.i(TAG, "  EventCopyToFileUtils--getOldestFileName()    : 之前最久写入的文件  " + timeStamp);
            return timeStamp;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "  EventCopyToFileUtils--getOldestFileName()   寻找之前最久写入的文件失败！ : " + e.getMessage());
        }

        return 0;
    }
}
