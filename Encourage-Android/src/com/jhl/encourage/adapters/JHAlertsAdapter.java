package com.jhl.encourage.adapters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.imageloader.JHImageLoader;
import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class JHAlertsAdapter extends ArrayAdapter<Notification> {
	private List<Notification> alerts;
	private Context context;
	private JHImageLoader imageLoader;

	public JHAlertsAdapter(Context context, int textViewResourceId,
			List<Notification> alerts) {
		super(context, textViewResourceId, alerts);
		this.context = context;
		this.alerts = alerts;
		imageLoader = new JHImageLoader(context.getApplicationContext());

		Collections.sort(this.alerts);
	}

	private class ViewHolder {
		TextView title;
		TextView dateTime;
		LinearLayout postLL;
		TableLayout postDetailsTL;
		LinearLayout cellLL;
	}

	public List<Notification> getAlerts() {
		return alerts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Log.d(JHConstants.LOG_TAG, String.valueOf(position));

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.alertitem, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.profileNameTV);
			holder.dateTime = (TextView) convertView
					.findViewById(R.id.dateAndTimeTV);
			holder.postLL = (LinearLayout) convertView
					.findViewById(R.id.postLL);
			holder.postDetailsTL = (TableLayout) convertView
					.findViewById(R.id.postDetailsTL);
			holder.cellLL = (LinearLayout) convertView.findViewById(R.id.alert_cell);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Alert alert = (Alert) alerts.get(position);

		holder.title.setText(org.apache.commons.lang3.StringEscapeUtils.unescapeXml(alert.getTitle()));
		holder.dateTime.setText(JHUtility.getFormattedDate(alert.getDateTime()));

		// holder.author.setTag(notification.getAuthorName());
		// holder.title.setTag(notification.getTitle());
		//
		/*if(alert.getReadStatus().equalsIgnoreCase(JHConstants.NOT_XML_KEY_READ_STATUS_UNREAD)) {
			holder.cellLL.setBackgroundColor(Color.parseColor("DDDCFB"));
		}*/
		int count = holder.postLL.getChildCount();
		holder.postLL.removeViews(1, count - 1);
		holder.postDetailsTL.removeAllViews();
		if (alert.getContenType().equals(
				JHConstants.NOT_XML_KEY_ALERT_TYPE_IMAGE)) {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			int width = metrics.widthPixels;
			LayoutParams params = new LayoutParams(width, width);

			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(params);
			holder.postLL.addView(imageView);
			String imageName = "https://tryencourage.com/hwdsi/hwAttachedfile/"
					+ JHAppStateVariables.getLoginTocken() + "/"
					+ alert.getImageName();
			imageLoader.DisplayImage(imageName, imageView);
		} else if (alert.getContenType().equals(
				JHConstants.NOT_XML_KEY_ALERT_TYPE_MAP)) {
			//String mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=1080x1920&markers=size:mid|color:red|" + alert.getAddress();
			
			String mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=";
			
			try {
				String size = URLEncoder.encode("1080x1920", "UTF-8");
				String markers = URLEncoder.encode("size:mid|color:red|"+ alert.getAddress(), "UTF-8");
				mapURL = mapURL + size + "&markers=" + markers;
			} catch (UnsupportedEncodingException e) {
				mapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=1080x1920&markers=size:mid|color:red|" + alert.getAddress();
			}
			
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			int width = metrics.widthPixels;
			LayoutParams params = new LayoutParams(width, width);

			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(params);
			holder.postLL.addView(imageView);

			imageLoader.DisplayImage(mapURL, imageView);

		} else if (alert.getContenType().equals(
				JHConstants.NOT_XML_KEY_ALERT_TYPE_LINK)) {
			
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = vi.inflate(R.layout.alert_link_item, null);
			
//			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//			int width = metrics.widthPixels;
//			LayoutParams params = new LayoutParams(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
//			
//			view.setLayoutParams(params);
			ImageView imageView = (ImageView)view.findViewById(R.id.linkImage);
			TextView linkTitleTV = (TextView)view.findViewById(R.id.linkTitleTV);
			TextView linkPreviewTV = (TextView)view.findViewById(R.id.linkPreviewTV);
			
			linkTitleTV.setText(org.apache.commons.lang3.StringEscapeUtils.unescapeXml(alert.getUrlHeader()));
			linkPreviewTV.setText(alert.getDetails());
			String imageName =  alert.getUrlImage();
//			TableRow.LayoutParams params = new TableRow.LayoutParams(
//					TableRow.LayoutParams.MATCH_PARENT,
//					TableRow.LayoutParams.WRAP_CONTENT);
//			TableRow row = new TableRow(context);
//			row.setLayoutParams(params);
//			TextView linkTV = new TextView(context);
//			row.addView(linkTV);
//			linkTV.setMovementMethod(LinkMovementMethod.getInstance());
//			linkTV.setText(Html.fromHtml("<a href='"+alert.getUrl()+"'>" + alert.getUrl() + "</a>"));
			holder.postLL.addView(view);
			imageLoader.DisplayImage(imageName, imageView);
		}

		if(!alert.getContenType().equals(JHConstants.NOT_XML_KEY_ALERT_TYPE_LINK)){
		TableRow.LayoutParams params = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		TableRow row = new TableRow(context);
		row.setLayoutParams(params);
		TextView linkTV = new TextView(context);
		row.addView(linkTV);
		linkTV.setText(alert.getDetails());
		holder.postDetailsTL.addView(row);
		}
		return convertView;

	}

	@Override
	public int getCount() {
		return alerts.size();
	}
}
