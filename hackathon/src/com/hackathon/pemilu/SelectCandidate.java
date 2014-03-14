package com.hackathon.pemilu;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SelectCandidate extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	private ActionBar mActionBar;
	private TabsPageAdapter mAdapter;
	private ViewPager mViewPager;

	private ArrayList<Area> mAreaList;
	private HackathonApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_party);

		mApplication = (HackathonApplication) getApplicationContext();
		mAreaList = mApplication.getAreaList();

		mActionBar = getSupportActionBar();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new TabsPageAdapter(getSupportFragmentManager());

		mViewPager.setAdapter(mAdapter);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setHomeButtonEnabled(false);

		for (Area area : mAreaList) {
			mActionBar.addTab(mActionBar.newTab().setText(area.getLembaga())
					.setTabListener(this));
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						mActionBar.setSelectedNavigationItem(arg0);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
