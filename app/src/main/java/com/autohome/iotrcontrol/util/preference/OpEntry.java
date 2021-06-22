package com.autohome.iotrcontrol.util.preference;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OpEntry {
    static final int OP_TYPE_GET = 1;
    static final int OP_TYPE_PUT = 2;
    static final int OP_TYPE_CLEAR = 3;
    static final int OP_TYPE_REMOVE = 4;
    static final int OP_TYPE_COMMIT = 5;
    static final int OP_TYPE_APPLY = 6;
    static final int VALUE_TYPE_STRING = 1;
    static final int VALUE_TYPE_INT = 2;
    static final int VALUE_TYPE_LONG = 3;
    static final int VALUE_TYPE_FLOAT = 4;
    static final int VALUE_TYPE_BOOLEAN = 5;
    static final int VALUE_TYPE_STRING_SET = 6;
    static final String KEY_KEY = "key_key";
    static final String KEY_VALUE = "key_value";
    static final String KEY_VALUE_TYPE = "key_value_type";
    static final String KEY_OP_TYPE = "key_op_type";
    private Bundle bundle;

    private OpEntry() {
        this.bundle = new Bundle();
    }

    public OpEntry(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }

        this.bundle = bundle;
    }

    public String getKey() {
        return this.bundle.getString("key_key", (String)null);
    }

    public OpEntry setKey(String key) {
        this.bundle.putString("key_key", key);
        return this;
    }

    public int getValueType() {
        return this.bundle.getInt("key_value_type", 0);
    }

    public OpEntry setValueType(int valueType) {
        this.bundle.putInt("key_value_type", valueType);
        return this;
    }

    public int getOpType() {
        return this.bundle.getInt("key_op_type", 0);
    }

    public OpEntry setOpType(int opType) {
        this.bundle.putInt("key_op_type", opType);
        return this;
    }

    public String getStringValue(String defValue) {
        return this.bundle == null ? defValue : this.bundle.getString("key_value", defValue);
    }

    public OpEntry setStringValue(String value) {
        this.bundle.putInt("key_value_type", 1);
        this.bundle.putString("key_value", value);
        return this;
    }

    public int getIntValue(int defValue) {
        return this.bundle == null ? defValue : this.bundle.getInt("key_value", defValue);
    }

    public OpEntry setIntValue(int value) {
        this.bundle.putInt("key_value_type", 2);
        this.bundle.putInt("key_value", value);
        return this;
    }

    public long getLongValue(long defValue) {
        return this.bundle == null ? defValue : this.bundle.getLong("key_value", defValue);
    }

    public OpEntry setLongValue(long value) {
        this.bundle.putInt("key_value_type", 3);
        this.bundle.putLong("key_value", value);
        return this;
    }

    public float getFloatValue(float defValue) {
        return this.bundle == null ? defValue : this.bundle.getFloat("key_value");
    }

    public OpEntry setFloatValue(float value) {
        this.bundle.putInt("key_value_type", 4);
        this.bundle.putFloat("key_value", value);
        return this;
    }

    public boolean getBooleanValue(boolean defValue) {
        return this.bundle == null ? defValue : this.bundle.getBoolean("key_value", defValue);
    }

    public OpEntry setBooleanValue(boolean value) {
        this.bundle.putInt("key_value_type", 5);
        this.bundle.putBoolean("key_value", value);
        return this;
    }

    public Set<String> getStringSet() {
        ArrayList<String> list = this.bundle.getStringArrayList("key_value");
        return list == null ? null : new HashSet(list);
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public OpEntry setStringSettingsValue(Set<String> value) {
        this.bundle.putInt("key_value_type", 6);
        this.bundle.putStringArrayList("key_value", value == null ? null : new ArrayList(value));
        return this;
    }

    static OpEntry obtainGetOperation(String key) {
        return (new OpEntry()).setKey(key).setOpType(1);
    }

    static OpEntry obtainPutOperation(String key) {
        return (new OpEntry()).setKey(key).setOpType(2);
    }

    static OpEntry obtainRemoveOperation(String key) {
        return (new OpEntry()).setKey(key).setOpType(4);
    }

    static OpEntry obtainClear() {
        return (new OpEntry()).setOpType(3);
    }
}
