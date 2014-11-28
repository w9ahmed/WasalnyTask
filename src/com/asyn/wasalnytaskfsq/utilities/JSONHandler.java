package com.asyn.wasalnytaskfsq.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.asyn.wasalnytaskfsq.helpers.ImageRetriever;
import com.asyn.wasalnytaskfsq.models.Venue;

public final class JSONHandler {
	

	private static final String _RESPONSE = "response";
	private static final String _VENUES = "venues";

	private static final String _ID = "id";
	private static final String _NAME = "name";

	private static final String _LOCATION = "location";
	private static final String _ADDRESS = "address";
	private static final String _LATITUDE = "lat";
	private static final String _LONGITUDE = "lng";

	private JSONArray venues;
	private List<Venue> listOfVenues;

	public JSONHandler(StringBuilder responseData) {
		try {
			JSONObject jsonResponse = new JSONObject(responseData.toString());
			JSONObject response = jsonResponse.getJSONObject(_RESPONSE);
			venues = response.getJSONArray(_VENUES);
			listOfVenues = new ArrayList<Venue>();
			parseVenues();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseVenues() {
		for (int i = 0; i < venues.length(); i++) {
			try {
				JSONObject venueObject = venues.getJSONObject(i);
				Venue venue = new Venue();
				
				venue.setId(venueObject.getString(_ID));
				venue.setName(venueObject.getString(_NAME));
				
				JSONObject location = getInnerObject(venueObject, _LOCATION);
				/**
				 * Checks if the ADDRESS Object is available 
				 */
				if(location.has(_ADDRESS))
					venue.setAddress(location.getString(_ADDRESS));
				
				venue.setLatitude(location.getDouble(_LATITUDE));
				venue.setLongtitude(location.getDouble(_LONGITUDE));
				new ImageRetriever(venue);
				Log.v("Image", "Image Retrived for: " + venue.getName());
				listOfVenues.add(venue);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} // end for loop
	} // 
	
	private JSONObject getInnerObject(JSONObject object, String objectName) {
		JSONObject jsonObject = null;
		try {
			jsonObject = object.getJSONObject(objectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return jsonObject;
	}
	
	public List<Venue> getVenues() {
		return listOfVenues;
	}
	
}
