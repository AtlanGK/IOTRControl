package com.autohome.iotrcontrol.util;

import android.content.SharedPreferences;

import com.autohome.iotrcontrol.MyApplication;
import com.autohome.iotrcontrol.util.preference.AHPreferenceUtil;

public class SpHelper {
    public static final String IOTRCONTROL = "iotrcontrol";
    public static final String ACTIVITY_ZHUTIBEANS = "activity_zhutibeans";
    private static SharedPreferences mySharedPreferences = AHPreferenceUtil.getSharedPreference(MyApplication.getContext(), IOTRCONTROL);

    public static String getString(String key) {
        return mySharedPreferences.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return mySharedPreferences.getString(key, defaultValue);
    }

    public static boolean commitString(String key, String value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static int getInt(String key) {
        return mySharedPreferences.getInt(key, 1);
    }

    public static float getFloat(String key, float defaultValue) {
        return mySharedPreferences.getFloat(key, defaultValue);
    }

    public static boolean commitFloat(String key, float value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static String getDouble(String key) {
        return mySharedPreferences.getString(key, "0.00");
    }

    public static boolean commitDouble(String key, double value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, String.valueOf(value));
        return editor.commit();
    }

    public static int getInt(String key, int defaultValue) {
        return mySharedPreferences.getInt(key, defaultValue);
    }

    public static boolean commitInt(String key, int value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    // public static boolean getBoolean(String key) {
    // return mySharedPreferences.getBoolean(key, false);
    // }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mySharedPreferences.getBoolean(key, defaultValue);
    }

    public static boolean commitBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static long getLong(String key, long defaultValue) {
        return mySharedPreferences.getLong(key, defaultValue);
    }

    public static boolean commitLong(String key, long value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean contains(String key) {
        return mySharedPreferences.contains(key);
    }

    public static boolean remove(String key) {
        return mySharedPreferences.edit().remove(key).commit();
    }

}
