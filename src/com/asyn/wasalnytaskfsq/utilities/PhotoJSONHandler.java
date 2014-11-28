package com.asyn.wasalnytaskfsq.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoJSONHandler {
	
	private static final String _RESPONSE = "response";
	private static final String _PHOTOS = "photos";
	private static final String _ITEMS = "items";
	
	private static final String _PREFIX = "prefix";
	private static final String _SUFFIX = "suffix";
	
	private static final String SIZE = "100x100";
	
	private JSONArray items;
	
	private String imageURL;
	
	public PhotoJSONHandler(StringBuilder responseData) {
		imageURL = null;
		JSONObject jsonResponse;
		try {
			jsonResponse = new JSONObject(responseData.toString());
			JSONObject response = jsonResponse.getJSONObject(_RESPONSE);
			items = response.getJSONObject(_PHOTOS).getJSONArray(_ITEMS);
			
			if(items.length() != 0) {
				String prefix = items.getJSONObject(0).getString(_PREFIX);
				String suffix = items.getJSONObject(0).getString(_SUFFIX);
				imageURL = prefix + SIZE + suffix;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getImageURL() {
		return imageURL;
	}
}
