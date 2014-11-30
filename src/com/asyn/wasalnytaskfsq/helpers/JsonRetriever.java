package com.asyn.wasalnytaskfsq.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class JsonRetriever {
	
	private OnTaskStartedListener taskStartedListener;
	private OnTaskCompletedListener taskCompletedListener;
	private String url;
	
	private StringBuilder responseData;
	
	public JsonRetriever(String url, OnTaskStartedListener taskStartedListener, OnTaskCompletedListener taskCompletedListener) {
		/**
		 * TODO
		 * Create an interface to do the needed tasks
		 * TODO create 2 constructors
		 */
		this.url = url;
		this.taskStartedListener = taskStartedListener;
		this.taskCompletedListener = taskCompletedListener;
	}
	
	/**
	 * Executes GetJsonTask
	 */
	public void execute() {
		new GetJsonTask().execute();
	}
	
	/**
	 * GetJsonTask, Asynchronous Task
	 * @author Ahmed
	 *
	 */
	private class GetJsonTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			responseData = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			
			HttpResponse response;
			Log.v(JsonRetriever.class.getSimpleName(), "HTTP RESPONSE SUCCESSFUL");
			
			try {
				response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				Log.v(JsonRetriever.class.getSimpleName(), "StatusCode: " + statusCode);
				
				// Checks if it's a successful HTTP connection, 200
				if(statusCode == HttpURLConnection.HTTP_OK) {
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					
					String line;
					while((line = reader.readLine()) != null)
						responseData.append(line);
					
					taskStartedListener.onTaskStarted(responseData);					
				} else {
					Log.e(JsonRetriever.class.getSimpleName(), "Failed to download file.");
				}
			}
			catch (ClientProtocolException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			taskCompletedListener.onTaskCompleted();
		}		
	}
	
}
