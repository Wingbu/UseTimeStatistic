package com.example.wingbu.usetimestatistic.utils;

import android.util.Log;

/**
 * Created by Wingbu on 2017/7/18.
 */

public class StringUtils {
    public static final String TAG             = "StringUtils";

    //按行写入文件的字符串，格式如下：
    public static String getInputString(long timeStamp,String className, int type,long activeTime){
        String input = null;
        if(activeTime > 0){
            input = timeStamp+"  ("+DateTransUtils.stampToDate(timeStamp)+")  " + className+"  " +type+ "  " + activeTime +"\n";
        }else {
            input = timeStamp+"  ("+DateTransUtils.stampToDate(timeStamp)+")  " + className+"  " +type +"\n";
        }

        Log.i("StringUtils","  input : "+input);
        return input;
    }

    //截取文件名，去除后缀
    public static String getFileNameWithoutSuffix(String fileName){
        String[] fileNameSplited = fileName.split("\\.");
        if(fileNameSplited != null && fileNameSplited.length > 1){
            Log.i(TAG, "  StringUtils-- : 最新写入的文件  去除后缀  " + fileNameSplited[0]);
            return fileNameSplited[0];
        }
        return "-1";
    }

    //将按行写入文件的字符串，解析出时间戳
    public static long getTimeStampFromString(String record){
        if(record == null ){
            return 0;
        }
        String[] temp = record.split("  ");
        if( temp == null || temp.length <= 0 ){
            return 0;
        }
        Log.i(TAG, "  StringUtils--getTimeStampFromString : 解析出时间戳  " + temp[0]);
        return Long.parseLong(temp[0]);
    }
}
