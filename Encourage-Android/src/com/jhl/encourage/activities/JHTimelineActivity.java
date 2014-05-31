package com.jhl.encourage.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHTimelineAdapter;

public class JHTimelineActivity extends Activity{

	private ListView timelineView;
	private JHTimelineAdapter adapter;
	private ArrayList<String> list = new ArrayList<>(3);
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		for(int i =0;i<9;i++){
			list.add("lala");
		}
		initViews();
	}
	
	private void initViews(){
		timelineView = (ListView)findViewById(R.id.timeLineView);
		adapter = new JHTimelineAdapter(this, list, false);
		timelineView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	
	
	
}
