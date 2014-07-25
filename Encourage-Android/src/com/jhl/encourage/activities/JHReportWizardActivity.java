package com.jhl.encourage.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;
import android.widget.TextView;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHReportWizardPageAdapter;
import com.jhl.encourage.apis.ReportWizartAPICaller;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.model.JHUploadResponse;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHGPSTracker;
import com.jhl.encourage.utilities.JHHTTPClient;
import com.jhl.encourage.utilities.JHUploadResponseParser;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHContactDialog;
import com.jhl.encourage.views.JHReportFragment;
import com.jhl.encourage.views.JHReportWizardEmotionalFragment;
import com.jhl.encourage.views.JHReportWizardImageFragment;
import com.jhl.encourage.views.JHReportWizardMapFragment;
import com.jhl.encourage.views.JHReportWizardSicknessFragment;

public class JHReportWizardActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, JHReportWizardActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;
	private CheckBox cboxCareCircle;
	private JHReportWizardSicknessFragment sickFragment;
	private JHReportWizardEmotionalFragment emoFragment;
	private JHReportWizardImageFragment imageFragment;
	// private JHReportWizardVideoFragment videoFragment;
	private JHReportWizardMapFragment mapFragment;

	List<Fragment> fragments = null;

	private Calendar cal;
	private int day;
	private int month;
	private int year;

	/**
	 * Maintains extrinsic info of a tab's construct
	 */
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}

	}

	/**
	 * A simple factory that returns dummy views to the Tabhost
	 */
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);
//			v.setMinimumWidth(0);
//			v.setMinimumHeight(0);
			v.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return v;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the layout
		setContentView(R.layout.reportwizard);
		// Initialise the TabHost
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); // set
																				// the
																				// tab
																				// as
																				// per
																				// the
																				// saved
																				// state
		}

		cboxCareCircle = (CheckBox) findViewById(R.id.cboxCareCircle);
		cboxCareCircle.setChecked(true);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		
		// Intialise ViewPager
		this.intialiseViewPager();
		
		TabWidget tw = (TabWidget)mTabHost.findViewById(android.R.id.tabs);
		
		View tabView = tw.getChildTabViewAt(0);
		TextView tv = (TextView)tabView.findViewById(android.R.id.title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimension(
						R.dimen.tab_title_size));
		
		tabView = tw.getChildTabViewAt(1);
		tv = (TextView)tabView.findViewById(android.R.id.title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimension(
						R.dimen.tab_title_size));
		
		tabView = tw.getChildTabViewAt(2);
		tv = (TextView)tabView.findViewById(android.R.id.title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimension(
						R.dimen.tab_title_size));
		
		tabView = tw.getChildTabViewAt(3);
		tv = (TextView)tabView.findViewById(android.R.id.title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimension(
						R.dimen.tab_title_size));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
																// selected
		super.onSaveInstanceState(outState);
	}

	/**
	 * Initialise ViewPager
	 */
	private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		sickFragment = new JHReportWizardSicknessFragment();
		emoFragment = new JHReportWizardEmotionalFragment();
		imageFragment = new JHReportWizardImageFragment();
		// videoFragment = new JHReportWizardVideoFragment();
		mapFragment = new JHReportWizardMapFragment();

		fragments.add(sickFragment);
		fragments.add(emoFragment);
		fragments.add(imageFragment);
		// fragments.add(videoFragment);
		fragments.add(mapFragment);

		this.mPagerAdapter = new JHReportWizardPageAdapter(
				super.getSupportFragmentManager(), fragments);
		//
		this.mViewPager = (ViewPager) super.findViewById(R.id.reportpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);

		this.fragments = fragments;
	}

	/**
	 * Initialise the Tab Host
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		TabInfo tabInfo = null;
		JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab1").setIndicator("Sick"),
				(tabInfo = new TabInfo("Sick",
						JHReportWizardSicknessFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab2").setIndicator("Emotional"),
				(tabInfo = new TabInfo("Emotional",
						JHReportWizardEmotionalFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab3").setIndicator("Image"),
				(tabInfo = new TabInfo("Image",
						JHReportWizardImageFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		// JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
		// .newTabSpec("Tab4").setIndicator("Video"),
		// (tabInfo = new TabInfo("Video",
		// JHReportWizardVideoFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab5").setIndicator("Map"),
				(tabInfo = new TabInfo("Map", JHReportWizardMapFragment.class,
						args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		// Default to first tab
		// this.onTabChanged("Tab1");
		//
		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * Add Tab content to the Tabhost
	 * 
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	private static void addTab(JHReportWizardActivity activity,
			TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
		JHAppStateVariables.currentRwPage = pos;
		
		System.out.println("pos "+pos);
		
		if(! ( pos ==0 || pos == 1) ) {
			sickFragment.clearButtonSelections();
			emoFragment.clearButtonSelections();
		}else if (pos != 2){
			imageFragment.clearImagePath();
		}

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		JHAppStateVariables.currentRwPage = arg0;
		this.mTabHost.setCurrentTab(arg0);
		
		System.out.println("arg0 "+arg0);
		
		if(! ( arg0 ==0 || arg0 == 1) ) {
			sickFragment.clearButtonSelections();
			emoFragment.clearButtonSelections();
		}else if (arg0 != 2){
			imageFragment.clearImagePath();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(JHConstants.LOG_TAG, " requestCode " + requestCode);

		Log.d(JHConstants.LOG_TAG, " resultCode " + resultCode);

		if (requestCode == JHConstants.REQUEST_CODE_IMAGE_LIB
				&& resultCode == RESULT_OK && null != data) {
			Log.d(JHConstants.LOG_TAG, " data " + data.toString());
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			JHReportWizardImageFragment iFrag = (JHReportWizardImageFragment) fragments
					.get(2);
			iFrag.setImage(selectedImage, picturePath);

		}
	}

	private JHReportFragment getCurrentFragment(int page) {
		JHReportFragment fragment = null;
		switch (page) {
		case 0:
			fragment = sickFragment;
			break;
		case 1:
			fragment = emoFragment;
			break;
		case 2:
			fragment = imageFragment;
			break;
		case 3:
			fragment = mapFragment;
			break;

		}
		return fragment;

	}

	public void rwContactButtonPressed(View view) {

		int page = JHAppStateVariables.currentRwPage;
		// JHReportFragment fragment = getCurrentFragment(page);
		// JHContactDialog dialog = new JHContactDialog(this, fragment);
		// dialog.show();
		getAllContacts(getContentResolver());
	}

	public void getAllContacts(ContentResolver cr) {
		Contact contact;
		List<Contact> contacts = new ArrayList<Contact>();
		Log.i("Start ", new Date().toString());
		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String email = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
			if (name != null && email != null) {
				name = name.trim();
				email = email.trim();
				if (!(name.equals("") || email.equals(""))) {
					contact = new Contact(name, email, false, false);
					if (!contacts.contains(contact))
						contacts.add(contact);
				}
			}
		}
		JHAppStateVariables.setContacts(contacts);
		phones.close();

		try {
			Intent i = new Intent(JHReportWizardActivity.this,
					JHContactPickerActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_up, 0);
		} catch (Exception ex) {
			Log.e("main", ex.toString());
		}

		Log.i("End", new Date().toString());
	}

	// class ContactLoadTask extends AsyncTask<String, Integer, Boolean> {
	// @Override
	// protected void onPreExecute() {
	// spinner.setVisibility(View.VISIBLE);
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected void onPostExecute(Boolean result) {
	// try {
	// Intent i = new Intent(JHReportWizardActivity.this,
	// JHContactPickerActivity.class);
	// startActivity(i);
	// } catch (Exception ex) {
	// Log.e("main", ex.toString());
	// }
	// }
	//
	// @Override
	// protected Boolean doInBackground(String... params) {
	// Contact contact;
	// List<Contact> contacts = new ArrayList<Contact>();
	// ContentResolver cr = getContentResolver();
	// Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
	// null, null, null);
	// if (cur.getCount() > 0) {
	// while (cur.moveToNext()) {
	// String id = cur.getString(cur
	// .getColumnIndex(ContactsContract.Contacts._ID));
	// Cursor cur1 = cr.query(
	// ContactsContract.CommonDataKinds.Email.CONTENT_URI,
	// null,
	// ContactsContract.CommonDataKinds.Email.CONTACT_ID
	// + " = ?", new String[] { id }, null);
	// while (cur1.moveToNext()) {
	// // to get the contact names
	// String name = cur1
	// .getString(cur1
	// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	// // Log.e("Name :", name);
	// String email = cur1
	// .getString(cur1
	// .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	// // Log.e("Email", email);
	// if (email != null) {
	// contact = new Contact(name, email, false);
	// contacts.add(contact);
	// }
	// }
	// cur1.close();
	// }
	// }
	// JHAppStateVariables.setContacts(contacts);
	// Log.d(JHConstants.LOG_TAG, "doInBackground done");
	//
	// return true;
	// }
	// }

	public void imageButtonClicked(View view) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		Log.d(JHConstants.LOG_TAG, "my request code "
				+ JHConstants.REQUEST_CODE_IMAGE_LIB);
		startActivityForResult(i, JHConstants.REQUEST_CODE_IMAGE_LIB);
	}

	
	
	public void sendReportButtonPressed(View view) {
		int page = JHAppStateVariables.currentRwPage;
		Log.d(JHConstants.LOG_TAG, "page " + page);
		JHReportFragment fragment = getCurrentFragment(page);

		ReportWizartAPICaller apiCaller = new ReportWizartAPICaller(this);

		JHGPSTracker gpsTracker = JHGPSTracker
				.getGPSTracker(JHReportWizardActivity.this);
		Location location = gpsTracker.getLocation();
		String latitude = "";
		String longitude = "";
		if (location != null) {
			latitude = location.getLatitude() + "";
			longitude = location.getLongitude() + "";
		}

		switch (page) {
		case 0:
			String date = sickFragment.getSickDate();
			if (date == null || date.trim().equals("")) {
				date = JHUtility.getDate();
			}
			if (JHAppStateVariables.isReadyForSubmission()) {
				JHUtility.showProgressDialog("Reporting...", this);
				apiCaller.invokeSickenssEmotionalApi(date,
						JHAppStateVariables.getSickEmoReport(),
						sickFragment.getSickDesc(), cboxCareCircle.isChecked(),
						latitude, longitude);
			} else {
				JHUtility.showDialogOk("Error",
						"Please choose a sickness/emotion",
						JHReportWizardActivity.this);
			}
			break;
		case 1:
			date = emoFragment.getEmoDate();
			if (date == null || date.trim().equals("")) {
				date = JHUtility.getDate();
			}
			if (JHAppStateVariables.isReadyForSubmission()) {

				JHUtility.showProgressDialog("Reporting...", this);
				apiCaller.invokeSickenssEmotionalApi(date,
						JHAppStateVariables.getSickEmoReport(),
						emoFragment.getEmoDesc(), cboxCareCircle.isChecked(),
						latitude, longitude);
			} else {
				JHUtility.showDialogOk("Error",
						"Please choose a sickness/emotion",
						JHReportWizardActivity.this);
			}
			break;
		case 2:
			JHUtility.showProgressDialog("Uploading...", this);
			date = imageFragment.getDate();
			String name = imageFragment.getName();
			imageFragment.showProgrees();
			imageUpload(apiCaller, date, name);
			break;
		case 3:
			if (mapFragment.getAddress() != null
					&& mapFragment.getAddress().length() != 0) {
				JHUtility.showProgressDialog("Reporting...", this);
				apiCaller.invokeMapApi(mapFragment.getDate(),
						mapFragment.getName(), mapFragment.getAddress(),
						mapFragment.getDescription(),
						cboxCareCircle.isChecked(), latitude, longitude);
			} else {
				JHUtility.showDialogOk(null,
						"Please Enter the address of the event", this);
			}
			break;
		default:
			break;
		}
	}

	private void imageUpload(ReportWizartAPICaller apiCaller, String eventDate,
			String eventName) {
		// String url =
		// "https://tryencourage.com/hwdsi/hwservice/fileUpload.php";
		// String url = "http://192.168.1.20:80/encourage_gcm/upload.php";
		// String url = "https://tryencourage.com/test.php";

		String url = "https://tryencourage.com/ajaxRequest.php";

		String token = JHAppStateVariables.getLoginTocken();
		Log.d(JHConstants.LOG_TAG, "token " + token);

		

		String imagePath = imageFragment.getImagePath();
		if(imagePath==null){
			JHUtility.dismissProgressDialog(this);
			JHUtility.showDialogOk("Error", "Please choose an image to be uploaded", this);
			return;
		}
		File file = new File(imagePath);
		if (file.length() > 4194304) {
			JHUtility.dismissProgressDialog(this);
			JHUtility.showDialogOk("Size Limit",
					"Please choose a file of size less than 4MB", this);
			return;
		}
		String extenstion = imagePath.substring(imagePath.lastIndexOf("."),
				imagePath.length());
		String actualfileName = imagePath.substring(
				imagePath.lastIndexOf("/") + 1, imagePath.length());
		Map<String, String> paramters = new HashMap<String, String>();
		paramters.put("operationName", "fileUpload");
		paramters.put("token", token);
		// paramters.put("name", "images[]");

		Log.d(JHConstants.LOG_TAG, "file.getName() " + file.getName());

		paramters.put("filename", file.getName());
		paramters.put("doc_action", "fileUpload_mobile");
		paramters
				.put("imagetype", extenstion.substring(1, extenstion.length()));
		Log.d(JHConstants.LOG_TAG, extenstion);
		Log.d(JHConstants.LOG_TAG, imagePath);
		Log.d(JHConstants.LOG_TAG, "url " + url);

		new FileUploadTask(file, url, paramters, actualfileName, apiCaller,
				eventName, eventDate, false).execute();

	}

	private class FileUploadTask extends AsyncTask<String, Void, String> {

		String uploadUrl;
		Map<String, String> parameters;

		ReportWizartAPICaller apiCaller;
		String eventDate;
		String eventName;
		boolean ics;
		File file;
		String actualFileName;

		public FileUploadTask(File file, String uploadUrl,
				Map<String, String> parameters, String actualfileName,
				ReportWizartAPICaller apiCaller, String eventName,
				String eventDate, boolean ics) {

			this.uploadUrl = uploadUrl;
			this.parameters = parameters;
			this.file = file;
			this.apiCaller = apiCaller;
			this.eventDate = eventDate;
			this.eventName = eventName;
			this.ics = ics;
			this.actualFileName = actualfileName;
		}

		@Override
		protected String doInBackground(String... params) {

			JHHTTPClient client = new JHHTTPClient();
			String responseString = client.uploadFile(uploadUrl, file,
					parameters);
			JHUploadResponse response = JHUploadResponseParser
					.parse(responseString);

			// JHUploadResponseParser parser = new JHUploadResponseParser();
			//
			// Map<String, String> responseMap = parser.parse(response);
			//

			String status = response.getStatus().trim();
			status = status.replaceAll("\"", "");
			String uploadefileName = response.getFileName();

			uploadefileName = uploadefileName.replaceAll("\"", "");

			Log.d(JHConstants.LOG_TAG, "status " + status);

			Log.d(JHConstants.LOG_TAG, "uploadefileName " + uploadefileName);

			if (status.equals("true")) {

				JHGPSTracker gpsTracker = JHGPSTracker
						.getGPSTracker(JHReportWizardActivity.this);
				Location location = gpsTracker.getLocation();
				String latitude = "";
				String longitude = "";
				if (location != null) {
					latitude = location.getLatitude() + "";
					longitude = location.getLongitude() + "";
				}

				System.out.println("eventDate "+eventDate);
				
				apiCaller.invokeImageApi(eventDate, eventName, ics,
						uploadefileName, actualFileName, latitude, longitude);
			} else {
				JHReportWizardActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						JHUtility.dismissProgressDialog(JHReportWizardActivity.this);
						JHUtility.showDialogOk("Reporting error",
								"Report posting failed", JHReportWizardActivity.this);

					}
				});
							}

			return null;
		}

		@Override
		protected void onPostExecute(String data) {
			imageFragment.endProgress();

		}

	}

	private static int SICK_DATE_DIALOGUE = 1;
	private static int EMO_DATE_DIALOGUE = 2;
	private static int IMAGE_DATE_DIALOGUE = 3;
	private static int MAP_DATE_DIALOGUE = 4;

	public void sickDatePressed(View view) {
		showDialog(SICK_DATE_DIALOGUE);
	}

	public void emoDatePressed(View view) {
		showDialog(EMO_DATE_DIALOGUE);
	}

	public void imageDatePressed(View view) {
		showDialog(IMAGE_DATE_DIALOGUE);
	}

	public void mapDatePressed(View view) {
		showDialog(MAP_DATE_DIALOGUE);
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			return new DatePickerDialog(this, sickDatePickerListener, year,
					month, day);
		case 2:
			return new DatePickerDialog(this, emoDatePickerListener, year,
					month, day);
		case 3:
			return new DatePickerDialog(this, imageDatePickerListener, year,
					month, day);
		case 4:
			return new DatePickerDialog(this, mapDatePickerListener, year,
					month, day);
		default:
			break;
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener sickDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			String date = (selectedMonth + 1) + "/" + selectedDay + "/"
					+ selectedYear;
			date = JHUtility.getDatePickerDate(date);
			sickFragment.setDate(date);
		}
	};

	private DatePickerDialog.OnDateSetListener emoDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			String date = (selectedMonth + 1) + "/" + selectedDay + "/"
					+ selectedYear;
			date = JHUtility.getDatePickerDate(date);
			emoFragment.setDate(date);
		}
	};

	private DatePickerDialog.OnDateSetListener imageDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			String date = (selectedMonth + 1) + "/" + selectedDay + "/"
					+ selectedYear;
			date = JHUtility.getDatePickerDate(date);
			imageFragment.setDate(date);
		}
	};

	private DatePickerDialog.OnDateSetListener mapDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			String date = (selectedMonth + 1) + "/" + selectedDay + "/"
					+ selectedYear;
			date = JHUtility.getDatePickerDate(date);
			mapFragment.setDate(date);
		}
	};

	public void rwCloseButtonPressed(View view) {
		finish();
	}

}
