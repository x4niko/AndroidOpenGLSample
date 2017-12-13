package cn.autoref.androidopenglsample.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by niko on 2017/12/13.
 */
public class AssetsUtil {

    /**
     * 获取assets目录下的文件内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsString(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(nextLine);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            return "";
        }
        return stringBuilder.toString();
    }
}
