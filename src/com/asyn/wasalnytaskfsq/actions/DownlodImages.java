package com.asyn.wasalnytaskfsq.actions;

import com.asyn.wasalnytaskfsq.FindPlaces;
import com.asyn.wasalnytaskfsq.connections.JsonRetriever;
import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.connections.OnTaskStartedListener;
import com.asyn.wasalnytaskfsq.models.Venue;

public class DownlodImages {
	
	private static final String API_PHOTO = "https://api.foursquare.com/v2/venues/";
	private static final String PHOTOS_OAUTH = "/photos?oauth_token=" + FindPlaces.getOAuthToken();
	private static final String V_D_LIMIT = "&v=20141127&limit=1";
	
	private JsonRetriever jsonRetriever;
	private Venue venue;
	
	public DownlodImages(Venue venue, OnTaskCompletedListener onTaskCompletedListener) {
		this.venue = venue;
		jsonRetriever = new JsonRetriever(null, null, onTaskCompletedListener);
		jsonRetriever.execute();
	}
	
	
	private OnTaskStartedListener onTaskStartedListener = new OnTaskStartedListener() {
		@Override
		public void onTaskStarted(StringBuilder responseData) {
			
		}
	};
}
