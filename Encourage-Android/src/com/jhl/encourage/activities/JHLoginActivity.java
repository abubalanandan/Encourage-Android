package com.jhl.encourage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jhl.encourage.R;
import com.jhl.encourage.utilities.JHUtility;

public class JHLoginActivity extends Activity {

	private EditText emailField;
	private EditText passwordField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initViews();
		
	}


	private void initViews(){
		emailField = (EditText)findViewById(R.id.emailField);
		passwordField = (EditText)findViewById(R.id.passwordField);
	}
	
	public void loginButtonClicked(View view){
		
		validateFields();
		
	}
	
	
	
	private void validateFields(){
		String email = emailField.getText().toString();
		String pswd = passwordField.getText().toString();

		if (((email == null) || email.isEmpty())
				&& ((pswd == null) || pswd.isEmpty()))
			JHUtility.showDialogOk("",
					getString(R.string.email_and_pswd_empty_msg), this);
		else if ((email == null) || email.isEmpty())
			JHUtility.showDialogOk("", getString(R.string.email_empty_msg),
					this);
		else if ((pswd == null) || pswd.isEmpty())
			JHUtility
					.showDialogOk("", getString(R.string.pswd_empty_msg), this);
		else
			this.invokeLoginApi(email, pswd);
	}
	
	
	public void invokeLoginApi(String email, String password){
		
	}
	
	public void registerButtonClicked(View view){
		Intent intent = new Intent(this, JHRegistrationActivity.class);
		startActivity(intent);
	}
}
