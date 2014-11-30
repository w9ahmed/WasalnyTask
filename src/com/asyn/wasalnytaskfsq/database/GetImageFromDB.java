package com.asyn.wasalnytaskfsq.database;

import android.os.AsyncTask;
import android.util.Log;

public class GetImageFromDB {
	
	private ImageLiteHelper imageLiteHelper;
	
	public GetImageFromDB() {
		
	}
	
	private class LoadImageFromDB extends AsyncTask<Void, Void, ImageHelper> {
		
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ImageHelper doInBackground(Void... params) {
			Log.d("LoadImageFromDatabaseTask", "doInBackground");
			return imageLiteHelper.getImage("img");
		}
		
		@Override
		protected void onPostExecute(ImageHelper result) {
			Log.d("LoadImageFromDatabaseTask", "onPostExecute");
		}
	}
	
}
