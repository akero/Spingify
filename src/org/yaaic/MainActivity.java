package org.yaaic;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.loopj.android.image.SmartImageView;
import com.akero.spingify.R;
import com.zohaibbrohi.loginusingparsesdk.Login2;

public class MainActivity extends Activity {

	Button b2,b3;//b1;
	float density, dpHeight, dpWidth;
	int buttonwidth, buttonheight, padding, xdistance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mains);
		try {
			FileInputStream fis = getApplicationContext().openFileInput("isloggedin.txt");
			BufferedInputStream bis = new BufferedInputStream(fis);
			Boolean idgaf=true;
			/*byte[] c = new byte[1024];
			Boolean idgaf= false;
			int readChars = 0;
			String strFileContents=""; 
			 int bytesRead=0;
			 while( (bytesRead = bis.read(c)) != -1){ 
			    strFileContents = new String(c, 0, bytesRead);               
			    bis.close();
			    fis.close();
			    idgaf=true;
			    Log.d("end1","ntag");
			 }*/
			 if(idgaf==true){
				 Log.d("end2","ntag");
				 Intent in =  new Intent(MainActivity.this,org.yaaic.activity.ServersActivity.class);
					startActivity(in);
					Log.d("end3","ntag");
			 }
		
		}catch(Exception e){
			Log.d("Error is3 "+e.toString(),"gotcha123");
		}
	//	b1=(Button) findViewById(R.id.fblogin);
		b2=(Button) findViewById(R.id.emaillogin);
		b3=(Button) findViewById(R.id.login);
		SmartImageView myImage = (SmartImageView) this.findViewById(R.id.imageViewbg);
		myImage.setImageUrl("", R.drawable.background);
		screendimensions();
		
		//setting button's margins and length according to well thought out logic
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( 
				(int)(buttonwidth*density), (int)(buttonheight*density));
		layoutParams.setMargins(0, 0, 0, (int)(xdistance*density));
		//b1.setLayoutParams (layoutParams);
		b2.setLayoutParams (layoutParams);
		b3.setLayoutParams (layoutParams);
		
		//send user to login screen
		b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Intent i=new Intent(MainActivity.this, Login.class);
            	startActivity(i);
            }
        });
		
		b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	Intent i=new Intent(MainActivity.this,  com.zohaibbrohi.loginusingparsesdk.SignUp.class);
            	try{startActivity(i);}catch(Exception e){System.out.println(e);}
            }
        });
		
		//temp code remove
		
		/*b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Intent i=new Intent(MainActivity.this, org.yaaic.activity.ServersActivity.class);
            	startActivity(i);
            }
        });*/
		
	}
	//Don't touch w/o asking skaterKid
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
}