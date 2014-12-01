package com.asyn.wasalnytaskfsq.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.asyn.wasalnytaskfsq.connections.JsonRetriever;
import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.connections.OnTaskStartedListener;
import com.asyn.wasalnytaskfsq.constants.Keys;
import com.asyn.wasalnytaskfsq.database.VenuesDataSource;
import com.asyn.wasalnytaskfsq.models.Venue;
import com.asyn.wasalnytaskfsq.utilities.JsonTools;

public class NearbyVenues {

	private JsonRetriever jsonRetriever;
	private List<Venue> listOfVenues;
	private VenuesDataSource dataSource;

	public NearbyVenues(Context context, String url,
			OnTaskCompletedListener onTaskCompletedListener) {
		listOfVenues = new ArrayList<Venue>();
		dataSource = new VenuesDataSource(context); // TODO Context Add
		dataSource.open();
		dataSource.destroy();
		jsonRetriever = new JsonRetriever(url, onTaskStartedListener,
				onTaskCompletedListener);
		jsonRetriever.execute();
	}

	/**
	 * 
	 */
	private OnTaskStartedListener onTaskStartedListener = new OnTaskStartedListener() {
		@Override
		public void onTaskStarted(StringBuilder responseData) {
			JSONObject jsonFile = JsonTools
					.getJsonObjectFromResponse(responseData.toString());
			JSONObject response = JsonTools.getJsonObjectFrom(jsonFile,
					Keys._RESPONSE);
			JSONArray venues = JsonTools.getJsonArrayFrom(response,
					Keys.Venues._VENUES);
			parseVenues(venues);
		}
	};

	/**
	 * 
	 * @param venues
	 */
	private void parseVenues(JSONArray venues) {
		for (int i = 0; i < venues.length(); i++) {
			JSONObject venueObject = JsonTools.getJsonObjectFrom(venues, i);
			Venue venue = new Venue();

			venue.setId(JsonTools.getString(venueObject, Keys.Venues._ID)); // getting venue id
			venue.setName(JsonTools.getString(venueObject, Keys.Venues._NAME)); // getting venue name

			/*
			 * Getting Venue:
			 * Address, Latitude, Longitude
			 */
			JSONObject location = JsonTools.getJsonObjectFrom(venueObject,
					Keys.Venues._LOCATION);

			if (JsonTools.has(location, Keys.Venues._ADDRESS)) // Checks if venue have an address
				venue.setAddress(JsonTools.getString(location,
						Keys.Venues._ADDRESS));

			venue.setLatitude(JsonTools.getDouble(location,
					Keys.Venues._LATITUDE));
			venue.setLongtitude(JsonTools.getDouble(location,
					Keys.Venues._LONGITUDE));

			/*
			 * Getting Category
			 * and Icon URL
			 */
			JSONArray categories = JsonTools.getJsonArrayFrom(venueObject, Keys.Venues._CATEGORIES);
			JSONObject category_info = JsonTools.getJsonObjectFrom(categories, Keys.Venues._CATEGORY_INFO);
			
			venue.setCategory(JsonTools.getString(category_info, Keys.Venues._ID)); // Set category id
			
			JSONObject icon = JsonTools.getJsonObjectFrom(category_info, Keys.Venues._ICON);
			String prefix = JsonTools.getString(icon, Keys.Venues._PREFIX);
			String suffix = JsonTools.getString(icon, Keys.Venues._SUFFIX);
			
			venue.setPhotoURL(prefix + Keys.Venues._ICON_SIZE + suffix); // Set photo URL
			
			// Adding venue to listOfVenues
			listOfVenues.add(venue);
			dataSource.storeVenue(venue); // adding venue to database
			Log.v("STORE: ", venue.toString());
		}
	}

	/**
	 * Returns list of all the Venues
	 * 
	 * @return
	 */
	public List<Venue> getVenues() {
		return listOfVenues;
	}

}
