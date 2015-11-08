package ru.vlad805.guap.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity
		extends AppCompatActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	final public static String KEY_GID = "gid";
	final public static String KEY_STORED = "stored";

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private Utils u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);


		u = new Utils(this);

		if (!u.hasString(KEY_GID)) {
			startActivity(new Intent(this, SelectGroupActivity.class));
			finish();
			return;
		}

		setContentView(R.layout.activity_main);

	    mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
	    mTitle = getTitle();

	    mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


	}

	@Override
	public void onNavigationDrawerItemSelected (int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

	    switch (position) {
		    case 0:
			    transaction.replace(R.id.container, ScheduleListFragment.newInstance(false));
			    break;

		    case 1:
			    u.removeString(KEY_GID);
			    u.removeString(KEY_STORED);
			    startActivity(new Intent(this, SelectGroupActivity.class));
			    finish();
			    break;

		    case 2:
				startActivity(new Intent(this, AboutActivity.class));
			    return;
	    }

		transaction.commit();
	}

	public void restoreActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    if (actionBar == null)
	        return;
	    actionBar.setDisplayShowTitleEnabled(true);
	    actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed(){
		if(mNavigationDrawerFragment.isDrawerOpen()){
		} else {
			super.onBackPressed();
		}
	}
}
