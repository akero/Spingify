package com.zohaibbrohi.loginusingparsesdk;

import java.io.FileOutputStream;
import java.util.Locale;

import com.akero.spingify.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login2 extends Activity{
	Button btn_LoginIn = null;
	Button btn_SignUp = null;
	Button btn_ForgetPass = null;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login2);

		//Initializing Parse SDK
		onCreateParse();
		//Calling ParseAnalytics to see Analytics of our app
		ParseAnalytics.trackAppOpened(getIntent());
		
		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		btn_LoginIn = (Button) findViewById(R.id.btn_login);
		btn_SignUp = (Button) findViewById(R.id.btn_signup);
		btn_ForgetPass = (Button) findViewById(R.id.btn_ForgetPass);
		mUserNameEditText = (EditText) findViewById(R.id.username);
		mPasswordEditText = (EditText) findViewById(R.id.password);


		btn_LoginIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// get Internet status
				isInternetPresent = cd.isConnectingToInternet();
				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					// make HTTP requests
					attemptLogin();
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(Login2.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}

			}
		});

		btn_SignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in =  new Intent(Login2.this,SignUp.class);
				startActivity(in);
			}
		});
		
		btn_ForgetPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in =  new Intent(Login2.this,ForgetParsePassword.class);
				startActivity(in);
			}
		});



	}

	public void onCreateParse() { 
		Parse.initialize(this, "xfwzYdiI9NkeyAx255ulPJPOGV1fhod2WEQXOyxI", "Zw5WMdQS89Cf3tUDJv21u5erX9LilBZqtbN0bSRd");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_forgot_password:
			forgotPassword();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void forgotPassword(){
		/* 
		FragmentManager fm = getSupportFragmentManager();
	     ForgotPasswordDialogFragment forgotPasswordDialog = new ForgotPasswordDialogFragment();
	     forgotPasswordDialog.show(fm, null);
		 */
	}

	public void attemptLogin() {

		clearErrors();

		// Store values at the time of the login attempt.
		String username = mUserNameEditText.getText().toString();
		String password = mPasswordEditText.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			mPasswordEditText.setError(getString(R.string.error_field_required));
			focusView = mPasswordEditText;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordEditText.setError(getString(R.string.error_invalid_password));
			focusView =mPasswordEditText;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(username)) {
			mUserNameEditText.setError(getString(R.string.error_field_required));
			focusView = mUserNameEditText;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// perform the user login attempt.
			login(username.toLowerCase(Locale.getDefault()), password);
		}
	}

	private void login(String lowerCase, String password) {
		// TODO Auto-generated method stub
		ParseUser.logInInBackground(lowerCase, password, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null)
					loginSuccessful();
				else
					loginUnSuccessful();
			}
		});

	}

	protected void loginSuccessful() {
		Log.d("tagd", "does this work");
		// TODO Auto-generated method stub
		/*try{
			Log.d("tagd", "file made");
			FileOutputStream fos = null;Log.d("tagd", "file made");
			fos = getApplicationContext().openFileOutput("isloggedin.txt", 0);Log.d("tagd", "file made");
			
				fos.write("yes".getBytes());Log.d("tagd", "file made");
			fos.close();
			Log.d("tagd", "file made");
		}catch(Exception e){
			Log.d("error is "+e.toString(),"gotcha123");
			//Toast.makeText(Login2.this, "Failed, try emptying space in internal storage",  Toast.LENGTH_SHORT ).show();
		}*/
		Intent in =  new Intent(Login2.this,org.yaaic.activity.ServersActivity.class);
		startActivity(in);
	}
	protected void loginUnSuccessful() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
		showAlertDialog(Login2.this,"Login", "Username or Password is invalid.", false);
	}

	private void clearErrors(){
		mUserNameEditText.setError(null);
		mPasswordEditText.setError(null);
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon(R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}




}
