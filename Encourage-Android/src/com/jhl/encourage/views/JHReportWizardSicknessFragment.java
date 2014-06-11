package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class JHReportWizardSicknessFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sicknessreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.sicknessgrid);
        gridView.setAdapter(new JHSicknessButtonsAdapter(v.getContext()));
		return v;
	}
}
