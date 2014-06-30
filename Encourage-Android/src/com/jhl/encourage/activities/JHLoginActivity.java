package com.jhl.encourage.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.LoginService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
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

	private void initViews() {
		emailField = (EditText) findViewById(R.id.usernameField);
		passwordField = (EditText) findViewById(R.id.passwordField);	
	}

	public void loginButtonClicked(View view) {

		validateFields();

	}
	private String email;
	private String pswd;
	private String regId;
	
	private void validateFields() {
		this.email = emailField.getText().toString();
		this.pswd = passwordField.getText().toString();

		if (((email == null) || email.isEmpty()) && ((pswd == null) || pswd.isEmpty()))
			JHUtility.showDialogOk("",getString(R.string.email_and_pswd_empty_msg), this);
		else if ((email == null) || email.isEmpty())
			JHUtility.showDialogOk("", getString(R.string.email_empty_msg),	this);
		else if ((pswd == null) || pswd.isEmpty())
			JHUtility.showDialogOk("", getString(R.string.pswd_empty_msg), this);
		else {
			JHUtility.showProgressDialog("Logging in ...", this);		
			new GCMRegistrationTask().execute();
		}
	}

	
		
	public void forgotPasswordClicked(View view){
		
		String url = "tryencourage.com";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
	
	class GCMRegistrationTask extends AsyncTask<String, Integer, Boolean> {
			@Override
			protected void onPreExecute() {
				
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				invokeLoginApi(email, pswd, regId);
			}

			@Override
			protected Boolean doInBackground(String... params) {
				String msg = "";
                GoogleCloudMessaging gcm = null;
        		String PROJECT_NUMBER = "291052764949";
        		boolean retVal = false;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(JHLoginActivity.this);
                    }
                    regId = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regId;
                   
                    retVal = true;
                    
                    

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    retVal = false;

                }
                
                Log.i(JHConstants.LOG_TAG,  msg);
                
                return retVal;
			}
		}
	
	public void invokeLoginApi(String email, String password, String regId) {

		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		LoginService service = restAdapter.create(LoginService.class);

		Log.d(JHConstants.LOG_TAG, "regId "+regId);
		
		service.loginUser("userLogin", email, password,regId, 
				new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,	Response response) {
						ArrayList<SpocObject> responseList = spocResponse.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode().equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject.getMap();
								String success = map.get("success");
								if(success.equalsIgnoreCase("true")){
									System.out.println("success");
									String loginTocken = map.get("token");
									Log.d(JHConstants.LOG_TAG, "loginToken  " +loginTocken);
									JHAppStateVariables.setLoginTocken(loginTocken);
									JHUtility.dismissProgressDialog(JHLoginActivity.this);
									Intent intent = new Intent(JHLoginActivity.this, JHTimelineActivity.class);
									startActivity(intent);
									finish();
								}else{
									System.out.println("error");
									JHUtility.dismissProgressDialog(JHLoginActivity.this);
									JHUtility.showDialogOk("",getString(R.string.login_failed), JHLoginActivity.this);	
								}
							}
						}
					}

					@Override
					public void failure(RetrofitError retrofitError) {
						System.out.println("error");
						JHUtility.dismissProgressDialog(JHLoginActivity.this);

					}
				});
		

	}

	public void registerButtonClicked(View view) {
		Intent intent = new Intent(this, JHRegistrationActivity.class);
		startActivity(intent);
	
	}
}
