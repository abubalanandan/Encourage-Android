package com.jhl.encourage.activities;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHContactAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;

public class JHContactPickerActivity extends Activity {

	private JHContactAdapter contactAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactpicker);
		displayContacts();

	}

	public void contatDoneButtonClicked(View view) {
		try {

			List<Contact> selectedContacts = new ArrayList<Contact>();
			List<Contact> contacts = contactAdapter.getContacts();
			for (int i = 0; i < contacts.size(); i++) {
				Contact contact = contacts.get(i);
				if (contact.isSelected()) {
					selectedContacts.add(contact);
				}
			}

			Log.d(JHConstants.LOG_TAG, "Contacts " + selectedContacts.toString());

			JHAppStateVariables.setSelectedContacts(selectedContacts);
			finish();
			overridePendingTransition(0, R.anim.slide_down);
			
		} catch (Exception ex) {
			Log.e("main", ex.toString());
		}

	}

	private void displayContacts() {

		List<Contact> contacts = JHAppStateVariables.getContacts();

		// Cursor contactsCursor =
		// getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		// null, null, null, null);
		// Contact contact ;
		// while (contactsCursor.moveToNext()) {
		//
		// String email = contactsCursor
		// .getString(contactsCursor
		// .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
		// if(email != null && ! email.trim().equals("")){
		// String name = contactsCursor
		// .getString(contactsCursor
		// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		// contact = new Contact(name, email, false);
		// contacts.add(contact);
		// }
		// }
		// contactsCursor.close();
		Contact contact;

		// create an ArrayAdaptar from the String Array
		contactAdapter = new JHContactAdapter(this, R.layout.contactitem,
				contacts);
		ListView listView = (ListView) findViewById(R.id.contactsList);
		// Assign adapter to ListView
		listView.setAdapter(contactAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Contact contact = (Contact) parent.getItemAtPosition(position);

			}
		});
	}

	public void closeContactButtonPressed(View view) {
		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Warning").setMessage("Are you sure you want to clear the selected contacts?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				JHAppStateVariables.setSelectedContacts(null);
				finish();
				overridePendingTransition(0, R.anim.slide_down);
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).create();
		dialog.show();
		
		
	}

	@Override
	public void onBackPressed(){
		
	}
}
