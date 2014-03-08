package com.hackathon.pemilu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SelectCandidateActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	ViewPager viewPager;

	TabsPageAdapter adapter;
	ActionBar actionBar;
	HackathonApplication application;
	SessionManager session;

	String[] tabs = { "DPR", "DPD" };
	int party_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_candidate);
		application = (HackathonApplication) getApplicationContext();
		session = application.getSession();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			party_id = bundle.getInt("party_id");
			session.setPartyId(party_id);
		}

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		adapter = new TabsPageAdapter(getSupportFragmentManager());

		viewPager.setAdapter(adapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab)
					.setTabListener(this));
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
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
		viewPager.setCurrentItem(tab.getPosition());
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
