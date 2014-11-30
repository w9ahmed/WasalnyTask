package com.asyn.wasalnytaskfsq.connections;

import java.util.HashMap;

import android.graphics.Bitmap;

import com.asyn.wasalnytaskfsq.models.Venue;

public interface OnTaskCompletedListener {
	void onTaskCompleted();
	void onTaskComlpeted(HashMap<String, Bitmap> iconSet);
	void onTaskCompleted(Venue venue, int statusCode);
}
