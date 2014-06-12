package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.R.id;
import com.jhl.encourage.R.layout;
import com.jhl.encourage.adapters.JHReportWizardPageAdapter;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.views.JHReportWizardEmotionalFragment;
import com.jhl.encourage.views.JHReportWizardImageFragment;
import com.jhl.encourage.views.JHReportWizardMapFragment;
import com.jhl.encourage.views.JHReportWizardSicknessFragment;
import com.jhl.encourage.views.JHReportWizardVideoFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Contacts.People;

public class JHReportWizardActivity extends FragmentActivity {

	JHReportWizardPageAdapter pageAdapter;
	private ProgressBar spinner;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.reportwizard);
	    List<Fragment> fragments = getFragments();
	    pageAdapter = new JHReportWizardPageAdapter(getSupportFragmentManager(), fragments);
	    ViewPager pager = (ViewPager)findViewById(R.id.reportpager);
	    pager.setAdapter(pageAdapter);
	    
	    RelativeLayout contactLayout = (RelativeLayout)findViewById(R.id.rwContactButton);
	    contactLayout.setOnClickListener(new ContactClicked());
	    
	    TextView contactList = (TextView)findViewById(R.id.contactsList);
	    contactList.setText(JHAppStateVariables.getContactsListString());
	    
	    spinner = (ProgressBar)findViewById(R.id.pbContact);
		spinner.setVisibility(View.GONE);
	    
	   
	}

	private List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new JHReportWizardSicknessFragment());
		fragments.add(new JHReportWizardEmotionalFragment());
		fragments.add(new JHReportWizardImageFragment());
		fragments.add(new JHReportWizardVideoFragment());
		fragments.add(new JHReportWizardMapFragment());
		Log.d(JHConstants.LOG_TAG, fragments+"");
		return fragments;
	}
	
	
	class ContactClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			new ContactLoadTask().execute();
		}
	}
	
	class ContactLoadTask extends AsyncTask<String, Integer, Boolean> {
	    @Override
	    protected void onPreExecute() {
	    	spinner.setVisibility(View.VISIBLE);
	        super.onPreExecute();
	    }

	    @Override
	    protected void onPostExecute(Boolean result) {
	    	try{
			    Intent i = new Intent(JHReportWizardActivity.this, JHContactPickerActivity.class);
			    startActivity(i);
			    }
			    catch(Exception ex)
			    {
			        Log.e("main",ex.toString());
			    }
	    }

	    @Override
	    protected Boolean doInBackground(String... params) {
	    	Contact contact;
	    	List<Contact> contacts = new ArrayList<Contact>();
	    	ContentResolver cr = getContentResolver();
	        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
	        if (cur.getCount() > 0) {
	            while (cur.moveToNext()) {
	                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
	                Cursor cur1 = cr.query( 
	                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
	                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
	                                new String[]{id}, null); 
	                while (cur1.moveToNext()) { 
	                    //to get the contact names
	                    String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//	                    Log.e("Name :", name);
	                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//	                    Log.e("Email", email);
	                    if(email!=null){
	                    	contact = new Contact(name, email, false);
	                    	contacts.add(contact);	
	                    }
	                } 
	                cur1.close();
	            }
	        }
	        JHAppStateVariables.setContacts(contacts);
	        Log.d(JHConstants.LOG_TAG, "doInBackground done");
	        
	        return true;
	    }
	}	
}
