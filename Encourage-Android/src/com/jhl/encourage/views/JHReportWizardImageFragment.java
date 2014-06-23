package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class JHReportWizardImageFragment extends Fragment implements JHReportFragment{

	private ImageView imageHolder = null;
	private TextView imageDate = null;
	private TextView imageName = null;
	private ProgressBar uploadProgress = null;
	private Contact contact;
	private int day, month, year;
	
	private String imagePath ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagereport, container, false);
		initView(v);
		contact = new Contact();
		return v;
	}
	
	private void initView(View v){
		imageHolder = (ImageView) v.findViewById(R.id.imageHolder);
		imageDate = (TextView) v.findViewById(R.id.imageDate);
		imageName = (TextView) v.findViewById(R.id.imageName);
		imageDate.setText(JHUtility.getFormattedDate());
		
//		imageName.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				imageName.requestFocusFromTouch();
//				return true;
//			}
//		});
//		
//		
//		imageName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				String text = imageName.getText().toString().trim();
//				if (!hasFocus) {
//					if(text.length() == 0){
//						imageName.setText("Event Name");
//					}
//				}else{
//					if (text.equals("Event Name")){
//						imageName.setText("");
//					}
//				}
//			}
//		});
		
		

		uploadProgress = (ProgressBar)v.findViewById(R.id.uploadProgress);
		uploadProgress.setVisibility(View.GONE);
	}
	
	public void showProgrees() {
		uploadProgress.setVisibility(View.VISIBLE);
	}
	
	public void endProgress() {
		uploadProgress.setVisibility(View.GONE);
	}
	
	public void setDate(String date){
		imageDate.setText(date);
	}

	public void setImage(String path) {
		Log.d(JHConstants.LOG_TAG, path);
		imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
		imagePath = path;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			 year = selectedYear;
			 month = selectedMonth;
			 day = selectedDay;

			// set selected date into textview
			imageDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			//imageDP.init(year, month, day, null);

		}
	};
	
	public String getDate(){
		String dateString = imageDate.getText().toString();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date date = formatter.parse(dateString);
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateString = formatter.format(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return	dateString;
	}
	
	public String getName() {
		return imageName.getText().toString();
	}
	
	@Override
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	@Override
	public Contact getContact() {
		return contact;
	}

}
