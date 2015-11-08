package ru.vlad805.guap.schedule.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.fragments.AboutFragment;

public class AboutActivity extends BaseActivity {

	@Override
	protected int getContentView() {
		return R.layout.container;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar ab = getSupportActionBar();

		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setDisplayShowHomeEnabled(true);
			ab.setHomeButtonEnabled(true);
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new AboutFragment())
					.commit();
		}
	}

	public boolean onOptionsItemSelected (MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
