package com.ajiani.maidahui.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ajiani.maidahui.activity.MyApp;

import java.util.Set;

public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "myda_date";

    /**
     * 保存数据
     * @param context
     * @param key
     * @param object
     */

    public static void put(Context context, String key, Object object) {
        SharedPreferences.Editor editor = getEditor(context);

        if (object instanceof String) {
            Log.i("tag", "puasdast: ssddasdasdasdasfsasdasdasdasdasd");
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        }

        else {

            editor.putString(key, object.toString());
        }

        editor.commit();

    }

    /**
     * 获取数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */

    public static Object get(Context context, String key, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }

        return null;
    }

    /**
     * remove key
     * @param context
     * @param key
     */
    public static void remove(Context context,String key){
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        editor.commit();
    }

    /**
     * 判断是否包含key
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return  sp.contains(key);
    }

    /**
     * 清空数据
     * @param context
     */
    public static void clear(Context context){
        SharedPreferences.Editor editor  = getEditor(context);
        editor.clear();
        editor.commit();

    }



    public static SharedPreferences.Editor getEditor(Context context){
        if(context!=null){
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            return sp.edit();
        }else{
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MyApp.getApp().MODE_PRIVATE);
            return sp.edit();
        }

    }


}
