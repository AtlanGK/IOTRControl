package com.autohome.iotrcontrol.util.preference;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SharedPreferenceProvider extends ContentProvider {
    private Map<String, MethodProcess> processerMap = new HashMap(5);
    private SharedPreferenceProvider.MethodProcess methodQueryPid = new SharedPreferenceProvider.MethodProcess() {
        public Bundle process(String arg, Bundle extras) {
            Bundle bundle = new Bundle();
            bundle.putInt("key_result", Process.myPid());
            return bundle;
        }
    };
    private SharedPreferenceProvider.MethodProcess methodQueryValues = new SharedPreferenceProvider.MethodProcess() {
        public Bundle process(String arg, Bundle extras) {
            if (extras == null) {
                throw new IllegalArgumentException("methodQueryValues, extras is null!");
            } else {
                Context ctx = SharedPreferenceProvider.this.getContext();
                if (ctx == null) {
                    throw new IllegalArgumentException("methodQueryValues, ctx is null!");
                } else {
                    String key = extras.getString("key_key");
                    SharedPreferences preferences = ctx.getSharedPreferences(arg, 0);
                    int valueType = extras.getInt("key_value_type");
                    switch(valueType) {
                        case 1:
                            String valuexx = preferences.getString(key, extras.getString("key_value"));
                            extras.putString("key_value", valuexx);
                            return extras;
                        case 2:
                            int valuex = preferences.getInt(key, extras.getInt("key_value"));
                            extras.putInt("key_value", valuex);
                            return extras;
                        case 3:
                            long value = preferences.getLong(key, extras.getLong("key_value"));
                            extras.putLong("key_value", value);
                            return extras;
                        case 4:
                            float valuexxxxx = preferences.getFloat(key, extras.getFloat("key_value"));
                            extras.putFloat("key_value", valuexxxxx);
                            return extras;
                        case 5:
                            boolean valuexxxx = preferences.getBoolean(key, extras.getBoolean("key_value"));
                            extras.putBoolean("key_value", valuexxxx);
                            return extras;
                        case 6:
                            Set<String> valuexxx = preferences.getStringSet(key, (Set)null);
                            extras.putStringArrayList("key_value", valuexxx == null ? null : new ArrayList(valuexxx));
                            return extras;
                        default:
                            throw new IllegalArgumentException("unknown valueType:" + valueType);
                    }
                }
            }
        }
    };
    private SharedPreferenceProvider.MethodProcess methodContainKey = new SharedPreferenceProvider.MethodProcess() {
        public Bundle process(String arg, Bundle extras) {
            if (extras == null) {
                throw new IllegalArgumentException("methodQueryValues, extras is null!");
            } else {
                Context ctx = SharedPreferenceProvider.this.getContext();
                if (ctx == null) {
                    throw new IllegalArgumentException("methodQueryValues, ctx is null!");
                } else {
                    String key = extras.getString("key_key");
                    SharedPreferences preferences = ctx.getSharedPreferences(arg, 0);
                    extras.putBoolean("key_result", preferences.contains(key));
                    return extras;
                }
            }
        }
    };
    private SharedPreferenceProvider.MethodProcess methodEditor = new SharedPreferenceProvider.MethodProcess() {
        public Bundle process(String arg, Bundle extras) {
            if (extras == null) {
                throw new IllegalArgumentException("methodQueryValues, extras is null!");
            } else {
                Context ctx = SharedPreferenceProvider.this.getContext();
                if (ctx == null) {
                    throw new IllegalArgumentException("methodQueryValues, ctx is null!");
                } else {
                    SharedPreferences preferences = ctx.getSharedPreferences(arg, 0);
                    ArrayList<Bundle> ops = extras.getParcelableArrayList("key_result");
                    if (ops == null) {
                        ops = new ArrayList();
                    }

                    SharedPreferences.Editor editor = preferences.edit();
                    Iterator var7 = ops.iterator();

                    while(var7.hasNext()) {
                        Bundle opBundler = (Bundle)var7.next();
                        int opType = opBundler.getInt("key_op_type");
                        switch(opType) {
                            case 2:
                                editor = this.editValue(editor, opBundler);
                                break;
                            case 3:
                                editor = editor.clear();
                                break;
                            case 4:
                                editor = editor.remove(opBundler.getString("key_key"));
                                break;
                            default:
                                throw new IllegalArgumentException("unkonw op type:" + opType);
                        }
                    }

                    int applyOrCommit = extras.getInt("key_op_type");
                    if (applyOrCommit == 6) {
                        editor.apply();
                        return null;
                    } else if (applyOrCommit == 5) {
                        boolean res = editor.commit();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("key_result", res);
                        return bundle;
                    } else {
                        throw new IllegalArgumentException("unknown applyOrCommit:" + applyOrCommit);
                    }
                }
            }
        }

        private SharedPreferences.Editor editValue(SharedPreferences.Editor editor, Bundle opBundle) {
            String key = opBundle.getString("key_key");
            int valueType = opBundle.getInt("key_value_type");
            switch(valueType) {
                case 1:
                    return editor.putString(key, opBundle.getString("key_value"));
                case 2:
                    return editor.putInt(key, opBundle.getInt("key_value"));
                case 3:
                    return editor.putLong(key, opBundle.getLong("key_value"));
                case 4:
                    return editor.putFloat(key, opBundle.getFloat("key_value"));
                case 5:
                    return editor.putBoolean(key, opBundle.getBoolean("key_value"));
                case 6:
                    ArrayList<String> list = opBundle.getStringArrayList("key_value");
                    if (list == null) {
                        return editor.putStringSet(key, (Set)null);
                    }

                    return editor.putStringSet(key, new HashSet(list));
                default:
                    throw new IllegalArgumentException("unknown valueType:" + valueType);
            }
        }
    };

    public SharedPreferenceProvider() {
    }

    public boolean onCreate() {
        this.processerMap.put("method_query_value", this.methodQueryValues);
        this.processerMap.put("method_contain_key", this.methodContainKey);
        this.processerMap.put("method_edit", this.methodEditor);
        this.processerMap.put("method_query_pid", this.methodQueryPid);
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        throw new UnsupportedOperationException();
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException();
    }

    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public Bundle call(String method, String arg, Bundle extras) {
        SharedPreferenceProvider.MethodProcess processer = (SharedPreferenceProvider.MethodProcess)this.processerMap.get(method);
        return processer == null ? null : processer.process(arg, extras);
    }

    public interface MethodProcess {
        Bundle process(String var1, Bundle var2);
    }
}
