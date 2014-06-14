package com.jhl.encourage.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHLoginActivity;
import com.jhl.encourage.activities.JHRegistrationActivity;

public class JHRegistrationDialog extends Dialog {


	Activity regiActity;

	public JHRegistrationDialog(Context context, Activity regiActity) {
		super(context);
		setContentView(R.layout.regstration_dialogue);
		this.regiActity = regiActity;
		initViews();

		// TODO Auto-generated constructor stub
	}

	private void initViews() {
		Button okButton = (Button)findViewById(R.id.regOkButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(regiActity, JHLoginActivity.class);
				regiActity.startActivity(intent);
				regiActity.finish();
				
			}
		});
	}

	
}
