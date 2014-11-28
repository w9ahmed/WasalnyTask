package com.asyn.wasalnytaskfsq.utilities;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public class ShowLocation implements LocationListener {
	
	private LocationManager locationManager;
	private String provider;
	
	private Location location;
	
	public ShowLocation(Context context) {
		// Get location manager
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		// Define the criteria how to select the location provider
		provider = locationManager.NETWORK_PROVIDER;
		location = locationManager.getLastKnownLocation(provider);
		
		if(location != null)
			onLocationChanged(location);
	}
	
	/**
	 * Return Current Location
	 * @return
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Return Latitude and Longitude
	 * @return
	 */
	public LatLng getLatLng() {
		return new LatLng(location.getLatitude(), location.getLongitude());
	}
	
	/**
	 * Request updates
	 */
	public void requestUpdates() {
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	
	/**
	 *  Remove the location listener updates when Activity is paused
	 */
	public void removeUpdates() {
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getAltitude());
		int lng = (int) (location.getLongitude());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

}
