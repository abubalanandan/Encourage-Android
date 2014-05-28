package com.jhl.encourage.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jhl.encourage.R;
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
	}

	public void loginButtonClicked(View view) {
		finish();
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
			} else {
				JHUtility.showDialogOk("",
						"Please enter a valid email address", this);
			}
		} else {
			JHUtility.showDialogOk("", "Please enter your email address", this);
		}
	}

	public void showPolicy(View view) {
		JHTermsAndConditionsDialog dialog = new JHTermsAndConditionsDialog(this);
		dialog.show();
	}

	private void invokeRegistrationApi(String firstName, String lastName,
			String emailAddress) {

	}
}
