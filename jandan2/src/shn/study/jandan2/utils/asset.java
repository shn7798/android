package shn.study.jandan2.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shn7798 on 15-11-1.
 */
public class asset {
    public static InputStream getInputSteamFromAssets(Context context, String fileName){
        try{
            return context.getResources().getAssets().open(fileName);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName), "utf-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += (line + "\n");
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
