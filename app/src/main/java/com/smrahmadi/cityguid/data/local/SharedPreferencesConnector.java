package com.smrahmadi.cityguid.data.local;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesConnector {

    private static final String PREF_NAME = "PREFERENCES";
    private static final int MODE = Context.MODE_PRIVATE;

    private Context context;

    public SharedPreferencesConnector(Context context) {
        this.context = context;
    }

    public void writeBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public boolean readBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public void writeInteger(String key, int value) {
        getEditor().putInt(key, value).commit();
    }

    public int readInteger(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public void writeString(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public String readString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public void writeLong(String key, long value) {
        getEditor().putLong(key, value).commit();
    }

    public long readLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    private android.content.SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    private Editor getEditor() {
        return getPreferences().edit();
    }

    public boolean exist(String key) {
        return getPreferences().contains(key);
    }

    public void delete(String key) {
        getPreferences().edit().remove(key).apply();
    }
}
