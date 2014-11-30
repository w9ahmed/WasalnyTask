package com.asyn.wasalnytaskfsq.connections;

import java.util.HashMap;

import android.graphics.drawable.Drawable;

import com.asyn.wasalnytaskfsq.models.Venue;

public interface OnTaskCompletedListener {
	void onTaskCompleted();
	void onTaskComlpeted(HashMap<String, Drawable> iconSet);
	void onTaskCompleted(Venue venue, int statusCode);
}
