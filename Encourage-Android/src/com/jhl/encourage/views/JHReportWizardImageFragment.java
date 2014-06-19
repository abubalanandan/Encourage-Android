package com.jhl.encourage.views;

import java.util.Calendar;

import com.jhl.encourage.R;
import com.jhl.encourage.utilities.JHConstants;

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
import android.widget.TextView;

public class JHReportWizardImageFragment extends Fragment {

	private ImageView imageHolder = null;
	private TextView imageDate = null;
	private TextView imageName = null;

	private DatePicker imageDP;
	private int day, month, year;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagereport, container, false);

		imageHolder = (ImageView) v.findViewById(R.id.imageHolder);
		imageDate = (TextView) v.findViewById(R.id.imageDate);
		imageName = (TextView) v.findViewById(R.id.imageName);

		imageDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		imageName.setOnFocusChangeListener(new ImageNameFocusChangeLister());

		imageDP = (DatePicker) v.findViewById(R.id.imageDP);

		setCurrentDate();

		return v;
	}

	public void setCurrentDate() {

		final Calendar c = Calendar.getInstance();
		 year = c.get(Calendar.YEAR);
		 month = c.get(Calendar.MONTH);
		 day = c.get(Calendar.DAY_OF_MONTH);

		imageDP.init(year, month, day, null);

	}

	public void setImage(String path) {
		Log.d(JHConstants.LOG_TAG, path);
		imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
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
			imageDP.init(year, month, day, null);

		}
	};

}
