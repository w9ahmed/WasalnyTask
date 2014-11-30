package com.asyn.wasalnytaskfsq.utilities;

import android.content.Context;

import com.asyn.wasalnytaskfsq.models.Venue;

public class VenueCache {
	
	private DataCache cache;
	private Venue venue;
	
	public VenueCache(Context context, Venue venue, int index) {
		this.venue = venue;
		String name = "venue" + index;
		cache = new DataCache(context, name);
	}
	
	public void cacheVenue() {
		cache.cache("id", venue.getId());
		cache.cache("name", venue.getName());
		cache.cache("category", venue.getCategory());
		cache.cache("address", venue.getAddress());
		cache.cache("lat", venue.getLatitude());
		cache.cache("lng", venue.getLongtitude());
		cache.cache("photoUrl", venue.getPhotoURL());
	}
	
	public void getCachedVenue() {
		venue.setId(cache.getCachedString("id"));
		venue.setName(cache.getCachedString("name"));
		venue.setCategory(cache.getCachedString("category"));
		venue.setAddress(cache.getCachedString("address"));
		venue.setLatitude(cache.getCachedDouble("lat"));
		venue.setLongtitude(cache.getCachedDouble("lng"));
		venue.setPhotoURL(cache.getCachedString("photoUrl"));
	}
	
	public boolean itItCached() {
		return cache.isItCached("id", venue.getId());
	}
	
	public void clear() {
		cache.clear();
	}
}
