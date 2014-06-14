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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.SignupService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHRegistrationDialog;
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
				JHUtility.showDialogOk("",
						"Please follow instructions in the Encourage registration confirmation email", this);
				JHRegistrationDialog dialog = new JHRegistrationDialog(this, this);
				dialog.show();
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


        service.signUpUser("postPersonDetails",firstName,lastName,emailAddress, new Callback<SpocResponse>() {
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

						}else{
							System.out.println("error");

						}
						
					}
				}
			
            }

            @Override
            public void failure(RetrofitError retrofitError) {

                System.out.println("Error");

            }
        });

	}
}
