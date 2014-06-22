package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHEmotonalButtonsAdapter;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHUtility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class JHReportWizardEmotionalFragment extends Fragment implements JHReportFragment{
	
	TextView emoDate;
	TextView emoDesc;
	
	private Contact contact;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.emotionalsreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.emotionalgrid);
       // gridView.setBackground(getResources().getDrawable(R.drawable.page_bg));
		gridView.setAdapter(new JHEmotonalButtonsAdapter(v.getContext()));
		initViews(v);
		contact = new Contact();
		return v;
	}
	
	private void initViews  (View v) {
		emoDate = (TextView)v.findViewById(R.id.emoDate);
		emoDate.setText(JHUtility.getFormattedDate());
		emoDesc = (TextView)v.findViewById(R.id.emoDesc);
		emoDesc.setText("Enter a description");
		
		emoDesc.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				emoDesc.requestFocusFromTouch();
				return true;
			}
		});
		
		
		emoDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = emoDesc.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						emoDesc.setText("Enter a description");
					}
				}else{
					if (text.equals("Enter a description")){
						emoDesc.setText("");
					}
				}
			}
		});
		
	}
	
	public void setDate(String date){
		emoDate.setText(date);
	}
	
	@Override
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	@Override
	public Contact getContact() {
		return contact;
	}
	
	public String getEmoDate(){


			String dateString = emoDate.getText().toString();
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
	
	public String getEmoDesc(){
		String desc = emoDesc.getText().toString();
		if(desc.equals("Enter a description")){
			desc = "";
		}
		return desc;
	}
	
}
