package com.asyn.wasalnytaskfsq.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.asyn.wasalnytaskfsq.connections.OnTaskCompletedListener;
import com.asyn.wasalnytaskfsq.models.Venue;

public class GetImageFromDB {
	
	private ImageSQLiteHelper imageLiteHelper;
	private Venue venue;
	private OnTaskCompletedListener ontaskCompletedListener;
	
	public GetImageFromDB(Context context, Venue venue, OnTaskCompletedListener ontaskCompletedListener) {
		imageLiteHelper = new ImageSQLiteHelper(context);
		this.ontaskCompletedListener = ontaskCompletedListener;
		new LoadImageFromDB().execute();
	}
	
	private class LoadImageFromDB extends AsyncTask<Void, Void, ImageHelper> {

		@Override
		protected ImageHelper doInBackground(Void... params) {
			Log.d("LoadImageFromDatabaseTask", "doInBackground");
			return imageLiteHelper.getImage(venue.getCategory());
		}
		
		@Override
		protected void onPostExecute(ImageHelper result) {
			Log.d("LoadImageFromDatabaseTask", "onPostExecute");
			ontaskCompletedListener.onTaskCompleted(convertToBitmap(result.getImageByteArray()));
		}
	}
	
	private Bitmap convertToBitmap(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	
}
