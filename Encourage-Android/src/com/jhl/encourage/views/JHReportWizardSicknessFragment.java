package com.jhl.encourage.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHUtility;

public class JHReportWizardSicknessFragment extends Fragment implements JHReportFragment{
	
	EditText sickDate;
	TextView sickDesc;
	private Contact contact;
	private JHSicknessButtonsAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sicknessreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.sicknessgrid);
//        gridView.setBackground(getResources().getDrawable(R.drawable.page_bg));
//        gridView.setBackgroundResource(R.drawable.page_bg);
		adapter = new JHSicknessButtonsAdapter(v.getContext());
		gridView.setAdapter(adapter);

		
//		TextView contactList = (TextView)v.findViewById(R.id.contactsList);
//		contactList.setText(JHAppStateVariables.getContactsListString());
		
		initViews(v);
		contact = new Contact();
		return v;
	}
	
	private void initViews  (View v) {
		sickDate = (EditText)v.findViewById(R.id.sickDate);
		sickDate.setText(JHUtility.getDatePickerDate());
		sickDesc = (TextView)v.findViewById(R.id.sickDesc);
		
//		sickDesc.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				sickDesc.requestFocusFromTouch();
//				return true;
//			}
//		});
//		
//		
//		sickDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				String text = sickDesc.getText().toString().trim();
//				if (!hasFocus) {
//					if(text.length() == 0){
//						sickDesc.setText("Enter a description");
//					}
//				}else{
//					if (text.equals("Enter a description")){
//						sickDesc.setText("");
//					}
//				}
//			}
//		});  ENTHONNADAI ITHU!!!!! CHUMMA ORU HINT KODUKKAN AAYT
		
	}
	
	public void clearButtonSelections(){
		adapter.clearButtonSelections();
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
		String dateString = sickDate.getText().toString();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
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
	
	public String getSickDesc(){
		String desc = sickDesc.getText().toString();
		if(desc.equals("Enter a description")){
			desc = "";
		}
		return desc;
	}
}
