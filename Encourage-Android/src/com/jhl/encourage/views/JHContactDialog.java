package com.jhl.encourage.views;


import android.app.Dialog;
import android.content.Context;


import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jhl.encourage.R;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Contact;



public class JHContactDialog extends Dialog {

	private JHReportFragment fragment;
	
	private TextView contactName1;
	private TextView contactEmail1;
	private CheckBox addtoCC1;
	private TextView contactName2;
	private TextView contactEmail2;
	private CheckBox addtoCC2;
	private Button btnContactSelected;
	
	public JHContactDialog(Context context, JHReportFragment fragment) {
		super(context, R.style.ThemeDialogCustom);
		this.fragment = fragment;
		setContentView(R.layout.contacts);
		
		initView();
		
	}
	
	private void initView(){
		
		contactName1 = (TextView) findViewById(R.id.contactName1);
		
		contactName1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				contactName1.requestFocusFromTouch();
				return true;
			}
		});
		
		
		contactName1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = contactName1.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						contactName1.setText("Enter name of person that you want to inform");
					}
				}else{
					if (text.equals("Enter name of person that you want to inform")){
						contactName1.setText("");
					}
				}
			}
		});
		
		contactEmail1 = (TextView) findViewById(R.id.contactEmail1);
		
		contactEmail1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				contactEmail1.requestFocusFromTouch();
				return true;
			}
		});
		
		
		contactEmail1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = contactEmail1.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						contactEmail1.setText("Enter email address");
					}
				}else{
					if (text.equals("Enter email address")){
						contactEmail1.setText("");
					}
				}
			}
		});
		
		contactName2 = (TextView) findViewById(R.id.contactName2);
		
		contactName2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				contactName2.requestFocusFromTouch();
				return true;
			}
		});
		
		
		contactName2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = contactName2.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						contactName2.setText("Enter name of person that you want to inform");
					}
				}else{
					if (text.equals("Enter name of person that you want to inform")){
						contactName2.setText("");
					}
				}
			}
		});
		
		contactEmail2 = (TextView) findViewById(R.id.contactEmail2);
		
		contactEmail2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				contactEmail2.requestFocusFromTouch();
				return true;
			}
		});
		
		
		contactEmail2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = contactEmail2.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						contactEmail2.setText("Enter email address");
					}
				}else{
					if (text.equals("Enter email address")){
						contactEmail2.setText("");
					}
				}
			}
		});
		
		addtoCC1 = (CheckBox) findViewById(R.id.addtoCC1);
		addtoCC2 = (CheckBox) findViewById(R.id.addtoCC2);
		
		btnContactSelected = (Button) findViewById(R.id.btnContactSelected);
		btnContactSelected.setOnClickListener(new ContactSelectedButtonClicked());
	}
	
	class ContactSelectedButtonClicked implements View.OnClickListener {
		
		@Override
		public void onClick(View v) {
//			Contact contact = new Contact();
//			contact.setName1(contactName1.getText().toString());
//			contact.setEmail1(contactEmail1.getText().toString());
//			contact.setName2(contactName2.getText().toString());
//			contact.setEmail2(contactEmail2.getText().toString());
//			contact.setAddToCC1(addtoCC1.isChecked());
//			contact.setAddToCC2(addtoCC2.isChecked());
//			
//			fragment.setContact(contact);
			cancel();
		}
	}
}
