package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHUtility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JHReportWizardMapFragment extends Fragment implements JHReportFragment{
	
	TextView mapReportDateText ;
	TextView mapReportNameText ;
	TextView mapReportAddressText ;
	TextView mapReportDescText ;
	
	private Contact contact;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.mapreport, container, false);
		initView(v);
		contact = new Contact();
		return v;
	}
	
	private void initView(View v) {
		mapReportDateText = (TextView) v.findViewById(R.id.mapReportDateText);
		
		mapReportDateText.setText(JHUtility.getFormattedDate());
		mapReportNameText = (TextView) v.findViewById(R.id.mapReportNameText);
		
				
		
		mapReportAddressText = (TextView) v.findViewById(R.id.mapReportAddressText);
		
				
		mapReportDescText = (TextView) v.findViewById(R.id.mapReportDescText);
		
	
	
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
	
	@Override
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	@Override
	public Contact getContact() {
		return contact;
	}
}	
