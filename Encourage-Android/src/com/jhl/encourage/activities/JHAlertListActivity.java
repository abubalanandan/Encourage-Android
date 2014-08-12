package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHAlertsAdapter;
import com.jhl.encourage.apis.AlertsService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.apis.TimeLineService;
import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHGPSTracker;
import com.jhl.encourage.utilities.JHUtility;

public class JHAlertListActivity extends Activity {
	ListView listView;
	ProgressBar dialog;
	public static int position = 0;
	public static String id;
	List<Notification> alerts = null;
	JHAlertsAdapter alertAdapter;
	ListView alertsList;
	private boolean isLoading = false;
	private int currentVisibleItem = 0;
	private int currentScrollState = 0;
	private int currentVisibleItemCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alerts);

		// final List<Notification> alerts = new
		// ArrayList<Notification>(JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT));
		alerts = new ArrayList<Notification>();
		Collections.sort(alerts);

		alertAdapter = new JHAlertsAdapter(this, R.layout.alertitem, alerts);

		alertsList = (ListView) findViewById(R.id.alertListView);
		dialog = new ProgressBar(this);
		dialog.setProgressDrawable(getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal));

		alertsList.addFooterView(dialog);
		alertsList.setAdapter(alertAdapter);

		alertsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Alert alert = (Alert) alerts.get(position);
				if (alert.getContenType().equals(
						JHConstants.NOT_XML_KEY_ALERT_TYPE_LINK)) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(alert.getUrl()));
					startActivity(intent);

				}
			}
		});
		alertsList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				JHAlertListActivity.this.currentScrollState = scrollState;
				isScrollCompleted();

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				JHAlertListActivity.this.currentVisibleItem = firstVisibleItem;
				JHAlertListActivity.this.currentVisibleItemCount = visibleItemCount;
				isScrollCompleted();
			}

			private void isScrollCompleted() {
				if (JHAlertListActivity.this.currentScrollState == SCROLL_STATE_FLING
						&& (JHAlertListActivity.this.currentVisibleItem + JHAlertListActivity.this.currentVisibleItemCount) == (alerts
								.size())) {
					if (alertsList.getFooterViewsCount() <= 0) {
						if (!isLoading) {
							alertsList.addFooterView(dialog);
							invokeAlertsApi("" + alerts.size());

						}
					}
				}
			}
		});

		invokeAlertsApi("0");

	}

	private void invokeAlertsApi(final String start) {
		isLoading = true;
		AlertsService service = EncourageApplication.getRestAdapter().create(
				AlertsService.class);
		JHGPSTracker gpsTracker = JHGPSTracker
				.getGPSTracker(JHAlertListActivity.this);
		Location location = gpsTracker.getLocation();
		String latitude = "";
		String longitude = "";
		if (location != null) {
			latitude = location.getLatitude() + "";
			longitude = location.getLongitude() + "";
		}
		service.getAlertDetails("getUserAlertsDetails",
				JHAppStateVariables.getLoginTocken(), JHUtility.getDateTime(),
				JHUtility.getTimeZoneString(),latitude,longitude,start,
				new Callback<SpocResponse>() {
					public void success(SpocResponse spocResponse,
							retrofit.client.Response arg1) {
						ArrayList<SpocObject> responseList = spocResponse
								.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode()
									.equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject
										.getMap();
								String success = map.get("success");
								if (success.equalsIgnoreCase("true")) {
									System.out.println("success");
									isLoading = false;
									if (alertsList.getFooterViewsCount() > 0)

										alertsList.removeFooterView(dialog);

								} else {
									isLoading = false;
									if (alertsList.getFooterViewsCount() > 0)

										alertsList.removeFooterView(dialog);
									System.out.println("failure");

								}
							} else if (spocObject.getResultTypeCode()
									.equalsIgnoreCase("READONLY_FORM")) {
								HashMap<String, String> map = spocObject
										.getMap();
								if (map.containsKey("lastcount")) {
									// lastCount =
									// Integer.parseInt(map.get("lastcount"));
								} else {
									Alert alert = new Alert();
									if (map.get(
											JHConstants.NOT_XML_KEY_ALERT_TYPE)
											.contains("Map")) {
										alert.setContenType(JHConstants.NOT_XML_KEY_ALERT_TYPE_MAP);
									} else if (map.get(
											JHConstants.NOT_XML_KEY_ALERT_TYPE)
											.contains("Link")) {
										alert.setContenType(JHConstants.NOT_XML_KEY_ALERT_TYPE_LINK);
									} else if (map.get(
											JHConstants.NOT_XML_KEY_ALERT_TYPE)
											.contains("Note")) {
										alert.setContenType(JHConstants.NOT_XML_KEY_ALERT_TYPE_NOTE);
									} else if (map.get(
											JHConstants.NOT_XML_KEY_ALERT_TYPE)
											.contains("Image")) {
										alert.setContenType(JHConstants.NOT_XML_KEY_ALERT_TYPE_IMAGE);
									}

									alert.setId(map
											.get(JHConstants.NOT_XML_KEY_ALERT_KEY));
									alert.setNotificationType(map
											.get(JHConstants.NOT_XML_KEY_TYPE));
									alert.setAuthorName(map
											.get(JHConstants.NOT_XML_KEY_AUTHOR_NAME));
									alert.setContenType(map
											.get(JHConstants.NOT_XML_KEY_CONTENT_TYPE));
									alert.setDateTime(map
											.get(JHConstants.NOT_XML_KEY_DATE_TIME));
									alert.setDateTimeDiff(map
											.get(JHConstants.NOT_XML_KEY_DATE_TIME_DIFF));
									alert.setDetails(map
											.get(JHConstants.NOT_XML_KEY_DETAILS));
									alert.setNotificationType(map
											.get(JHConstants.NOT_XML_KEY_TYPE));
									alert.setReadStatus(map
											.get(JHConstants.NOT_XML_KEY_READ_STATUS));
									alert.setTitle(map
											.get(JHConstants.NOT_XML_KEY_TITLE));

									alert.setAlertType(map
											.get(JHConstants.NOT_XML_KEY_ALERT_TYPE));

									String url = map
											.get(JHConstants.NOT_XML_KEY_ALERT_LINK_KEY);
									String address = map
											.get(JHConstants.NOT_XML_KEY_ALERT_MAP_KEY);
									String imageName = map
											.get(JHConstants.NOT_XML_KEY_ALERT_IMAGE_KEY);
									String authorProfilePicName = map
											.get("author_profilepic_name");
									String header = map.get("header");
									String urlImage = map.get("image");

									if (url != null && !url.trim().equals("")) {
										alert.setUrl(url);
										alert.setAuthorProfilePicName(authorProfilePicName);
										alert.setUrlHeader(header);
										alert.setUrlImage(urlImage);

									}

									if (address != null
											&& !address.trim().equals("")) {
										alert.setAddress(address);
									}

									if (imageName != null
											&& !imageName.trim().equals("")) {
										alert.setImageName(imageName);
									}
									alerts.add(alert);
									Collections.sort(alerts);
									alertAdapter.notifyDataSetChanged();
								}
							}
						}

						if (start.equalsIgnoreCase("0")) {
							boolean flag = false;
							int i = 0;
							for (i = 0; i < alerts.size(); i++) {

								if (alerts.get(i).getId().equalsIgnoreCase(id)) {
									flag = true;
									break;
								}
							}

							if (flag) {
								alertsList.setSelection(i);
							}

						}

					}

					public void failure(retrofit.RetrofitError arg0) {
						isLoading = false;
						if (alertsList.getFooterViewsCount() > 0)
							alertsList.removeFooterView(dialog);
					}

				});
	}

	public void closeButtonPressed(View view) {
		finish();
	}
}
