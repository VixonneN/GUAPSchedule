package ru.vlad805.guap.schedule.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.fragments.SelectGroupFragment;

public class SelectGroupActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new SelectGroupFragment())
					.commit();
		}
	}

}
