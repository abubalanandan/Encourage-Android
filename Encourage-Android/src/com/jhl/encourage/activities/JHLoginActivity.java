package com.jhl.encourage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.LoginService;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.utilities.JHUtility;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
		emailField = (EditText)findViewById(R.id.usernameField);
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

        RestAdapter restAdapter = EncourageApplication.getRestAdapter();

        LoginService service = restAdapter.create(LoginService.class);

        service.loginUser("userLogin",email,password,new Callback<SpocResponse>() {
            @Override
            public void success(SpocResponse spocResponse, Response response) {

                System.out.println("success");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println("error");
            }
        });
		
	}
	
	public void registerButtonClicked(View view){
		Intent intent = new Intent(this, JHRegistrationActivity.class);
		startActivity(intent);
	}
}
