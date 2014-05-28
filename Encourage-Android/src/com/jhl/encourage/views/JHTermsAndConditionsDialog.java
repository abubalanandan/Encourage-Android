package com.jhl.encourage.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.jhl.encourage.R;

public class JHTermsAndConditionsDialog extends Dialog {

	private TextView htmlView;
	private Activity mActivity;

	public JHTermsAndConditionsDialog(Context context) {
		super(context, R.style.ThemeDialogCustom);
		mActivity = (Activity) context;
		setContentView(R.layout.terms_and_conditions);
		initViews();

		// TODO Auto-generated constructor stub
	}

	private void initViews() {
		htmlView = (TextView) findViewById(R.id.htmlView);
		htmlView.setText(Html.fromHtml(readTxt()));
	}

	private String readTxt() {
		
		int i;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			InputStream inputStream = mActivity.getAssets().open("jhlpolicy.txt");
		 byteArrayOutputStream = new ByteArrayOutputStream();
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toString();
	}
}
