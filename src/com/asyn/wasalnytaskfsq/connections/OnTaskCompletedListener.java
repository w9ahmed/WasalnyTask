package com.asyn.wasalnytaskfsq.connections;

import com.asyn.wasalnytaskfsq.models.Venue;

public interface OnTaskCompletedListener {
	void onTaskCompleted();
	void onTaskCompleted(Venue venue, int statusCode);
}
