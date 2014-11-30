package com.asyn.wasalnytaskfsq;

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
	
	public String getCached(String key) {
		return preferences.getString(key, null);
	}
}
