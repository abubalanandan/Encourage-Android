package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter;
import com.jhl.encourage.utilities.JHUtility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class JHReportWizardEmotionalFragment extends Fragment {
	
	TextView emoDate;
	TextView emoDesc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.emotionalsreport, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.emotionalgrid);
		gridView.setAdapter(new JHSicknessButtonsAdapter(v.getContext()));
		initViews(v);
		return v;
	}
	
	private void initViews  (View v) {
		emoDate = (TextView)v.findViewById(R.id.emoDate);
		emoDate.setText(JHUtility.getFormattedDate());
		emoDesc = (TextView)v.findViewById(R.id.emoDesc);
		emoDesc.setText("Enter a description");
		
		emoDesc.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				emoDesc.requestFocusFromTouch();
				return true;
			}
		});
		
		
		emoDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String text = emoDesc.getText().toString().trim();
				if (!hasFocus) {
					if(text.length() == 0){
						emoDesc.setText("Enter a description");
					}
				}else{
					if (text.equals("Enter a description")){
						emoDesc.setText("");
					}
				}
			}
		});
		
	}
	
	public void setDate(String date){
		emoDate.setText(date);
	}
	
}
