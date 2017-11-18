package com.example.wingbu.usetimestatistic.file;

import android.util.Log;

import com.example.wingbu.usetimestatistic.utils.DateTransUtils;
import com.example.wingbu.usetimestatistic.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 在本应用的生命周期中调用WriteRecordFileUtils，将记录本应用的events，用于和系统记录的events进行对比
 *
 * Created by Wingbu on 2017/7/18.
 */

public class WriteRecordFileUtils {
    public static final String TAG             = "WriteRecordFileUtils";
    public static final String BASE_FILE_PATH  = "/data/data/com.example.wingbu.usetimestatistic/files/event_log";
    public static final int    MAX_FILE_NUMBER = 7;
    private static      long   currentFileName = 0;

    // =======================================
    // Write  Files
    // =======================================

    public static void write(long timeStamp,String className, int type,long activeTime){

        if(currentFileName == 0){
            currentFileName = getLatestFileName();
        }

        long time = DateTransUtils.getZeroClockTimestamp(timeStamp);
        if( time == currentFileName ){
            writeToFile(time,timeStamp,className, type,activeTime);
        }else if( time > currentFileName ){
            createFile(time);
            writeToFile(time,timeStamp,className, type,activeTime);
        }else {
            Log.i(TAG," WriteRecordFileUtils--write()    写入文件  时间有bug ");
        }
    }

    private static void writeToFile(long fileName,long timeStamp,String className, int type,long activeTime){
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            String filePath = BASE_FILE_PATH + "/" + fileName + ".txt";
            FileWriter writer = new FileWriter(filePath, true);
            String input_str = StringUtils.getInputString(timeStamp,className,type,activeTime);
            writer.write(input_str);
            writer.close();
            Log.i(TAG," WriteRecordFileUtils--writeToFile()  写入文件成功 ");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG," WriteRecordFileUtils--writeToFile()  写入文件错误 "+e.getMessage());
        }
    }

    private static void createFile(long fileName){

        File baseFile = new File(BASE_FILE_PATH );
        if(!baseFile.exists()){
            baseFile.mkdirs();
        }

        File file = new File(BASE_FILE_PATH + "/" + fileName + ".txt");
        if(!file.exists()){
            try
            {
                if(file.createNewFile()){
                    Log.i(TAG, "  WriteRecordFileUtils--createFile()    文件创建成功 : "  + fileName);
                }else {
                    Log.i(TAG, "  WriteRecordFileUtils--createFile()   文件创建失败 : "  + fileName);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "  WriteRecordFileUtils--createFile()   文件创建失败！ : " + e.getMessage());
            }
        }

        //每次新建文件，都会检查是否需要删除多余文件
        deleteRedundantFile();
    }

    private static void deleteRedundantFile(){
        File baseFile = new File(BASE_FILE_PATH);
        String[] files = baseFile.list();

        if(files == null || files.length == 0){
            Log.i(TAG, "  WriteRecordFileUtils--deleteFilePath()     没有可以删除的文件  " );
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
                    Log.i(TAG, "  WriteRecordFileUtils--deleteFilePath()     文件删除成功 : " + fileName);
                }else {
                    Log.i(TAG, "  WriteRecordFileUtils--deleteFilePath()     文件删除失败 : "  + fileName);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "  WriteRecordFileUtils--deleteFilePath()     文件删除失败！ : " + e.getMessage());
            }
        }
    }

    //以时间戳为文件名
    private static long getLatestFileName(){
        try
        {
            long timeStamp = 0;
            File file = new File(BASE_FILE_PATH);
            String[] fileName = file.list();
            if(fileName == null || fileName.length == 0){
                return 0;
            }
            for(int i = 0 ; i < fileName.length ; i++){
                if( timeStamp < Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i])) ){
                    timeStamp = Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i]));
                }
            }
            currentFileName = timeStamp;
            Log.i(TAG, "  WriteRecordFileUtils--getLatestFileName() : 最新写入的文件  " + currentFileName);
            return timeStamp;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "  WriteRecordFileUtils--getLatestFileName()    寻找最新写入的文件失败！ : " + e.getMessage());
        }

        return 0;
    }

    private static long getOldestFileName(){
        try
        {
            long timeStamp = 999999999999999999L;
            File file = new File(BASE_FILE_PATH);
            String[] fileName = file.list();
            if(fileName == null || fileName.length == 0){
                Log.i(TAG, "  WriteRecordFileUtils--getOldestFileName()   没有之前最久写入的文件  " );
                return 0;
            }
            for(int i = 0 ; i < fileName.length ; i++){
                if( timeStamp > Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i]) )){
                    timeStamp = Long.parseLong(StringUtils.getFileNameWithoutSuffix(fileName[i]));
                }
            }
            Log.i(TAG, "  WriteRecordFileUtils--getOldestFileName()    : 之前最久写入的文件  " + timeStamp);
            return timeStamp;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "  WriteRecordFileUtils--getOldestFileName()   寻找之前最久写入的文件失败！ : " + e.getMessage());
        }

        return 0;
    }
}
