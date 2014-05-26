package com.jhl.encourage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jhl.encourage.R;

public class JHLoginActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		
	}

	
	public void registerButtonClicked(View view){
		Intent intent = new Intent(this, JHRegistrationActivity.class);
		startActivity(intent);
	}
}
