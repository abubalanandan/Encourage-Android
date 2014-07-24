package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.internal.co;
import com.google.android.gms.maps.GoogleMap;
import com.jhl.encourage.R;
import com.jhl.encourage.imageloader.JHImageLoader;
import com.jhl.encourage.model.TimeLineItem;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHTimelineItemView;

public class JHTimelineAdapter extends BaseAdapter {

	Context ctx;
	private TextView dateField;
	private TextView nameField;
	private TextView fishCountField;
	public Boolean needLoadMoreMsg;
	private TextView msgField;
	private ArrayList<TimeLineItem> msgList;
	private GoogleMap map;
	private JHImageLoader imageLoader;

	public JHTimelineAdapter(Context context, ArrayList<TimeLineItem> list,
			Boolean needLoadMore,JHImageLoader imageLoader) {

		this.ctx = context;
		this.msgList = list;
		this.needLoadMoreMsg = needLoadMore;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {

//		if (needLoadMoreMsg || msgList.isEmpty())
//			return msgList.size() + 1;

		return msgList.size();
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.timeline_cell, parent, false);
		}
		
		
		TextView dateTV = (TextView)convertView.findViewById(R.id.dateAndTimeTV);
		TextView typeTV = (TextView)convertView.findViewById(R.id.typeTV);
		TextView profileNameTV = (TextView)convertView.findViewById(R.id.profileNameTV);
		
		dateTV.setText(JHUtility.getFormattedDate(msgList.get(position).getTimelineDate()));
		typeTV.setText(msgList.get(position).getType());
		profileNameTV.setText(msgList.get(position).getPerson());
		
		LinearLayout postLL = (LinearLayout) convertView
				.findViewById(R.id.postLL);
		TableLayout postDetailsTL = (TableLayout)convertView.findViewById(R.id.postDetailsTL);
		//postLL.removeAllViews();
		int count = postLL.getChildCount();
		postLL.removeViews(1, count-1);
		
		if(msgList.get(position).getDatatype().equalsIgnoreCase("image")){
			DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();	
			int width = metrics.widthPixels;
			LayoutParams params = new LayoutParams(width, width);

			ImageView imageView = new ImageView(ctx);
			imageView.setLayoutParams(params);
			postLL.addView(imageView);

		//	 imageView.setBackgroundColor(ctx.getResources().getColor(android.R.color.darker_gray));
			Log.d(JHConstants.LOG_TAG,  "IMAGE_URL ::: "+msgList.get(position).getFilename());

			imageLoader.DisplayImage(msgList.get(position).getFilename(), imageView);
		}else if(msgList.get(position).getDatatype().equalsIgnoreCase("map")){
			//String mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=1080x1920&markers=size:mid|color:red|" + msgList.get(position).getEventAddress();
			String mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=";
			try {
				String size = URLEncoder.encode("1080x1920", "UTF-8");
				String markers = URLEncoder.encode("size:mid|color:red|"+ msgList.get(position).getEventAddress(), "UTF-8");
				mapURL = mapURL + size + "&markers=" + markers;
			} catch (UnsupportedEncodingException e) {
				mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=1080x1920&markers=size:mid|color:red|" + msgList.get(position).getEventAddress();
			}
			Log.i("MAP!!!!!",mapURL);
			DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();	
			int width = metrics.widthPixels;
			LayoutParams params = new LayoutParams(width, width);

			ImageView imageView = new ImageView(ctx);
			imageView.setLayoutParams(params);
			imageView.setTag(msgList.get(position).getTimelineid());
			postLL.addView(imageView);

			Log.d(JHConstants.LOG_TAG,  "MAP_URL ::: "+mapURL);
			
			// imageView.setBackgroundColor(ctx.getResources().getColor(android.R.color.darker_gray));
			imageLoader.DisplayImage(mapURL, imageView);

		}
		
		Iterator iterator = msgList.get(position).getDetails().iterator();
		postDetailsTL.removeAllViews();
		while(iterator.hasNext()){
			String[] pairs = (String[])iterator.next();
			if(pairs!=null && pairs.length==2){
			TableRow.LayoutParams params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.bottomMargin = (int) ctx.getResources().getDimension(R.dimen.timeline_item_bottom_margin);
//			JHTimelineItemView itemView = new JHTimelineItemView(ctx);
//			itemView.setLayoutParams(params);
//			itemView.getKeyTV().setText(pairs.getKey());
//			itemView.getValueTV().setText(pairs.getValue());
//			postLL.addView(itemView);
			
			TableRow row = new TableRow(ctx);
			row.setLayoutParams(params);
			TextView keyTV = new TextView(ctx);
			keyTV.setLayoutParams(new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
			TextView valueTV = new TextView(ctx);
			valueTV.setLayoutParams(new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
			keyTV.setTextColor(Color.BLACK);
			valueTV.setTextColor(Color.BLACK);
			row.addView(keyTV);
			row.addView(valueTV);
			keyTV.setText(pairs[0]);
			valueTV.setText(pairs[1]);
			
			postDetailsTL.addView(row);
			}
			
	}
		
		
	
		

		return convertView;
	}

}
