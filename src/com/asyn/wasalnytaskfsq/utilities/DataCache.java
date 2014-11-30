package com.asyn.wasalnytaskfsq.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataCache {
	
	private SharedPreferences preferences;
	
	public DataCache(Context context, String name) {
		this.preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
	
	public void cache(String key, String value) {
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}
	
	public String getCachedString(String key) {
		return preferences.getString(key, null);
	}
	
	public void cache(String key, double value) {
		Editor editor = preferences.edit();
		editor.putFloat(key, (float) value);
		editor.apply();
	}
	
	public double getCachedDouble(String key) {
		return preferences.getFloat(key, 0);
	}
	
	public boolean isItCached(String key, String value) {
		if(preferences.getString(key, value) != null)
			return true;
		else
			return false;
	}
	
}
