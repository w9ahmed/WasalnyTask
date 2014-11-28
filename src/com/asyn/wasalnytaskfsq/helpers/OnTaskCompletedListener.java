package com.asyn.wasalnytaskfsq.helpers;

import com.asyn.wasalnytaskfsq.models.Venue;

public interface OnTaskCompletedListener {
	void onTaskTaskCompleted();
	void onTaskTaskCompleted(Venue venue, int statusCode);
}
