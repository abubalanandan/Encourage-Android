package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.SignupService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.utilities.JHGPSTracker;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHTermsAndConditionsDialog;

public class JHRegistrationActivity extends Activity {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;
	private EditText emailField;
	private EditText firstNameField;
	private EditText lastNameField;
    private Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		pattern = Pattern.compile(EMAIL_PATTERN);
		initViews();
	}

	private void initViews() {
		emailField = (EditText) findViewById(R.id.emailField);
		firstNameField = (EditText) findViewById(R.id.firstNameField);
		lastNameField = (EditText) findViewById(R.id.lastNameField);
        registerButton = (Button) findViewById(R.id.registerButton);
        TextView termsTV = (TextView)findViewById(R.id.termsTV);
        termsTV.setText(Html.fromHtml("By clicking Register, you agree to our <u>Terms and Conditions</u> herein documented"));
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButtonClicked(view);
            }
        });
	}

	public void loginButtonClicked(View view) {
		Intent intent = new Intent(this, JHLoginActivity.class);
		finish();
		startActivity(intent);
	}

	private boolean validate(final String hex) {

		matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	public void registerButtonClicked(View view) {
		if (firstNameField.getText().toString() == null
				|| firstNameField.getText().toString().isEmpty()) {
			JHUtility.showDialogOk("", "Please enter your first name", this);
			return;
		}
		if (emailField.getText().toString() != null
				&& !emailField.getText().toString().isEmpty()) {
			if (validate(emailField.getText().toString())) {
				invokeRegistrationApi(firstNameField.getText().toString(),
						lastNameField.getText().toString(), emailField
								.getText().toString());
				
//				JHRegistrationDialog dialog = new JHRegistrationDialog(this);
//				dialog.show();
			} else {
				JHUtility.showDialogOk("",
						"Please enter a valid email address", this);
			}
		} else {
			JHUtility.showDialogOk("", "Please enter your email address", this);
		}
	}

//	private void showLogin(){
//		
//	}
	
	public void showPolicy(View view) {
		JHTermsAndConditionsDialog dialog = new JHTermsAndConditionsDialog(this);
		dialog.show();
	}

	private void invokeRegistrationApi(String firstName, String lastName,
			String emailAddress) {

        RestAdapter restAdapter = EncourageApplication.getRestAdapter();

        SignupService service = restAdapter.create(SignupService.class);
        JHUtility.showProgressDialog("Signing up..", this);

		JHGPSTracker gpsTracker = JHGPSTracker.getGPSTracker(JHRegistrationActivity.this);
		Location location = gpsTracker.getLocation();
		String latitude = "";
		String longitude = "";
		if(location != null ){
			latitude = location.getLatitude()+"";
			longitude = location.getLongitude()+"";
		}
        
        service.signUpUser("postPersonDetails",firstName,lastName,emailAddress, JHUtility.getDateTime(), JHUtility.getTimeZoneString(), latitude, longitude, 
        		new Callback<SpocResponse>() {
            @Override
            public void success(SpocResponse spocResponse, Response response) {


				ArrayList<SpocObject> responseList = spocResponse
						.getSpocObjects();
				for (SpocObject spocObject : responseList) {
					if (spocObject.getResultTypeCode()
							.equalsIgnoreCase("STATUS")) {
						HashMap<String, String> map = spocObject
								.getMap();
						String success = map.get("success");
						if(success.equalsIgnoreCase("true")){
							System.out.println("success");
							JHUtility.dismissProgressDialog(JHRegistrationActivity.this);
							AlertDialog dialog = new AlertDialog.Builder(JHRegistrationActivity.this).setTitle("Success").setMessage("Please follow instructions in the Encourage registration confirmation email").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									finish();
								}
							}).create();
							dialog.show();

						}else{
							JHUtility.dismissProgressDialog(JHRegistrationActivity.this);

							if(responseList.size() > 1){
								spocObject = responseList.get(1);
								map = spocObject.getMap();
								String message = map.get("errortitle");
								
								if(message.equals("User Name Exist")) {
									JHUtility.showDialogOk("Error", map.get("errordescription"), JHRegistrationActivity.this);
								}
							}else {
								JHUtility.showDialogOk("Error", "Registration Failed. Please try again", JHRegistrationActivity.this);
							}
							System.out.println("error");

						}
						
					}
				}
			
            }

            @Override
            public void failure(RetrofitError retrofitError) {

                System.out.println("Error");
                JHUtility.dismissProgressDialog(JHRegistrationActivity.this);

				JHUtility.showDialogOk("Error", "Registration Failed. Please try again", JHRegistrationActivity.this);


            }
        });

	}
}
