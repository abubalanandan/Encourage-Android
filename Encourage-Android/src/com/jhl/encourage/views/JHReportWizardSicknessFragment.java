package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHRegistrationActivity;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;
import com.jhl.encourage.utilities.JHAppStateVariables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class JHReportWizardSicknessFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sicknessreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.sicknessgrid);
		gridView.setAdapter(new JHSicknessButtonsAdapter(v.getContext()));
		
		TextView contactList = (TextView)v.findViewById(R.id.contactsList);
		contactList.setText(JHAppStateVariables.getContactsListString());
		
		return v;
	}
	
	
}
