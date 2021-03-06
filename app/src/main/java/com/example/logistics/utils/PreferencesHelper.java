package com.example.logistics.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreferences临时库方法
 * @author kenmy
 *
 */
public class PreferencesHelper {
	
	public final static String shareName = "sp";
	
	private Context context = null;
	private String name = "";
	private SharedPreferences preferences = null;

	public PreferencesHelper(Context ctx, String name) {
		context = ctx;
		if(name == null || name.length() == 0)
			this.name = shareName;
		else
			this.name = name;
		preferences = getPerferences();
	}

	private SharedPreferences getPerferences() {
		SharedPreferences per = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return per;
	}

	public boolean setString(String key, String val) {
		return preferences.edit().putString(key, val).commit();
	}

	public String getString(String key, String defaultValue) {
		return preferences.getString(key, defaultValue);
	}

	public boolean setBoolean(String key, boolean val) {
		return preferences.edit().putBoolean(key, val).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return preferences.getBoolean(key, defaultValue);
	}

	public boolean setFloat(String key, float val) {
		return preferences.edit().putFloat(key, val).commit();
	}

	public float getFloat(String key, float defaultValue) {
		return preferences.getFloat(key, defaultValue);
	}

	public boolean setInt(String key, int val) {
		return preferences.edit().putInt(key, val).commit();
	}

	public int getInt(String key, int defaultValue) {
		return preferences.getInt(key, defaultValue);
	}

	public boolean setLong(String key, long val) {
		return preferences.edit().putLong(key, val).commit();
	}

	public long getLong(String key, long defaultValue) {
		return preferences.getLong(key, defaultValue);
	}
}
