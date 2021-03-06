package ru.vlad805.guap.schedule.activities;

import android.content.Intent;
import android.os.Bundle;

import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.fragments.SelectGroupFragment;

public class WelcomeActivity extends BaseActivity implements SelectGroupFragment.GroupSelectedListener{

	@Override
	protected int getContentView() {
		return R.layout.container;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new SelectGroupFragment())
					.commit();
		}
	}

	@Override
	public void onGroupSelected() {
		startActivity(new Intent(this, DrawerActivity.class));
		finish();
	}
}
