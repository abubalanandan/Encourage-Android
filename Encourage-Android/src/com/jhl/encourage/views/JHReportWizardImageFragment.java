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
	
//	public static final JHReportWizardEmotionalFragment getInstance(String message) {
//		JHReportWizardEmotionalFragment fragment = new JHReportWizardEmotionalFragment();
//		return  fragment;
//	}
	
	private ImageView imageHolder = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagereport, container, false);
		
		ImageButton imageButton = (ImageButton)v.findViewById(R.id.imageButton);
		imageButton.setOnClickListener(new IBClickListener());
		
		imageHolder = (ImageView) v.findViewById(R.id.imageHolder);
		
		return v;
	}
	
	class IBClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			
			Log.d(JHConstants.LOG_TAG, "my request code "+JHConstants.REQUEST_CODE_IMAGE_LIB);
			getActivity().startActivityForResult(i, JHConstants.REQUEST_CODE_IMAGE_LIB);
		}
	}
	
	public void setImage(String path){
		Log.d(JHConstants.LOG_TAG, path);
		imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
	}
}
