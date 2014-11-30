package com.asyn.wasalnytaskfsq.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonTools {
	private JsonTools() {}
	
	/**
	 * Take The Response and return the JSON object
	 * @param responseData
	 * @return
	 */
	public static JSONObject getJsonObjectFromResponse(String responseData) {
		try {
			return new JSONObject(responseData);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Returns a JSON object from another JSON Object
	 * @param fromObject
	 * @param name
	 * @return
	 */
	public static JSONObject getJsonObjectFrom(JSONObject fromObject, String name) {
		try {
			return fromObject.getJSONObject(name);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a JSON Array from another JSON Object
	 * @param fromObject
	 * @param name
	 * @return
	 */
	public static JSONArray getJsonArrayFrom(JSONObject fromObject, String name) {
		try {
			return fromObject.getJSONArray(name);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param fromArray
	 * @param index
	 * @return
	 */
	public static JSONObject getJsonObjectFrom(JSONArray fromArray, int index) {
		try {
			return fromArray.getJSONObject(index);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param fromObject
	 * @param name
	 * @return
	 */
	public static String getString(JSONObject fromObject, String name) {
		try {
			return fromObject.getString(name);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static double getDouble(JSONObject fromObject, String name) {
		try {
			return fromObject.getDouble(name);
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * 
	 * @param jsonObject
	 * @param name
	 * @return
	 */
	public static boolean has(JSONObject jsonObject, String name) {
		return jsonObject.has(name);
	}
}
