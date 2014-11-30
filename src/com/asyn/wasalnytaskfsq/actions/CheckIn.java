package com.asyn.wasalnytaskfsq.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.models.Venue;

import android.os.AsyncTask;

public class CheckIn {
	
	private static final String CHECK_IN_URL = "https://api.foursquare.com/v2/checkins/add";
	private static final String VENUE_ID = "venueId";
	
	private Venue venue;
	private OnTaskCompletedListener listener;
	
	private String oauthToken;
	
	public CheckIn(Venue venue, String oauthToken, OnTaskCompletedListener listener) {
		this.venue = venue;
		this.oauthToken = oauthToken;
		this.listener = listener;
		new CheckIntask().execute();
	}
	
	private class CheckIntask extends AsyncTask<Void, Void, Void> {
		
		int statusCode = -1;

		@Override
		protected Void doInBackground(Void... params) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(CHECK_IN_URL);
			
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("oauth_token", oauthToken));
				nameValuePairs.add(new BasicNameValuePair("v", "20141127"));
				nameValuePairs.add(new BasicNameValuePair(VENUE_ID, venue.getId()));
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = client.execute(post);
				statusCode = response.getStatusLine().getStatusCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			listener.onTaskCompleted(venue, statusCode);
		}
	}
	
}
