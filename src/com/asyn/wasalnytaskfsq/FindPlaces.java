package com.asyn.wasalnytaskfsq;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asyn.wasalnytaskfsq.actions.CheckIn;
import com.asyn.wasalnytaskfsq.actions.DownloadImages;
import com.asyn.wasalnytaskfsq.actions.NearbyVenues;
import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.models.Venue;
import com.asyn.wasalnytaskfsq.utilities.ShowLocation;
import com.google.android.gms.internal.ca;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindPlaces extends Activity {

	private static final String API_VENUES = "https://api.foursquare.com/v2/venues/search?ll=";
	
	private static final String _COMMA_ = ",";
	private static final String O_AUTH = "&oauth_token=";
	private static final String API_V_D = "&v=20141127";

	private static String oauth_token; // TODO find a better way

	private GoogleMap map;
	private ShowLocation currentLocation;
	
	private NearbyVenues nearbyVenues;
	
	private HashMap<Marker, Venue> venueMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_places);
		initialize();
	}
	
	/**
	 * Initialize Map
	 * Initializer
	 */
	private void initialize() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setInfoWindowAdapter(infoWindowAdapter);
		map.setOnInfoWindowClickListener(infoWindowClickListener);
		
		getMyCurrentLocation();		
		nearbyVenues = new NearbyVenues(buildAPIURL(), getVenuesTaskCompletedListener);
	}
	
	
	/**
	 * Get Current Location
	 */
	private void getMyCurrentLocation() {
		if(currentLocation == null)
			currentLocation = new ShowLocation(this);
		
		// Zoom in to current location
		if (currentLocation.getLocation() != null) {
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
		
		oauth_token = getIntent().getStringExtra(AuthenticationActivity.O_AUTH_TOKEN);
		String lat = Double.toString(currentLocation.getLocation().getLatitude());
		String lng = Double.toString(currentLocation.getLocation().getLongitude());
		
		String url = API_VENUES + lat + _COMMA_ + lng + O_AUTH + oauth_token + API_V_D;
		return url;
	}
	
	
	
	/**
	 * Draw Markers on the Map
	 * Called in: asyncTaskCompletedListener
	 */
	private void traceMarkers() {
		List<Venue> venues = nearbyVenues.getVenues();
		venueMarkers = new HashMap<Marker, Venue>();
		for (Venue venue : venues) {
			Marker marker = map.addMarker(new MarkerOptions().position(
					venue.getLocation()).title(venue.getName()));
			venueMarkers.put(marker, venue);
		}
	}
	
	
	/**
	 * Runs after the AsynTask finishes
	 */
	private OnTaskCompletedListener getVenuesTaskCompletedListener = new OnTaskCompletedListener() {
		@Override
		public void onTaskCompleted() {
			traceMarkers();
			new DownloadImages(nearbyVenues.getVenues(), imageLoadCompletedListener).execute();
		}

		@Override
		public void onTaskCompleted(Venue venue, int statusCode) {
		}

		@Override
		public void onTaskComlpeted(HashMap<String, Bitmap> iconSet) {
			
		}
	};
	
	
	/**
	 * Setting a custom info window adapter for map
	 */
	private InfoWindowAdapter infoWindowAdapter = new InfoWindowAdapter() {
		
		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			
			Venue venue = venueMarkers.get(marker);
			
			View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);
			
			TextView venue_name = (TextView) view.findViewById(R.id.venueName);
			venue_name.setText(venue.getName());
			
			TextView venue_address = (TextView) view.findViewById(R.id.venueAddress);
			venue_address.setText(venue.getAddress());
			
			if(venue.getPhotoURL() != null) {
				Log.v("FindPlaces", venue.getPhotoURL());
				ImageView venue_photo = (ImageView) view.findViewById(R.id.venueIcon);
			}
			
			return view;
		}
	};
	
	
	/**
	 * Click Listener for the Info Window
	 * Checking in
	 */
	private OnInfoWindowClickListener infoWindowClickListener = new OnInfoWindowClickListener() {
		@Override
		public void onInfoWindowClick(Marker marker) {
			new CheckIn(venueMarkers.get(marker), oauth_token, checkinCompletedListener);
		}
	};
	
	
	/**
	 * Awaits Response Of Check in
	 * Displays a Toast message
	 */
	private OnTaskCompletedListener checkinCompletedListener = new OnTaskCompletedListener() {
		
		@Override
		public void onTaskCompleted(Venue venue, int statusCode) {
			if(statusCode == HttpURLConnection.HTTP_OK)
				Toast.makeText(getApplicationContext(), "Checked at: " + venue.getName(), Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "Unable to Check in", Toast.LENGTH_LONG).show();
		}			
		
		@Override
		public void onTaskCompleted() {
		}

		@Override
		public void onTaskComlpeted(HashMap<String, Bitmap> iconSet) {
			
		}
	};
	
	
	/**
	 * 
	 */
	private OnTaskCompletedListener imageLoadCompletedListener = new OnTaskCompletedListener() {
		@Override
		public void onTaskCompleted(Venue venue, int statusCode) {
		}
		
		@Override
		public void onTaskCompleted() {
		}

		@Override
		public void onTaskComlpeted(HashMap<String, Bitmap> iconSet) {
			Map<Marker, Venue> map = venueMarkers;
			for (Map.Entry<Marker, Venue> entry : map.entrySet()) {
				Marker marker = entry.getKey();
				Bitmap bitmap = iconSet.get(entry.getValue().getCategory());
				BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
				marker.setIcon(icon);
			}
		}
	};
	
	
	//public static String getOAuthToken() { return oauth_token; }
	
	
	/**
	 * onPause
	 * Stop Location Updates When Pause
	 */
	@Override
	protected void onPause() {
		super.onPause();
		currentLocation.removeUpdates();
	}
	
	
	/**
	 * onResume
	 * Request Location Updates when Resume
	 */
	@Override
	protected void onResume() {
		super.onResume();
		currentLocation.requestUpdates();
	}
}
