package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHContactAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;


public class JHContactPickerActivity extends Activity {
	
	private JHContactAdapter contactAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.contactpicker);
		 displayContacts();
		// checkButtonClick();
		 
		 ImageButton closeButton = (ImageButton)findViewById(R.id.contatDoneButton);
		 closeButton.setOnClickListener(new CloseClicked());
		 
		 
	}
	
	private void displayContacts() {
		 
		  List<Contact> contacts = JHAppStateVariables.getContacts();

		 
		  
//		  Cursor contactsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//		  Contact contact ; 
//			while (contactsCursor.moveToNext()) {
//				
//				String email = contactsCursor
//						.getString(contactsCursor
//								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//				if(email != null && ! email.trim().equals("")){
//					String name = contactsCursor
//						.getString(contactsCursor
//								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//					contact = new Contact(name, email, false);
//					contacts.add(contact);	
//				}
//			}
//			contactsCursor.close();
		  Contact contact ; 
		  
		  
		  
		  //create an ArrayAdaptar from the String Array
		  contactAdapter = new JHContactAdapter(this,
		    R.layout.contactitem, contacts);
		  ListView listView = (ListView) findViewById(R.id.contactsList);
		  // Assign adapter to ListView
		  listView.setAdapter(contactAdapter);
		 
		 
		  listView.setOnItemClickListener(new OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  // When clicked, show a toast with the TextView text
				  Contact contact = (Contact) parent.getItemAtPosition(position);
				 
			  }
		  	});
	}
	
	class CloseClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			try{
				
					StringBuffer contactList = new StringBuffer();
				 
					List<Contact> contacts = contactAdapter.getContacts();
					for(int i=0;i<contacts.size();i++){
						Contact contact = contacts.get(i);
						if(contact.isSelected()){
							contactList.append(contact.getEmail() + ";");
						}
					}
					
					Log.d(JHConstants.LOG_TAG, "Contacts " + contactList.toString());
					
					JHAppStateVariables.setContactsListString(contactList.toString());
			    Intent i = new Intent(JHContactPickerActivity.this, JHReportWizardActivity.class);
			    startActivity(i);
			}
			catch(Exception ex)
			{
				Log.e("main",ex.toString());
			}
		}
	}
	
}
