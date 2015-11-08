package ru.vlad805.guap.schedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.vlad805.guap.schedule.MainMenuItem;
import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.utils.Utils;

public class DrawerActivity
		extends BaseActivity
		implements  NavigationView.OnNavigationItemSelectedListener {

	final public static String KEY_GID = "gid";
	final public static String KEY_STORED = "stored";

	private static final String CONTAINER_TAG = "currentFragment";

	@Bind(R.id.drawer_layout)	DrawerLayout mDrawerLayout;
	@Bind(R.id.navigation) 		NavigationView mNavigation;

	private Utils u;

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);
		u = new Utils(this);

		if (!u.hasString(KEY_GID)) {
			startActivity(new Intent(this, WelcomeActivity.class));
			finish();
			return;
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);

		mNavigation.setNavigationItemSelectedListener(this);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		selectMenuItem(MainMenuItem.defaultItem());
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		for (MainMenuItem mainMenuItem : MainMenuItem.values()) {
			if (item.getItemId() == mainMenuItem.item) {
				selectMenuItem(mainMenuItem);
			}
		}
		mDrawerLayout.closeDrawer(GravityCompat.START);
		return false;
	}

	public void selectMenuItem(MainMenuItem item) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.findFragmentByTag(CONTAINER_TAG) == null) {
			fragmentManager.beginTransaction()
					.add(R.id.container, item.fragment, CONTAINER_TAG)
					.commit();
		} else {
			fragmentManager.beginTransaction()
					.replace(R.id.container, item.fragment, CONTAINER_TAG)
					.commit();
		}
		mToolbar.setTitle(item.title);
		mNavigation.setCheckedItem(item.item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				toggleDrawer();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void toggleDrawer() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else {
			mDrawerLayout.openDrawer(GravityCompat.START);
		}
	}
}
