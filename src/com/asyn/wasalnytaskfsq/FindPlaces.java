package com.asyn.wasalnytaskfsq;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.asyn.wasalnytaskfsq.helpers.NearbyVenuesReader;
import com.asyn.wasalnytaskfsq.helpers.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.helpers.Venue;
import com.asyn.wasalnytaskfsq.utilities.ShowLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindPlaces extends Activity {

	static final LatLng HOME = new LatLng(30.1054124, 31.3691705);

	private static final String API_VENUES = "https://api.foursquare.com/v2/venues/search?ll=";
	private static final String _COMMA_ = ",";
	private static final String O_AUTH = "&oauth_token=";
	private static final String API_V_D = "&v=20141127";

	private String oauth_token;

	private GoogleMap map;
	private ShowLocation currentLocation;
	
	private NearbyVenuesReader nearbyVenues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_places);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		 getMyCurrentLocation();		
		
		nearbyVenues = new NearbyVenuesReader(buildAPIURL(), listener);
	}

	/**
	 * Get Current Location
	 */
	private void getMyCurrentLocation() {
		if(currentLocation == null)
			currentLocation = new ShowLocation(this);
		
		if (currentLocation.getLocation() != null) {
			Marker marker = map.addMarker(new MarkerOptions().position(
					currentLocation.getLatLng()).title("Current"));

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(
					currentLocation.getLatLng(), 15));
		}
	}
	
	/**
	 * Build API URL
	 * @return
	 */
	private String buildAPIURL() {
		if(currentLocation == null)
			currentLocation = new ShowLocation(this);
		oauth_token = getIntent().getStringExtra(MainActivity.O_AUTH_TOKEN);
		String lat = Double.toString(currentLocation.getLocation().getLatitude());
		String lng = Double.toString(currentLocation.getLocation().getLongitude());
		
		String url = API_VENUES + lat + _COMMA_ + lng + O_AUTH + oauth_token + API_V_D;
		return url;
	}
	
	private void traceMarkers() {
		
	}
	
	private OnTaskCompletedListener listener = new OnTaskCompletedListener() {
		@Override
		public void onTaskTaskCompleted() {
			traceMarkers();
		}
	};
	
}
