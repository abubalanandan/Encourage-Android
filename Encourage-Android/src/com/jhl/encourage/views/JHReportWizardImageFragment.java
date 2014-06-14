package com.jhl.encourage.views;

import com.jhl.encourage.R;
import com.jhl.encourage.utilities.JHConstants;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class JHReportWizardImageFragment extends Fragment {

	
	private ImageView imageHolder = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagereport, container, false);
		
		imageHolder = (ImageView) v.findViewById(R.id.imageHolder);
		
		return v;
	}
	
	
	
	public void setImage(String path){
		Log.d(JHConstants.LOG_TAG, path);
		imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
	}
}
