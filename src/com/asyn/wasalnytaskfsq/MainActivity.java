package com.asyn.wasalnytaskfsq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private DataCache cache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		cache = new DataCache(getApplicationContext(), "oauth");
		Intent intent;
		if(cache.getCached("token") != null) {
			intent = new Intent(MainActivity.this, FindPlaces.class); // TODO
			intent.putExtra(AuthenticationActivity.O_AUTH_TOKEN, cache.getCached("token"));
		}
		else {
			intent = new Intent(MainActivity.this, AuthenticationActivity.class);
		}
		
		startActivity(intent);
		this.finish();
	}
}
