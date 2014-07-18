package com.jhl.encourage.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;

public class JHTermsAndConditionsDialog extends Dialog {

	private TextView htmlView;
	private Activity mActivity;
	private ImageView backgroundView;

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
		backgroundView = (ImageView)findViewById(R.id.backgroundImageView);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		DisplayMetrics metrics = EncourageApplication.getSharedApplication().getResources().getDisplayMetrics();
		float ratio =(float) metrics.heightPixels *1.0f /metrics.widthPixels;
		float marginWidth  = (float) TypedValue.applyDimension(
		        TypedValue.COMPLEX_UNIT_DIP,
		        30, 
		        metrics		);
		float marginHeight = ratio * marginWidth * ratio;
		params.setMargins((int)marginWidth,(int) marginHeight,(int) marginWidth,(int) marginHeight);
		backgroundView.setLayoutParams(params);
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
