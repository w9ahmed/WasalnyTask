package com.asyn.wasalnytaskfsq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asyn.wasalnytaskfsq.constants.AuthKeys;
import com.asyn.wasalnytaskfsq.utilities.DataCache;
import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

public class AuthenticationActivity extends Activity {
	
	private static final int REQUEST_CODE_FSQ_CONNECT = 200;
	private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication_main);
		ensureUi();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_FSQ_CONNECT:
			onCompleteConnect(resultCode, data);
			break;

		case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
			onCompleteTokenExchange(resultCode, data);
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	/**
	 * Update the UI. If we already fetched a token, we'll just show a success
	 * message.
	 */
	private void ensureUi() {
		boolean isAuthorized = !TextUtils.isEmpty(ExampleTokenStore.get()
				.getToken());

		TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
		tvMessage.setVisibility(isAuthorized ? View.VISIBLE : View.GONE);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setVisibility(isAuthorized ? View.GONE : View.VISIBLE);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start the native auth flow.
				Intent intent = FoursquareOAuth.getConnectIntent(
						AuthenticationActivity.this, AuthKeys.CLIENT_ID);

				/*
				 * If the device does not have the Foursquare app installed,
				 * we'd get an intent back that would open the Play Store for download.
				 * Otherwise we start the auth flow.
				 */
				if (FoursquareOAuth.isPlayStoreIntent(intent)) {
					toastMessage(AuthenticationActivity.this,
							getString(R.string.app_not_installed_message));
					startActivity(intent);
				} else {
					startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
				}
			}
		});
	}

	private void onCompleteConnect(int resultCode, Intent data) {
		AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(
				resultCode, data);
		Exception exception = codeResponse.getException();

		if (exception == null) {
			// Success.
			String code = codeResponse.getCode();
			performTokenExchange(code);

		} else {
			if (exception instanceof FoursquareCancelException) {
				// Cancel.
				toastMessage(this, "Canceled");

			} else if (exception instanceof FoursquareDenyException) {
				// Deny.
				toastMessage(this, "Denied");

			} else if (exception instanceof FoursquareOAuthException) {
				// OAuth error.
				String errorMessage = exception.getMessage();
				String errorCode = ((FoursquareOAuthException) exception)
						.getErrorCode();
				toastMessage(this, errorMessage + " [" + errorCode + "]");

			} else if (exception instanceof FoursquareUnsupportedVersionException) {
				// Unsupported Fourquare app version on the device.
				toastError(this, exception);

			} else if (exception instanceof FoursquareInvalidRequestException) {
				// Invalid request.
				toastError(this, exception);

			} else {
				// Error.
				toastError(this, exception);
			}
		}
	}

	private void onCompleteTokenExchange(int resultCode, Intent data) {
		AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(
				resultCode, data);
		Exception exception = tokenResponse.getException();

		if (exception == null) {
			String accessToken = tokenResponse.getAccessToken();
			// Success.
			// toastMessage(this, "Access token: " + accessToken);

			// Persist the token for later use. In this example, we save
			// it to shared prefs.
			ExampleTokenStore.get().setToken(accessToken);

			// Refresh UI.
			ensureUi();
			
			/*
			 * Move on to the next activity
			 */
			Intent intent = new Intent(AuthenticationActivity.this, FindPlaces.class);
			new DataCache(AuthenticationActivity.this, "oauth").cache("token", accessToken); // Storing Key
			intent.putExtra(AuthKeys.O_AUTH_TOKEN, accessToken);
			startActivity(intent);
			this.finish();
		} else {
			if (exception instanceof FoursquareOAuthException) {
				// OAuth error.
				String errorMessage = ((FoursquareOAuthException) exception)
						.getMessage();
				String errorCode = ((FoursquareOAuthException) exception)
						.getErrorCode();
				toastMessage(this, errorMessage + " [" + errorCode + "]");

			} else {
				// Other exception type.
				toastError(this, exception);
			}
		}
	}

	/**
	 * Exchange a code for an OAuth Token. Note that we do not recommend you do
	 * this in your app, rather do the exchange on your server. Added here for
	 * demo purposes.
	 * 
	 * @param code
	 * The auth code returned from the native auth flow.
	 */
	private void performTokenExchange(String code) {
		Intent intent = FoursquareOAuth.getTokenExchangeIntent(this, AuthKeys.CLIENT_ID,
				AuthKeys.CLIENT_SECRET, code);
		startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
	}

	/**
	 * 
	 * @param context
	 * @param message
	 */
	public static void toastMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 
	 * @param context
	 * @param throwable
	 */
	public static void toastError(Context context, Throwable t) {
		Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
