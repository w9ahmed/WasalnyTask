package com.asyn.wasalnytaskfsq.actions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.models.Venue;

public class DownloadImages {
	
	private OnTaskCompletedListener onTaskCompletedListener;
	private List<Venue> venues;
	private HashMap<String, Bitmap> iconSet;
	
	public DownloadImages(List<Venue> venues, OnTaskCompletedListener onTaskCompletedListener) {
		this.venues = venues;
		this.onTaskCompletedListener = onTaskCompletedListener;
		iconSet = new HashMap<String, Bitmap>();
	}
	
	public void execute() {
		new GetBitmapFromURL().execute();
	}
	
	private class GetBitmapFromURL extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			for (Venue venue : venues) {
				if(iconSet.containsKey(venue.getCategory()) == false) {
					Bitmap bitmap = getBitmapFromURL(venue.getPhotoURL());
					if(bitmap != null) {
						iconSet.put(venue.getCategory(), bitmap);
					}
				}
			}			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			onTaskCompletedListener.onTaskComlpeted(iconSet);
		}
	}
	
	/**
	 * Establish connection
	 * and get download bitmap
	 * @param path
	 * @return
	 */
	private Bitmap getBitmapFromURL(String path) {
		try {
			Log.d("Down", "loading");
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			
			InputStream inputStream = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			Log.v("Down", "loading");
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
