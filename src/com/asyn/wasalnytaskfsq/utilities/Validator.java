package com.asyn.wasalnytaskfsq.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Validator {
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
			return true;
		return false;
	}
	
}
