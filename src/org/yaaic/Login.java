package org.yaaic;
import java.io.FileOutputStream;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.akero.spingify.R;
import com.zohaibbrohi.loginusingparsesdk.ConnectionDetector;

public class Login extends Activity {
	EditText username1, password1;
	Button login;
	//TextView btn_ForgetPass;
	
	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;

	float density, dpHeight, dpWidth;
	int buttonwidth, buttonheight, padding, xdistance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logins);
		login=(Button) findViewById(R.id.submitbutton);
		//btn_ForgetPass=(TextView) findViewById(R.id.forgotpass);
		username1=(EditText)findViewById(R.id.username);
		password1=(EditText)findViewById(R.id.pass);
		screendimensions();
		
		LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams( 
				(int)(buttonwidth*density), (int)(buttonheight*density));
		layoutParams0.setMargins((int)(xdistance*density), (int)(6*(xdistance*density)), 0, (int)(xdistance*density));
		username1.setLayoutParams (layoutParams0);
		
		LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams( 
				(int)(buttonwidth*density), (int)(buttonheight*density));
		layoutParams3.setMargins((int)(xdistance*density), 0, 0, (int)(xdistance*density));
		password1.setLayoutParams (layoutParams3);
		
		LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams( 
				(int)(buttonwidth*density), (int)(buttonheight*density));
		layoutParams1.setMargins((int)(xdistance*density), 0, 0, (int)(xdistance*density));
		login.setLayoutParams (layoutParams1);
		/*final View activityRootView = findViewById(R.id.qwerty);
		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		        int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
		        if (heightDiff > 1) { // if more than 100 pixels, its probably a keyboard...
		            // do something here
		    		Log.d("tag",Integer.toString(heightDiff));
		        }
		     }
		});*/
		//if("a"=="b"){
			
		//}
		
		//login part
		onCreateParse();
		//Calling ParseAnalytics to see Analytics of our app
		ParseAnalytics.trackAppOpened(getIntent());
		
		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());
		
		login.setOnClickListener(new OnClickListener() {

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
					showAlertDialog(Login.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}

			}
		});
		/*SpannableString content = new SpannableString("forgot your pasword?");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		btn_ForgetPass.setText(content);
		*/
/*btn_ForgetPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in =  new Intent(Login.this,com.zohaibbrohi.loginusingparsesdk.ForgetParsePassword.class);
				startActivity(in);
			}
		});*/
	}
	
	public void screendimensions(){
		Display display = getWindowManager().getDefaultDisplay();
	    DisplayMetrics outMetrics = new DisplayMetrics ();
	    display.getMetrics(outMetrics);

	    density  = getResources().getDisplayMetrics().density;
	    dpHeight = outMetrics.heightPixels / density;
	    dpWidth  = outMetrics.widthPixels / density;

		buttonwidth=(int)dpWidth-10;
		int height=(int)dpHeight/3;
		int padding=(height/18);
		buttonheight=padding*5;
		buttonwidth=(int)dpWidth-(padding*2);
		xdistance=(int)((dpWidth-buttonwidth)/2);
		Log.d("tag",Float.toString(outMetrics.heightPixels));
		Log.d("tag",Float.toString(outMetrics.widthPixels));
	}
	
	public void onCreateParse() { 
		Parse.initialize(this, "xfwzYdiI9NkeyAx255ulPJPOGV1fhod2WEQXOyxI", "Zw5WMdQS89Cf3tUDJv21u5erX9LilBZqtbN0bSRd");
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
		String username = username1.getText().toString();
		String password = password1.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			password1.setError(getString(R.string.error_field_required));
			focusView = password1;
			cancel = true;
		} else if (password.length() < 4) {
			password1.setError(getString(R.string.error_invalid_password));
			focusView =password1;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(username)) {
			username1.setError(getString(R.string.error_field_required));
			focusView = username1;
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
		// TODO Auto-generated method stub
		try{
			Log.d("tagd", "file made");
			FileOutputStream fos = null;Log.d("tagd", "file made");
			fos = getApplicationContext().openFileOutput("isloggedin.txt", 0);Log.d("tagd", "file made");
			
				fos.write("yes".getBytes());Log.d("tagd", "file made");
			fos.close();
			Log.d("tagd", "file made");
		}catch(Exception e){
			Log.d("error is "+e.toString(),"gotcha123");
			//Toast.makeText(Login2.this, "Failed, try emptying space in internal storage",  Toast.LENGTH_SHORT ).show();
		}
		Intent in =  new Intent(Login.this,org.yaaic.activity.ServersActivity.class);
		Log.d("tagd", "file made");
		startActivity(in);
		Log.d("tagd", "file made");
	}
	protected void loginUnSuccessful() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
		showAlertDialog(Login.this,"Login", "Username or Password is invalid.", false);
	}

	private void clearErrors(){
		username1.setError(null);
		password1.setError(null);
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