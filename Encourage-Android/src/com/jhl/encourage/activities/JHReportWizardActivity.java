package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.R.id;
import com.jhl.encourage.R.layout;
import com.jhl.encourage.adapters.JHReportWizardPageAdapter;
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
import android.widget.RelativeLayout;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;

public class JHReportWizardActivity extends FragmentActivity {

	JHReportWizardPageAdapter pageAdapter;
	
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
		public void onClick(View v) {
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
		            Contacts.CONTENT_URI);
		    startActivityForResult(contactPickerIntent, 1001);
			
		}
	}
}
