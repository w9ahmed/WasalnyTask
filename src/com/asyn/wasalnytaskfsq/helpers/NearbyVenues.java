package com.asyn.wasalnytaskfsq.helpers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asyn.wasalnytaskfsq.models.Venue;
import com.asyn.wasalnytaskfsq.models.constants.Keys;
import com.asyn.wasalnytaskfsq.utilities.JsonTools;

public class NearbyVenues {
	
	private JsonRetriever asynUpdating;
	private List<Venue> listOfVenues;
	
	public NearbyVenues(String url, OnTaskCompletedListener onTaskCompletedListener) {
		listOfVenues = new ArrayList<Venue>();
		asynUpdating = new JsonRetriever(url, onTaskStartedListener, onTaskCompletedListener);
		asynUpdating.execute();
	}
	
	
	/**
	 * 
	 */
	private OnTaskStartedListener onTaskStartedListener = new OnTaskStartedListener() {
		@Override
		public void onTaskStarted(StringBuilder responseData) {
			JSONObject jsonFile = JsonTools.getJsonObjectFromResponse(responseData.toString());
			JSONObject response = JsonTools.getJsonObjectFrom(jsonFile, Keys._RESPONSE);
			JSONArray venues = JsonTools.getJsonArrayFrom(response, Keys.Venues._VENUES);
			parseVenues(venues);
		}
	};
	
	
	/**
	 * 
	 * @param venues
	 */
	private void parseVenues(JSONArray venues) {
		for(int i = 0; i < venues.length(); i++) {
			JSONObject venueObject = JsonTools.getJsonObjectFrom(venues, i);
			Venue venue = new Venue();
			
			venue.setId(JsonTools.getString(venueObject, Keys.Venues._ID));
			venue.setName(JsonTools.getString(venueObject, Keys.Venues._NAME));
			
			JSONObject location = JsonTools.getJsonObjectFrom(venueObject, Keys.Venues._LOCATION);
			
			if(JsonTools.has(location, Keys.Venues._ADDRESS))
				venue.setAddress(JsonTools.getString(location, Keys.Venues._ADDRESS));
			
			venue.setLatitude(JsonTools.getDouble(location, Keys.Venues._LATITUDE));
			venue.setLongtitude(JsonTools.getDouble(location, Keys.Venues._LONGITUDE));
			
			// Adding venue to listOfVenues
			listOfVenues.add(venue);
		}
	}
	
	
	public List<Venue> getVenues() {
		return listOfVenues;
	}

}
