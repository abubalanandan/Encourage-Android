package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jhl.encourage.R;
import com.jhl.encourage.utilities.JHUtility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JHReportWizardMapFragment extends Fragment {
	
	TextView mapReportDateText ;
	TextView mapReportNameText ;
	TextView mapReportAddressText ;
	TextView mapReportDescText ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.mapreport, container, false);
		initView(v);
		return v;
	}
	
	private void initView(View v) {
		mapReportDateText = (TextView) v.findViewById(R.id.mapReportDateText);
		
		mapReportDateText.setText(JHUtility.getFormattedDate());
		mapReportNameText = (TextView) v.findViewById(R.id.mapReportNameText);
		mapReportNameText.setText("Event Name");
		
		mapReportNameText.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mapReportNameText.requestFocusFromTouch();
				return true;
			}
		});
		
		
		mapReportNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = mapReportNameText.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						mapReportNameText.setText("Event Name");
					}
				}else{
					if (text.equals("Event Name")){
						mapReportNameText.setText("");
					}
				}
			}
		});
		
		
		
		mapReportAddressText = (TextView) v.findViewById(R.id.mapReportAddressText);
		mapReportAddressText.setText("Event Address");
		
		mapReportAddressText.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mapReportAddressText.requestFocusFromTouch();
				return true;
			}
		});
		
		
		mapReportAddressText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = mapReportAddressText.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						mapReportAddressText.setText("Event Address");
					}
				}else{
					if (text.equals("Event Address")){
						mapReportAddressText.setText("");
					}
				}
			}
		});
		
		mapReportDescText = (TextView) v.findViewById(R.id.mapReportDescText);
		mapReportDescText.setText("Event Description");
		
		mapReportDescText.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mapReportDescText.requestFocusFromTouch();
				return true;
			}
		});
		
		
		mapReportDescText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = mapReportDescText.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						mapReportDescText.setText("Event Description");
					}
				}else{
					if (text.equals("Event Description")){
						mapReportDescText.setText("");
					}
				}
			}
		});
		
	
	}
	
	public String getDate(){
		String dateString = mapReportDateText.getText().toString();
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
		return mapReportNameText.getText().toString();
	}
	
	public String getAddress() {
		return mapReportAddressText.getText().toString();
	}
	
	public String getDescription() {
		return mapReportDescText.getText().toString();
	}
	
	public void setDate(String date){
		mapReportDateText.setText(date);
	}
}	
