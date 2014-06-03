package com.jhl.encourage.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.jhl.encourage.R;
import com.jhl.encourage.views.JHTimelineItemView;

public class JHTimelineAdapter extends BaseAdapter {

	Context ctx;
	private TextView dateField;
	private TextView nameField;
	private TextView fishCountField;
	public Boolean needLoadMoreMsg;
	private TextView msgField;
	private ArrayList<String> msgList;

	public JHTimelineAdapter(Context context, ArrayList<String> list,
			Boolean needLoadMore) {

		this.ctx = context;
		this.msgList = list;
		this.needLoadMoreMsg = needLoadMore;
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

		if (convertView == null)
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.timeline_cell, parent, false);
		
		LinearLayout postLL = (LinearLayout) convertView
				.findViewById(R.id.postLL);
		postLL.removeAllViews();
		for (int i = 0; i < position; i++) {
			LayoutParams params = new LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			JHTimelineItemView itemView = new JHTimelineItemView(ctx);
			itemView.setLayoutParams(params);
			postLL.addView(itemView);
		}

		// dateField = (TextView) convertView.findViewById(R.id.dateField);
		// nameField = (TextView) convertView.findViewById(R.id.nameField);
		// fishCountField = (TextView)
		// convertView.findViewById(R.id.fishCountField);
		// msgField = (TextView) convertView.findViewById(R.id.msgField);

		// if ((msgList.isEmpty()) || (needLoadMoreMsg && position ==
		// msgList.size())) {
		//
		// msgField.setVisibility(View.VISIBLE);
		// dateField.setVisibility(View.GONE);
		// nameField.setVisibility(View.GONE);
		// fishCountField.setVisibility(View.GONE);
		//
		// String msgText = (msgList.isEmpty()) ? FHConstants.kNoMsgs :
		// FHConstants.kLoadMoreMsgs;
		// msgField.setText(msgText);
		// }
		// else {
		//
		// msgField.setVisibility(View.GONE);
		// dateField.setVisibility(View.VISIBLE);
		// nameField.setVisibility(View.VISIBLE);
		// fishCountField.setVisibility(View.VISIBLE);
		//
		// FHMessageDetail msg = (FHMessageDetail) msgList.get(position);
		// String formattedDate =
		// FHUtility.formattedStringFromDateString(msg.getDate(),
		// FHConstants.kMsgListResponseTimeFormat,
		// FHConstants.kMsgListTimeFormat);
		//
		// dateField.setText(formattedDate);
		// nameField.setText(msg.getName());
		// fishCountField.setText(msg.getFishcount().toString());
		//
		// if (msg.getUnread() != null && msg.getUnread().equalsIgnoreCase("Y"))
		// {
		//
		// dateField.setText(Html.fromHtml("<b>"+formattedDate+"</b>"));
		// nameField.setText(Html.fromHtml("<b>"+msg.getName()+"</b>"));
		// fishCountField.setText(Html.fromHtml("<b>"+msg.getFishcount()+"</b>"));
		// }
		// else {
		//
		// dateField.setText(formattedDate);
		// nameField.setText(msg.getName());
		// fishCountField.setText(msg.getFishcount().toString());
		// }
		// }

		return convertView;
	}

}
