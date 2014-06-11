package com.jhl.encourage.views;

import com.jhl.encourage.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JHReportWizardEmotionalFragment extends Fragment {
	
//	public static final JHReportWizardEmotionalFragment getInstance(String message) {
//		JHReportWizardEmotionalFragment fragment = new JHReportWizardEmotionalFragment();
//		return  fragment;
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.emotionalsreport, container, false);
		return v;
	}
}
