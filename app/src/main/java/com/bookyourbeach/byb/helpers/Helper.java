package com.bookyourbeach.byb.helpers;

import android.content.Context;
import android.content.SharedPreferences;
          // Code by TechyaSoft
public class Helper {

    static String SHARED_PREFERENCES = "sharedPreferences";
    public static String VALUE = "value";

    public static void setCounter(Context context, int val){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(VALUE, val);
        editor.apply();
    }

    public static int getCounter(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(VALUE,0);
    }
}
