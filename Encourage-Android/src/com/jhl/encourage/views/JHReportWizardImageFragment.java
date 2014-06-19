package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jhl.encourage.R;
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
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class JHReportWizardImageFragment extends Fragment {

	private ImageView imageHolder = null;
	private TextView imageDate = null;
	private TextView imageName = null;
	private ProgressBar uploadProgress = null;
	
	//private DatePicker imageDP;
	private int day, month, year;
	
	private String imagePath ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagereport, container, false);

		imageHolder = (ImageView) v.findViewById(R.id.imageHolder);
		imageDate = (TextView) v.findViewById(R.id.imageDate);
		imageName = (TextView) v.findViewById(R.id.imageName);

		imageDate.setText(JHUtility.getFormattedDate());
		
		imageDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		imageName.setOnFocusChangeListener(new ImageNameFocusChangeLister());

		uploadProgress = (ProgressBar)v.findViewById(R.id.uploadProgress);
		uploadProgress.setVisibility(View.GONE);

		setCurrentDate();

		return v;
	}
	
	public void showProgrees() {
		uploadProgress.setVisibility(View.VISIBLE);
	}
	
	public void endProgress() {
		uploadProgress.setVisibility(View.GONE);
	}
	
	public void setCurrentDate() {

		final Calendar c = Calendar.getInstance();
		 year = c.get(Calendar.YEAR);
		 month = c.get(Calendar.MONTH);
		 day = c.get(Calendar.DAY_OF_MONTH);

		//imageDP.init(year, month, day, null);

	}

	public void setImage(String path) {
		Log.d(JHConstants.LOG_TAG, path);
		imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
		imagePath = path;
	}

	class ImageNameFocusChangeLister implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				imageName.setText("");
			} else {
				imageName.setText("Event Name");
			}

		}
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

}
