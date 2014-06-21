package com.jhl.encourage.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhl.encourage.R;

public class JHTimelineItemView extends RelativeLayout{

	private TextView keyTV;
	private TextView valueTV;
	
	public JHTimelineItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) context.getSystemService(infService);
		li.inflate(R.layout.timeline_item, this, true);
		//initViews();
	}
	
//	private void initViews(){
//		keyTV = (TextView)findViewById(R.id.keyTV);
//		valueTV = (TextView)findViewById(R.id.valueTV);
//		
//		
//	}

	public TextView getKeyTV() {
		return keyTV;
	}

	public TextView getValueTV() {
		return valueTV;
	}

}
