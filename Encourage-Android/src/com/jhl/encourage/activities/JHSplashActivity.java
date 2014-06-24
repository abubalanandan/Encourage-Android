package com.jhl.encourage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.jhl.encourage.R;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHCustomOkDialog;

public class JHSplashActivity extends Activity {

	
	public static int SPLASH_DISPLAY_LENGTH = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhsplash);

		 new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	            	
	                /* Create an Intent that will start the Menu-Activity. */
	                Intent mainIntent = new Intent(JHSplashActivity.this,JHLoginActivity.class);
	                startActivity(mainIntent);
	                finish();
	            }
	        }, SPLASH_DISPLAY_LENGTH);
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jhsplash, menu);
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

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_jhsplash,
//					container, false);
//			return rootView;
//		}
//	}

}
