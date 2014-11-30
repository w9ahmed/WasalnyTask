package com.asyn.wasalnytaskfsq.actions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.asyn.wasalnytaskfsq.FindPlaces;
import com.asyn.wasalnytaskfsq.models.Venue;
import com.asyn.wasalnytaskfsq.utilities.PhotoJSONHandler;


public class ImageRetriever {
	
	private static final String API_PHOTO = "https://api.foursquare.com/v2/venues/";
	private static final String PHOTOS_OAUTH = "/photos?oauth_token=" + FindPlaces.getOAuthToken();
	private static final String V_D_LIMIT = "&v=20141127&limit=1";
	
	//private OnTaskCompletedListener listener;
	private StringBuilder builder;
	private Venue venue;
	
	public ImageRetriever(Venue venue) {
		this.venue = venue;
		new GetImages().execute();
	}
	
	private class GetImages extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			String url = API_PHOTO + venue.getId() + PHOTOS_OAUTH + V_D_LIMIT;
			HttpGet httpGet = new HttpGet(url);
			
			HttpResponse response;
			try {
				response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();				
				if(statusCode == HttpURLConnection.HTTP_OK) {
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while((line = reader.readLine()) != null)
						builder.append(line);
					PhotoJSONHandler jsonHandler = new PhotoJSONHandler(builder);
					venue.setPhotoURL(jsonHandler.getImageURL());
					Log.v(ImageRetriever.class.getSimpleName(), jsonHandler.getImageURL());
				}
			} catch (Exception e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
		}
		
	}
	
}