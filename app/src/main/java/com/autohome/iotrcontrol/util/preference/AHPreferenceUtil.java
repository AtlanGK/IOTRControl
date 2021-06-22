package com.autohome.iotrcontrol.util.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class AHPreferenceUtil {
    public static final String METHOD_CONTAIN_KEY = "method_contain_key";
    public static String AUTHORITY = "com.autohome.commontools.preference";
    public static final Uri URI;
    public static final String METHOD_QUERY_VALUE = "method_query_value";
    public static final String METHOD_EIDIT_VALUE = "method_edit";
    public static final String METHOD_QUERY_PID = "method_query_pid";
    public static final String KEY_VALUES = "key_result";
    public static final Uri sContentCreate;
    public static final Uri sContentChanged;

    public AHPreferenceUtil() {
    }

    public static SharedPreferences getSharedPreference(Context ctx, String preferName) {
        return SharedPreferenceProxy.getSharedPreferences(ctx, preferName);
    }

    static {
        URI = Uri.parse("content://" + AUTHORITY);
        sContentCreate = Uri.withAppendedPath(URI, "create");
        sContentChanged = Uri.withAppendedPath(URI, "changed");
    }
}
