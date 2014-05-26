package com.jhl.encourage.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jhl.encourage.R;

public class JHRegistrationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		
	}

	public void loginButtonClicked(View view){
		finish();
	}
}
