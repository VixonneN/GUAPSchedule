package ru.vlad805.guap.schedule;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ActionBar ab = getSupportActionBar();

		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setDisplayShowHomeEnabled(true);
			ab.setHomeButtonEnabled(true);
		}

		String version = "1.0";

		try {
			PackageInfo data = getPackageManager().getPackageInfo(getApplicationInfo().packageName, PackageManager.GET_CONFIGURATIONS);
			version = data.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		((TextView) findViewById(R.id.about_version)).setText("v" + version);
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
