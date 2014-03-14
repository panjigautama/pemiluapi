package com.hackathon.pemilu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPageAdapter extends FragmentPagerAdapter {

	public TabsPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new DPDFragment();
		case 1:
			return new DPRDFragment();
		case 2:
			return new DPRFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
