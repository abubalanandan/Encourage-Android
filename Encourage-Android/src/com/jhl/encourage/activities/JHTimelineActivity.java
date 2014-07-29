package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHTimelineAdapter;
import com.jhl.encourage.apis.LogoutService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.apis.TimeLineService;
import com.jhl.encourage.apis.VersionCheckService;
import com.jhl.encourage.imageloader.JHImageLoader;
import com.jhl.encourage.model.TimeLineItem;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHGPSTracker;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHAlertsTeaserDialog;
import com.jhl.encourage.views.JHCareTasksDialog;

public class JHTimelineActivity extends Activity {

	private ListView timelineView;
	private JHTimelineAdapter adapter;
	private ArrayList<TimeLineItem> list = new ArrayList<TimeLineItem>(3);
	private int lastCount = 0;
	private int currentFirstVisibleItem = 0;
	private int currentVisibleItemCount = 0;
	private int currentScrollState = 0; 
	ProgressBar dialog;
	private boolean loadingItems = false;
	private DrawerLayout drawer;
	private JHImageLoader imageLoader;
	final String[] data = { "Logout" };

	private class MenuAdapter extends ArrayAdapter<String> {

		public MenuAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) JHTimelineActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.menu_item, null);
			}
			TextView option = (TextView) convertView.findViewById(R.id.option);
			option.setText(data[position]);
			return convertView;

		}

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.timeline);
		MenuAdapter adapter = new MenuAdapter(this, R.layout.menu_item, data);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		imageLoader = new JHImageLoader(this);
		final ListView navList = (ListView) findViewById(R.id.left_drawer);
		View headerView = getLayoutInflater().inflate(R.layout.navlist_header,
				null);
		ImageView profilePicIconView = (ImageView) headerView
				.findViewById(R.id.profilePicView);
		imageLoader.DisplayImage(JHAppStateVariables.getProfilePicURL(),
				profilePicIconView);
		TextView userNameTV = (TextView) headerView
				.findViewById(R.id.profileNameTV);
		TextView userEmailTV = (TextView) headerView
				.findViewById(R.id.profileEmailTV);
		userNameTV.setText(JHAppStateVariables.getUsername());
		userEmailTV.setText(JHAppStateVariables.getEmail());
		navList.addHeaderView(headerView);
		navList.setAdapter(adapter);
		drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

		});
		navList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int pos, long id) {
				if (pos == 0)
					return;
				invokeLogoutApi();
				drawer.closeDrawer(navList);
			}
		});

		EncourageApplication.getSharedApplication().setImageLoader(imageLoader);
		initViews();
	}

	protected void onResume() {
		super.onResume();
		if (JHAppStateVariables.getLoginTocken() == null) {
			Intent intent = new Intent(JHTimelineActivity.this,
					JHLoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

		} else {
			invokeTimelineDetailsApi("", JHUtility.getDateTime(),
					JHUtility.getTimeZoneString(), 0);
			new VersionCheckTask().execute();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(10);
	}

	private void invokeLogoutApi() {

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(10);

		LogoutService service = EncourageApplication.getRestAdapter().create(
				LogoutService.class);

		JHUtility.showProgressDialog("Logging out..", this);

		JHGPSTracker gpsTracker = JHGPSTracker
				.getGPSTracker(JHTimelineActivity.this);
		Location location = gpsTracker.getLocation();
		String latitude = "";
		String longitude = "";
		if (location != null) {
			latitude = location.getLatitude() + "";
			longitude = location.getLongitude() + "";
		}

		service.logoutUser("userLogout", JHAppStateVariables.getLoginTocken(),
				JHUtility.getDateTime(), JHUtility.getTimeZoneString(),
				latitude, longitude, new Callback<SpocResponse>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						JHUtility
								.dismissProgressDialog(JHTimelineActivity.this);
						JHUtility.showDialogOk("Error",
								"Logout failed. Please try again!",
								JHTimelineActivity.this);
					}

					@Override
					public void success(SpocResponse spocResponse, Response arg1) {
						// TODO Auto-generated method stub
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
									JHAppStateVariables.setLoginTocken(null);
									JHAppStateVariables
											.clearAppStateVariables();
									if (EncourageApplication
											.getSharedApplication()
											.getImageLoader() != null) {
										EncourageApplication
												.getSharedApplication()
												.getImageLoader().clearCache();
									}
									JHUtility
											.dismissProgressDialog(JHTimelineActivity.this);
									Intent intent = new Intent(
											JHTimelineActivity.this,
											JHLoginActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
											| Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
									finish();
								} else {
									System.out.println("error");
									JHUtility
											.dismissProgressDialog(JHTimelineActivity.this);
									JHUtility.showDialogOk("Error",
											"Logout failed. Please try again!",
											JHTimelineActivity.this);
								}
							}
						}

					}
				});
	}

	
	JHAlertsTeaserDialog adialog;
	
	class AlertClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			//if (JHAppStateVariables
				//	.getUnreadNotificationCount(JHConstants.NOT_TYPE_ALERT) > 0) {
				adialog = new JHAlertsTeaserDialog(
						JHTimelineActivity.this);
				adialog.show();
			//}
		}
	}
	

	public void closeAlertTeaser(View view) {
		if (adialog != null && adialog.isShowing()) {
			adialog.cancel();
		}
	}
	
	JHCareTasksDialog cdialog;
	
	class CTClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (JHAppStateVariables
					.getUnreadNotificationCount(JHConstants.NOT_TYPE_CARE_TASK) > 0) {
				cdialog = new JHCareTasksDialog(
						JHTimelineActivity.this, JHTimelineActivity.this);
				cdialog.show();
			}

		}
	}

	public void closeCareTaskTeaser(View view) {
		if (cdialog != null && cdialog.isShowing()) {
			cdialog.cancel();
		}
	}
	
	private void initViews() {

		timelineView = (ListView) findViewById(R.id.timeLineView);

		dialog = new ProgressBar(this);
		dialog.setProgressDrawable(getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal));
		timelineView.addFooterView(dialog);
		adapter = new JHTimelineAdapter(this, list, false, imageLoader);
		timelineView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		// timelineView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// if(list.get(position).getDatatype().equalsIgnoreCase("Map")||
		// list.get(position).getDatatype().equalsIgnoreCase("Image")){
		// ImageView imageView =
		// (ImageView)view.findViewWithTag(list.get(position).getTimelineid());
		// if(imageView!=null){
		// if(imageView.getBackground().equals(getResources().getDrawable(R.drawable.retry)))
		// imageLoader.DisplayImage(list.get(position).getFilename(),
		// imageView);
		// }
		// }
		//
		// }
		// });

		timelineView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				JHTimelineActivity.this.currentScrollState = scrollState;
				this.isScrollCompleted();

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				JHTimelineActivity.this.currentFirstVisibleItem = firstVisibleItem;
				JHTimelineActivity.this.currentVisibleItemCount = visibleItemCount;
				this.isScrollCompleted();

			}

			private void isScrollCompleted() {
				if (JHTimelineActivity.this.currentFirstVisibleItem
						+ JHTimelineActivity.this.currentVisibleItemCount == (list
						.size() - 1)
						&& JHTimelineActivity.this.currentScrollState == SCROLL_STATE_FLING) {

					if (timelineView.getFooterViewsCount() <= 0) {
						if (!loadingItems) {
							timelineView.addFooterView(dialog);
							invokeTimelineDetailsApi("",
									JHUtility.getDateTime(),
									JHUtility.getTimeZoneString(), list.size());

						}
					}
				}
			}
		});

		RelativeLayout alertsButton = (RelativeLayout) findViewById(R.id.alertButton);
		alertsButton.setOnClickListener(new AlertClicked());

		RelativeLayout ctButton = (RelativeLayout) findViewById(R.id.careTaskButton);
		ctButton.setOnClickListener(new CTClicked());
		ImageButton reportButton = (ImageButton) findViewById(R.id.reportButton);
		reportButton.setOnClickListener(new ReportClicked());

		TextView alertNumberView = (TextView) findViewById(R.id.alertnumberView);
		alertNumberView.setText(JHAppStateVariables
				.getUnreadNotificationCount(JHConstants.NOT_TYPE_ALERT) + "");

		JHAppStateVariables.alertNumberView = alertNumberView;

		TextView careTaskNumberView = (TextView) findViewById(R.id.caretasknumberView);
		careTaskNumberView.setText(JHAppStateVariables
				.getUnreadNotificationCount(JHConstants.NOT_TYPE_CARE_TASK)
				+ "");

		JHAppStateVariables.careTaskNumberView = careTaskNumberView;

		JHAppStateVariables.timeLineActivity = this;

	}

	private void invokeTimelineDetailsApi(String careTargetId, String dateTime,
			String timeZone, int start) {

		loadingItems = true;
		TimeLineService service = EncourageApplication.getRestAdapter().create(
				TimeLineService.class);
		String token = JHAppStateVariables.getLoginTocken();

		JHGPSTracker gpsTracker = JHGPSTracker
				.getGPSTracker(JHTimelineActivity.this);
		Location location = gpsTracker.getLocation();
		String latitude = "";
		String longitude = "";
		if (location != null) {
			latitude = location.getLatitude() + "";
			longitude = location.getLongitude() + "";
		}

		if (start != 0) {
			service.getAdditionalTimeLineDetails("getTimelineDetails", token,
					careTargetId, dateTime, timeZone, start, latitude,
					longitude, new Callback<SpocResponse>() {
						@Override
						public void success(SpocResponse spocResponse,
								Response response) {

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
										loadingItems = false;
										timelineView.removeFooterView(dialog);

									} else {
										loadingItems = false;
										timelineView.removeFooterView(dialog);

										Toast.makeText(
												JHTimelineActivity.this,
												"Failed to fetch latest feed. Please try again.",
												Toast.LENGTH_LONG).show();
									}

								} else if (spocObject.getResultTypeCode()
										.equalsIgnoreCase("READONLY_FORM")) {
									HashMap<String, String> map = spocObject
											.getMap();
									if (map.containsKey("lastcount")) {
										lastCount = Integer.parseInt(map
												.get("lastcount"));
									} else {
										TimeLineItem item = new TimeLineItem();
										//Log.e("crash", "CRASHHHHHHH");
										//Log.e("crash", map.toString());
										item.setType(map.get("datatype"));
										if (map.get("datatype").contains("Map")) {
											if (map.get("eventAddress") == null) {
												continue;
											}

											item.setDatatype("map");
											item.setEventAddress(map
													.get("eventaddress"));

										} else if (map.get("datatype")
												.contains("Image")) {
											item.setDatatype("image");
											Log.i("IMAGE!!!!!",
													"https://tryencourage.com/hwdsi/hwAttachedfile/"
															+ JHAppStateVariables
																	.getLoginTocken()
															+ "/"
															+ map.get("documentactualname"));
											item.setFilename("https://tryencourage.com/hwdsi/hwAttachedfile/"
													+ JHAppStateVariables
															.getLoginTocken()
													+ "/"
													+ map.get("documentactualname"));
										} else {
											item.setDatatype("normal");
										}
										item.setDetails(map.get("details"));
										item.setTimelineid(map
												.get("timelineid"));
										if (list.contains(item))
											continue;
										item.setRecordKey(map.get("recordKey"));
										item.setPerson(map.get("person"));
										item.setTimelineDate(map
												.get("timelinedate")/*
																	 * .split(
																	 * "&nbsp;IST"
																	 * )[0]
																	 */);
										item.setTitle(map.get("title"));
										list.add(item);
									}
								}
							}
							Collections.sort(list);
							adapter.notifyDataSetChanged();
						}

						@Override
						public void failure(RetrofitError retrofitError) {

							System.out.println("Retro error");
							loadingItems = false;
						}
					});
		} else {
			service.getTimeLineDetails("getTimelineDetails", token,
					careTargetId, dateTime, timeZone, latitude, longitude,
					new Callback<SpocResponse>() {
						@Override
						public void success(SpocResponse spocResponse,
								Response response) {

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
										loadingItems = false;
										timelineView.removeFooterView(dialog);

									} else {
										// JHUtility.showDialogOk("Network Error",
										// "Failed to fetch latest feed",
										// JHTimelineActivity.this);
										loadingItems = false;
										timelineView.removeFooterView(dialog);

									}

								} else if (spocObject.getResultTypeCode()
										.equalsIgnoreCase("READONLY_FORM")) {
									HashMap<String, String> map = spocObject
											.getMap();
									if (map.containsKey("lastcount")) {
										lastCount = Integer.parseInt(map
												.get("lastcount"));
									} else {
										TimeLineItem item = new TimeLineItem();
										//Log.e("crash", "CRASHHHHHHH");
										//Log.e("crash", map.toString());
										item.setType(map.get("datatype"));
										if (map.get("datatype").contains("Map")) {
											item.setDatatype("map");
											item.setEventAddress(map
													.get("eventaddress"));

										} else if (map.get("datatype")
												.contains("Image")) {
											item.setDatatype("image");
											Log.i("IMAGE!!!!!",
													"https://tryencourage.com/hwdsi/hwAttachedfile/"
															+ JHAppStateVariables
																	.getLoginTocken()
															+ "/"
															+ map.get("documentactualname"));
											item.setFilename("https://tryencourage.com/hwdsi/hwAttachedfile/"
													+ JHAppStateVariables
															.getLoginTocken()
													+ "/"
													+ map.get("documentactualname"));
										} else {
											item.setDatatype("normal");
										}
										item.setDetails(map.get("details"));
										item.setTimelineid(map
												.get("timelineid"));
										if (list.contains(item))
											continue;
										item.setRecordKey(map.get("recordKey"));
										item.setPerson(map.get("person"));
										item.setTimelineDate(map
												.get("timelinedate")/*
																	 * .split(
																	 * "&nbsp;IST"
																	 * )[0]
																	 */);
										item.setTitle(map.get("title"));
										list.add(item);
									}
								}
							}
							Collections.sort(list);
							adapter.notifyDataSetChanged();
						}

						@Override
						public void failure(RetrofitError retrofitError) {

							System.out.println("Retro error");
							loadingItems = false;
						}
					});
		}

	}

	class ReportClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			try {
				Intent i = new Intent(JHTimelineActivity.this,
						JHReportWizardActivity.class);
				startActivity(i);
			} catch (Exception ex) {
				Log.e("main", ex.toString());
			}
		}
	}

	public void menuButtonClicked(View view) {
		drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
		drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

	}

	@Override
	public void onBackPressed() {
		// Disabled back button action
	}

	class VersionCheckTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {

		}

		@Override
		protected Boolean doInBackground(String... params) {
			invokeVersionCheckApi();
			return true;
		}
	}

	private void invokeVersionCheckApi() {
		RestAdapter restAdapter = EncourageApplication.getRestAdapter();
		VersionCheckService service = restAdapter
				.create(VersionCheckService.class);
		service.checkVersion("android", new Callback<SpocResponse>() {
			@Override
			public void success(SpocResponse spocResponse, Response arg1) {
				ArrayList<SpocObject> responseList = spocResponse
						.getSpocObjects();

				System.out.println("responseList.size() " + responseList.size());

				for (SpocObject spocObject : responseList) {
					if (spocObject.getResultTypeCode().equalsIgnoreCase(
							"STATUS")) {
						HashMap<String, String> map = spocObject.getMap();
						String success = map.get("success");
						if (success.equalsIgnoreCase("true")) {
							spocObject = responseList.get(2);
							map = spocObject.getMap();

							try {
								PackageInfo pInfo = getPackageManager()
										.getPackageInfo(getPackageName(), 0);
								String version = pInfo.versionName;
								System.out.println("VERSION " + version);

								String forcedupdate = map.get("forcedupdate");
								String updateVerion = map.get("appversion");
								final String updateUrl = map.get("updateurl");
								System.out.println("forcedupdate "
										+ forcedupdate);
								System.out.println("updateUrl "+updateUrl);

								float aVersion = 0.0f;
								float uVersion = 0.0f;

								try {
									aVersion = Float.parseFloat(version);
									uVersion = Float.parseFloat(updateVerion);
								} catch (NumberFormatException e) {
									aVersion = 0.0f;
									uVersion = 0.0f;
								}

								if (uVersion > aVersion) {
									// if(true) { for testing
									// forcedupdate = "true";
									if (forcedupdate.equalsIgnoreCase("true")) {

										JHTimelineActivity.this
												.runOnUiThread(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated
														// method stub
														AlertDialog dialog = new AlertDialog.Builder(
																JHTimelineActivity.this)
																.setTitle(
																		"Info")
																.setMessage(
																		"A mandatory update of Enocurage Mobile is required")
																.setPositiveButton(
																		"Ok",
																		new DialogInterface.OnClickListener() {

																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {
																				Uri uri = Uri
																						.parse(updateUrl);
																				Intent intent = new Intent(
																						Intent.ACTION_VIEW,
																						uri);
																				startActivity(intent);
																				dialog.dismiss();
																			}
																		})
																.create();
														dialog.show();
													}
												});

									} else {

										JHTimelineActivity.this
												.runOnUiThread(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated
														// method stub
														AlertDialog dialog = new AlertDialog.Builder(
																JHTimelineActivity.this)
																.setTitle(
																		"Info")
																.setMessage(
																		"An update is available for Enocurage Mobile")
																.setPositiveButton(
																		"Ok",
																		new DialogInterface.OnClickListener() {

																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {
																				// TODO
																				// Auto-generated
																				// method
																				// stub
																				dialog.dismiss();
																			}
																		})
																.create();
														dialog.show();
													}
												});

									}
								}

							} catch (NameNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
