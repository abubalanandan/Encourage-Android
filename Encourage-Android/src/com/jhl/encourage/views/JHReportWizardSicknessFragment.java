package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHRegistrationActivity;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHUtility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class JHReportWizardSicknessFragment extends Fragment implements JHReportFragment{
	
	TextView sickDate;
	TextView sickDesc;
	private Contact contact;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sicknessreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.sicknessgrid);
		gridView.setAdapter(new JHSicknessButtonsAdapter(v.getContext()));
		
//		TextView contactList = (TextView)v.findViewById(R.id.contactsList);
//		contactList.setText(JHAppStateVariables.getContactsListString());
		
		initViews(v);
		contact = new Contact();
		return v;
	}
	
	private void initViews  (View v) {
		sickDate = (TextView)v.findViewById(R.id.sickDate);
		sickDate.setText(JHUtility.getFormattedDate());
		sickDesc = (TextView)v.findViewById(R.id.sickDesc);
		sickDesc.setText("Enter a description");
		
		sickDesc.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sickDesc.requestFocusFromTouch();
				return true;
			}
		});
		
		
		sickDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = sickDesc.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						sickDesc.setText("Enter a description");
					}
				}else{
					if (text.equals("Enter a description")){
						sickDesc.setText("");
					}
				}
			}
		});
		
	}
	
	public void setDate(String date){
		sickDate.setText(date);
	}
	
	@Override
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	@Override
	public Contact getContact() {
		return contact;
	}
	
	public String getSickDate(){
		return sickDate.getText().toString();
	}
	
	public String getSickDesc(){
		String desc = sickDesc.getText().toString();
		if(desc.equals("Enter a description")){
			desc = "";
		}
		return desc;
	}
}
