package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JHReportWizardVideoFragment extends Fragment implements JHReportFragment{
	
//	public static final JHReportWizardEmotionalFragment getInstance(String message) {
//		JHReportWizardEmotionalFragment fragment = new JHReportWizardEmotionalFragment();
//		return  fragment;
//	}
	private Contact contact;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.videoreport, container, false);
		contact = new Contact();
		return v;
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
