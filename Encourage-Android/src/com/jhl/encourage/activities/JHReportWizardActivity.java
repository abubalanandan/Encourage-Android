package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHReportWizardPageAdapter;
import com.jhl.encourage.apis.ReportWizartAPICaller;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHReportWizardEmotionalFragment;
import com.jhl.encourage.views.JHReportWizardImageFragment;
import com.jhl.encourage.views.JHReportWizardMapFragment;
import com.jhl.encourage.views.JHReportWizardSicknessFragment;
import com.jhl.encourage.views.JHReportWizardVideoFragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

//public class JHReportWizardActivity extends FragmentActivity {

public class JHReportWizardActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, JHReportWizardActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;

	private JHReportWizardSicknessFragment sickFragment;
	private JHReportWizardEmotionalFragment emoFragment;
	private JHReportWizardImageFragment imageFragment;
	private JHReportWizardVideoFragment videoFragment;
	private JHReportWizardMapFragment mapFragment;
	
	List<Fragment> fragments = null;
	private ProgressBar spinner;

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
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
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
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); // set the tab  as per the saved state
		}
		spinner = (ProgressBar)findViewById(R.id.pbContact);
		spinner.setVisibility(View.GONE);
		// Intialise ViewPager
		this.intialiseViewPager();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab selected
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
		videoFragment = new JHReportWizardVideoFragment();
		mapFragment = new JHReportWizardMapFragment();
		
		fragments.add(sickFragment);
		fragments.add(emoFragment);
		fragments.add(imageFragment);
		fragments.add(videoFragment);
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
		JHReportWizardActivity.addTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab4").setIndicator("Video"),
				(tabInfo = new TabInfo("Video",
						JHReportWizardVideoFragment.class, args)));
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

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			JHReportWizardImageFragment iFrag = (JHReportWizardImageFragment) fragments .get(2);
			iFrag.setImage(picturePath);

		}
	}

	public void rwContactButtonPressed(View view) {
		new ContactLoadTask().execute();
	}

	class ContactLoadTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			spinner.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			try {
				Intent i = new Intent(JHReportWizardActivity.this, JHContactPickerActivity.class);
				startActivity(i);
			} catch (Exception ex) {
				Log.e("main", ex.toString());
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Contact contact;
			List<Contact> contacts = new ArrayList<Contact>();
			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
					null, null, null);
			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					String id = cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts._ID));
					Cursor cur1 = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (cur1.moveToNext()) {
						// to get the contact names
						String name = cur1
								.getString(cur1
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						// Log.e("Name :", name);
						String email = cur1
								.getString(cur1
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						// Log.e("Email", email);
						if (email != null) {
							contact = new Contact(name, email, false);
							contacts.add(contact);
						}
					}
					cur1.close();
				}
			}
			JHAppStateVariables.setContacts(contacts);
			Log.d(JHConstants.LOG_TAG, "doInBackground done");

			return true;
		}
	}
	
	public void imageButtonClicked(View view){
		Intent i = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		Log.d(JHConstants.LOG_TAG, "my request code "+JHConstants.REQUEST_CODE_IMAGE_LIB);
		startActivityForResult(i, JHConstants.REQUEST_CODE_IMAGE_LIB);
	}
	
	
	public void sendReportButtonPressed(View view) {
		int page = JHAppStateVariables.currentRwPage;
		
		Log.d(JHConstants.LOG_TAG, "page "+page);
		
		ReportWizartAPICaller apiCaller = new ReportWizartAPICaller(this);
		switch (page) {
		case 0:
			apiCaller.invokeSickenssEmotionalApi(JHUtility.getDate(), JHAppStateVariables.getSickEmoReport(), "", false);
		case 1:
			apiCaller.invokeSickenssEmotionalApi(JHUtility.getDate(), JHAppStateVariables.getSickEmoReport(), "", false);
			break;
		case 2:	
			break;
		case 3:
			break;
		case 4:	
			apiCaller.invokeMapApi(mapFragment.getDate(), mapFragment.getName(),mapFragment.getAddress(), mapFragment.getDescription(), false);
			break;
		default:
			break;
		}
	}

}
