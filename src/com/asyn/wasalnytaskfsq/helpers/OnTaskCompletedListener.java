package com.asyn.wasalnytaskfsq.helpers;

import com.asyn.wasalnytaskfsq.models.Venue;

public interface OnTaskCompletedListener {
	void onTaskCompleted();
	void onTaskCompleted(Venue venue, int statusCode);
}
