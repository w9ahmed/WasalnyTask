package com.asyn.wasalnytaskfsq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asyn.wasalnytaskfsq.constants.AuthKeys;
import com.asyn.wasalnytaskfsq.utilities.DataCache;
import com.asyn.wasalnytaskfsq.utilities.Validator;

public class MainActivity extends Activity {

	private DataCache cache;
	private TextView notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cache = new DataCache(getApplicationContext(), "oauth");
		if (Validator.isNetworkAvailable(MainActivity.this)) {
			Intent intent;
			if(cache.getCachedString("token") != null) {
				intent = new Intent(MainActivity.this, FindPlaces.class); // TODO
				intent.putExtra(AuthKeys.O_AUTH_TOKEN, cache.getCachedString("token"));
			}
			else {
				intent = new Intent(MainActivity.this, AuthenticationActivity.class);
			}
			startActivity(intent);
			this.finish();
		} else {
			// Toast.makeText(MainActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
			notice = (TextView) findViewById(R.id.notice);
			notice.setVisibility(View.VISIBLE);
		}
	}
}
