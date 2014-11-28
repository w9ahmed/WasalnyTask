package com.asyn.wasalnytaskfsq.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class NearbyVenuesReader {
	
	private String url;
	private StringBuilder builder;
	
	private List<Venue> venues;
	
	public NearbyVenuesReader(String url) {
		this.url = url;
		venues = new ArrayList<Venue>();
		new ReadJSONTask().execute();
	} // end constructor
	
	private class ReadJSONTask extends AsyncTask<Object, Void, StringBuilder> {

		@Override
		protected StringBuilder doInBackground(Object... params) {
			builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			
			HttpResponse response;
			Log.v("HTTP GET", "HTTP RESPONSE SUCCESSEFUL");
			JSONParser jsonParser = null;
			try {
				response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				Log.v("STATUS", "CODE: " + statusCode);
				
				if(statusCode == HttpURLConnection.HTTP_OK) {
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while((line = reader.readLine()) != null)
						builder.append(line);
					jsonParser = new JSONParser(builder);
					jsonParser.parseVenues();
					venues = jsonParser.getVenuesList();
				} else {
					Log.e("NEARBY", "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
			return builder;
		}
		
		protected void onPostExecute(List<Venue> venues) {
			Log.v(NearbyVenuesReader.class.getSimpleName(), "Size: " + venues.size());
		};
	} // end of async class
	
	public List<Venue> getVenues() {
		return venues;
	}
}
