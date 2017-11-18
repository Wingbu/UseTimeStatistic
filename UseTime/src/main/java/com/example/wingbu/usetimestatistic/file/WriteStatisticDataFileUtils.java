package com.example.wingbu.usetimestatistic.file;

import android.util.Log;

import com.example.wingbu.usetimestatistic.domain.AppUsageDaily;
import com.example.wingbu.usetimestatistic.utils.DateTransUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Wingbu on 2017/7/18.
 */

public class WriteStatisticDataFileUtils {
    public static final String TAG             = "WriteStatisticDataFileUtils";
    public static final String BASE_FILE_PATH  = "/data/data/com.example.wingbu.usetimestatistic/files/statics";
    public static final String FILE_NAME       = "current.txt";
    public static final long   MAX_FILE_SIZE   = 10 * 1024 * 1024;

    // =======================================
    // Write  Files
    // =======================================

    public static void write(ArrayList<AppUsageDaily> AppUsageList){
        checkFile();
        writeToFile(AppUsageList);
    }

    private static void writeToFile(ArrayList<AppUsageDaily> appUsageList){
        File file = new File(BASE_FILE_PATH + "/" + FILE_NAME);
        if(!file.exists()){
            createFile(file);
        }

        Log.i(TAG," WriteStatisticDataFileUtils--writeToFile()  写入文件天数 :" + appUsageList.size());
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(file, true);
            long currentTime = System.currentTimeMillis();
            writer.write("本次写入文件的时间为 : "+ currentTime + "   "+ DateTransUtils.stampToDate(currentTime)+"\n");

            for (int i = 0 ; i < appUsageList.size() ; i++){
                writeAppUsage(writer,appUsageList.get(i));
            }
            writer.close();
            Log.i(TAG," WriteStatisticDataFileUtils--writeToFile()  写入文件成功 " );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeAppUsage(FileWriter writer,AppUsageDaily appUsageDaily){
        try {
            writer.write("当前数据所属日期 : "+appUsageDaily.getmStartTimeStamp() + "   "+DateTransUtils.stampToDate(appUsageDaily.getmStartTimeStamp())+"\n");
            writer.write("当前数据Flag : "+appUsageDaily.getmFlag()+"\n");
            if(appUsageDaily.getmPackageInfoListByEvent() != null && appUsageDaily.getmPackageInfoListByEvent().size() > 0){
                for ( int i = 0 ; i < appUsageDaily.getmPackageInfoListByEvent().size() ; i++){
                    writer.write("event :  "+appUsageDaily.getmPackageInfoListByEvent().get(i).getmPackageName() + "  使用次数:"+appUsageDaily.getmPackageInfoListByEvent().get(i).getmUsedCount()+ "    使用时长:"+appUsageDaily.getmPackageInfoListByEvent().get(i).getmUsedTime()+"\n");
                }
            }
            if(appUsageDaily.getmPackageInfoListByUsage() != null && appUsageDaily.getmPackageInfoListByUsage().size() > 0){
                for ( int j = 0 ; j < appUsageDaily.getmPackageInfoListByUsage().size() ; j++){
                    writer.write("usage :  "+appUsageDaily.getmPackageInfoListByUsage().get(j).getmPackageName() + "  使用次数:"+appUsageDaily.getmPackageInfoListByUsage().get(j).getmUsedCount()+ "    使用时长:"+appUsageDaily.getmPackageInfoListByUsage().get(j).getmUsedTime()+"\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFile(){
        File file = new File(BASE_FILE_PATH + "/" + FILE_NAME);
        if(file.exists()){
            if(file.length() > MAX_FILE_SIZE){
                File newFile = new File(BASE_FILE_PATH + "/" +System.currentTimeMillis()+"-rename.txt");
                file.renameTo(newFile);
            }
        }else {
            createFile(file);
        }
    }

    private static void createFile(File file){
        try
        {
            if(file.createNewFile()){
                Log.i(TAG, "  WriteStatisticDataFileUtils--checkFile()    文件创建成功 : "  );
            }else {
                Log.i(TAG, "  WriteStatisticDataFileUtils--checkFile()   文件创建失败 : "  );
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "  WriteStatisticDataFileUtils--checkFile()   文件创建失败！ : " + e.getMessage());
        }
    }
}
