package com.jhl.encourage.adapters;

import java.util.List;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class JHReportWizardPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	private String[] titles = new String[]{"Sick", "Emotional", "Image", "Video", "Map"};
	
	public JHReportWizardPageAdapter(FragmentManager manager, List<Fragment> fragments){
		super(manager);
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		return titles[position];
	}
}
