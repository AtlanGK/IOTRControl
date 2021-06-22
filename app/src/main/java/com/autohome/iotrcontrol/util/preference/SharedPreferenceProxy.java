package com.autohome.iotrcontrol.util.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;

import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedPreferenceProxy implements SharedPreferences {
    private static String TAG = "SharedPreferenceProxy";
    private static Map<String, SharedPreferenceProxy> sharedPreferenceProxyMap;
    private static AtomicInteger processFlag = new AtomicInteger(0);
    private Context ctx;
    private String preferName;

    private SharedPreferenceProxy(Context ctx, String name) {
        this.ctx = ctx.getApplicationContext();
        this.preferName = name;
    }

    public Map<String, ?> getAll() {
        return new HashMap();
    }

    public String getString(String key, String defValue) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setStringValue(defValue));
        return result == null ? defValue : result.getStringValue(defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setStringSettingsValue(defValues));
        if (result == null) {
            return defValues;
        } else {
            Set<String> set = result.getStringSet();
            return set == null ? defValues : set;
        }
    }

    public int getInt(String key, int defValue) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setIntValue(defValue));
        return result == null ? defValue : result.getIntValue(defValue);
    }

    public long getLong(String key, long defValue) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setLongValue(defValue));
        return result == null ? defValue : result.getLongValue(defValue);
    }

    public float getFloat(String key, float defValue) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setFloatValue(defValue));
        return result == null ? defValue : result.getFloatValue(defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        OpEntry result = this.getResult(OpEntry.obtainGetOperation(key).setBooleanValue(defValue));
        return result == null ? defValue : result.getBooleanValue(defValue);
    }

    public boolean contains(String key) {
        Bundle input = new Bundle();
        input.putString("key_key", key);

        try {
            Bundle res = this.ctx.getContentResolver().call(AHPreferenceUtil.URI, "method_contain_key", this.preferName, input);
            return res.getBoolean("key_result");
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public SharedPreferences.Editor edit() {
        return new SharedPreferenceProxy.EditorImpl();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }

    private OpEntry getResult(OpEntry input) {
        try {
            Bundle res = this.ctx.getContentResolver().call(AHPreferenceUtil.URI, "method_query_value", this.preferName, input.getBundle());
            return new OpEntry(res);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static SharedPreferences getSharedPreferences(Context ctx, String preferName) {
        if (processFlag.get() == 0) {
            try {
                Bundle bundle = ctx.getContentResolver().call(AHPreferenceUtil.URI, "method_query_pid", "", (Bundle)null);
                int pid = 0;
                if (bundle != null) {
                    pid = bundle.getInt("key_result");
                }

                if (pid == 0) {
                    return getFromLocalProcess(ctx, preferName);
                }

                processFlag.set(Process.myPid() == pid ? 1 : -1);
            } catch (Exception var4) {
                processFlag.set(1);
            }

            return getSharedPreferences(ctx, preferName);
        } else {
            return processFlag.get() > 0 ? getFromLocalProcess(ctx, preferName) : getFromRemoteProcess(ctx, preferName);
        }
    }

    private static SharedPreferences getFromRemoteProcess(Context ctx, String preferName) {
        Class var2 = SharedPreferenceProxy.class;
        synchronized(SharedPreferenceProxy.class) {
            if (sharedPreferenceProxyMap == null) {
                sharedPreferenceProxyMap = new HashMap();
            }

            SharedPreferenceProxy preferenceProxy = (SharedPreferenceProxy)sharedPreferenceProxyMap.get(preferName);
            if (preferenceProxy == null) {
                preferenceProxy = new SharedPreferenceProxy(ctx.getApplicationContext(), preferName);
                sharedPreferenceProxyMap.put(preferName, preferenceProxy);
            }

            LogUtil.d(TAG, "【子进程】通过主进程代理获取-SharedPreferences");
            return preferenceProxy;
        }
    }

    private static SharedPreferences getFromLocalProcess(Context ctx, String preferName) {
        LogUtil.d(TAG, "【主进程】直接获取-SharedPreferences");
        return ctx.getSharedPreferences(preferName, 0);
    }

    public class EditorImpl implements SharedPreferences.Editor {
        private ArrayList<OpEntry> mModified = new ArrayList();

        public EditorImpl() {
        }

        public SharedPreferences.Editor putString(String key, String value) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setStringValue(value);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setStringSettingsValue(values);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor putInt(String key, int value) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setIntValue(value);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor putLong(String key, long value) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setLongValue(value);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor putFloat(String key, float value) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setFloatValue(value);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            OpEntry entry = OpEntry.obtainPutOperation(key).setBooleanValue(value);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor remove(String key) {
            OpEntry entry = OpEntry.obtainRemoveOperation(key);
            return this.addOps(entry);
        }

        public SharedPreferences.Editor clear() {
            return this.addOps(OpEntry.obtainClear());
        }

        public boolean commit() {
            Bundle input = new Bundle();
            input.putParcelableArrayList("key_result", this.convertBundleList());
            input.putInt("key_op_type", 5);
            Bundle res = null;

            try {
                res = SharedPreferenceProxy.this.ctx.getContentResolver().call(AHPreferenceUtil.URI, "method_edit", SharedPreferenceProxy.this.preferName, input);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return res == null ? false : res.getBoolean("key_result", false);
        }

        public void apply() {
            Bundle intput = new Bundle();
            intput.putParcelableArrayList("key_result", this.convertBundleList());
            intput.putInt("key_op_type", 6);

            try {
                SharedPreferenceProxy.this.ctx.getContentResolver().call(AHPreferenceUtil.URI, "method_edit", SharedPreferenceProxy.this.preferName, intput);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }

        private SharedPreferences.Editor addOps(OpEntry op) {
            synchronized(this) {
                this.mModified.add(op);
                return this;
            }
        }

        private ArrayList<Bundle> convertBundleList() {
            synchronized(this) {
                ArrayList<Bundle> bundleList = new ArrayList(this.mModified.size());
                Iterator var3 = this.mModified.iterator();

                while(var3.hasNext()) {
                    OpEntry entry = (OpEntry)var3.next();
                    bundleList.add(entry.getBundle());
                }

                return bundleList;
            }
        }
    }
}
