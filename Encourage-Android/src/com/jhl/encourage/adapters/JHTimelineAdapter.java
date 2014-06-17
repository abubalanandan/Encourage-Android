package com.jhl.encourage.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.jhl.encourage.R;
import com.jhl.encourage.imageloader.JHImageLoader;
import com.jhl.encourage.views.JHTimelineItemView;

public class JHTimelineAdapter extends BaseAdapter {

	Context ctx;
	private TextView dateField;
	private TextView nameField;
	private TextView fishCountField;
	public Boolean needLoadMoreMsg;
	private TextView msgField;
	private ArrayList<String> msgList;
	private GoogleMap map;
	private JHImageLoader imageLoader;

	public JHTimelineAdapter(Context context, ArrayList<String> list,
			Boolean needLoadMore) {

		this.ctx = context;
		this.msgList = list;
		this.needLoadMoreMsg = needLoadMore;
		imageLoader = new JHImageLoader(ctx.getApplicationContext());
	}

	@Override
	public int getCount() {

		if (needLoadMoreMsg || msgList.isEmpty())
			return msgList.size() + 1;

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
		LinearLayout postLL = (LinearLayout) convertView
				.findViewById(R.id.postLL);
		postLL.removeAllViews();
		if (position == 4) {
			for (int i = 0; i < position; i++) {
				LayoutParams params = new LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				JHTimelineItemView itemView = new JHTimelineItemView(ctx);
				itemView.setLayoutParams(params);
				postLL.addView(itemView);
			}
		}  else {

		//	int width = postLL.getLayoutParams().width;
			
			DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();	
			int width = metrics.widthPixels;
			LayoutParams params = new LayoutParams(width, width);

			ImageView imageView = new ImageView(ctx);
			imageView.setLayoutParams(params);
			postLL.addView(imageView);

			// imageView.setBackgroundColor(ctx.getResources().getColor(android.R.color.darker_gray));
			imageLoader.DisplayImage(msgList.get(position), imageView);

		}

		return convertView;
	}

}
