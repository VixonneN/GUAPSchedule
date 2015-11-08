package ru.vlad805.guap.schedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.fragments.ScheduleListFragment;
import ru.vlad805.guap.schedule.utils.Utils;

public class DrawerActivity
		extends BaseActivity
		implements  NavigationView.OnNavigationItemSelectedListener {

	final public static String KEY_GID = "gid";
	final public static String KEY_STORED = "stored";

	NavigationView mNavigation;

	private Utils u;

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		u = new Utils(this);

		if (!u.hasString(KEY_GID)) {
			startActivity(new Intent(this, SelectGroupActivity.class));
			finish();
			return;
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);

		mNavigation = (NavigationView)findViewById(R.id.navigation);
		mNavigation.setCheckedItem(0);
		mNavigation.setNavigationItemSelectedListener(this);

		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.findFragmentById(R.id.container) == null) {
			fragmentManager.beginTransaction()
					.add(R.id.container, ScheduleListFragment.newInstance())
					.commit();
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_select_group:
				u.removeString(KEY_GID);
				u.removeString(KEY_STORED);
				startActivity(new Intent(this, SelectGroupActivity.class));
				finish();
				break;
			case R.id.item_about:
				startActivity(new Intent(this, AboutActivity.class));
				break;
		}

		return true;
	}
}
