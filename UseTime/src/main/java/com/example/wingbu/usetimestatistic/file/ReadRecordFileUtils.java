package com.example.wingbu.usetimestatistic.file;

import android.util.Log;

import com.example.wingbu.usetimestatistic.utils.DateTransUtils;
import com.example.wingbu.usetimestatistic.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Wingbu on 2017/7/18.
 */

public class ReadRecordFileUtils {

    public static final String TAG             = "EventUtils";

    // =======================================
    // Read  Files
    // =======================================

    public static long getRecordStartTime(String baseFilePath,long timeStamp){
        return StringUtils.getTimeStampFromString(getFirstStringLines(baseFilePath, timeStamp));
    }

    public static long getRecordEndTime(String baseFilePath,long timeStamp){

        ArrayList<String> list = getAllStringLines(baseFilePath, timeStamp);

        if(list == null || list.size() < 1){
            return 0;
        }

        Log.i(TAG,"ReadRecordFileUtils--getRecordEndTime()   以行为单位读取文件内容，读最后一行："+list.get(list.size()-1));
        if(list.get(list.size()-1) == null){
            Log.i(TAG,"ReadRecordFileUtils--getRecordEndTime()   以行为单位读取文件内容，读倒数第二行："+list.get(list.size()-2));
            return StringUtils.getTimeStampFromString(list.get(list.size()-2));
        }
        return StringUtils.getTimeStampFromString(list.get(list.size()-1));
    }

    public static ArrayList<String> getAllStringLines(String baseFilePath,long timeStamp){
        long fileName = DateTransUtils.getZeroClockTimestamp(timeStamp);
        File file = new File(baseFilePath + "/" + fileName + ".txt");

        if(!file.exists()){
            Log.i(TAG,"读取RecordFile文件内容时，文件不存在");
            return null;
        }

        BufferedReader reader = null;
        String line = null;
        ArrayList<String> stringsArray = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            // 一次读入一行，直到读入null为文件结束
            while ( ( line = reader.readLine()) != null) {
                Log.i("reader","      "+line);
                stringsArray.add(line) ;
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return stringsArray;
    }

    public static String getFirstStringLines(String baseFilePath,long timeStamp){
        long fileName = DateTransUtils.getZeroClockTimestamp(timeStamp);
        File file = new File(baseFilePath + "/" + fileName + ".txt");

        if(!file.exists()){
            Log.i(TAG,"读取RecordFile文件内容时，文件不存在");
            return null;
        }

        BufferedReader reader = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if(line == 1){
                    break;
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        Log.i(TAG,"ReadRecordFileUtils--getRecordStartTime()  以行为单位读取文件内容，读第一行："+tempString);
        return tempString;
    }

    public static String[] getAllFileNames(){
        File file = new File(WriteRecordFileUtils.BASE_FILE_PATH);
        return file.list();
    }
}
