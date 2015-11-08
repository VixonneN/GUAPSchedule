package ru.vlad805.guap.schedule.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mjson.Json;
import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.utils.API;
import ru.vlad805.guap.schedule.utils.APICallback;
import ru.vlad805.guap.schedule.utils.APIError;
import ru.vlad805.guap.schedule.utils.Utils;

public class SelectGroupActivity extends AppCompatActivity implements View.OnClickListener {

	private Utils u;
	private ProgressDialog progress;
	private ArrayList<String> items = new ArrayList<>();
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_group);

		u = new Utils(this);

		progress = u.showProgress(getString(R.string.alert_loading_groups));

		API.invoke(this, "guap.getGroups", new HashMap<String, String>(), new APICallback() {
			@Override
			public void onResult(Json result) {
				progress.cancel();
				showItems(result.at("items").asJsonList());
			}

			@Override
			public void onError(APIError e) {
				progress.cancel();
				u.toast(getString(R.string.alert_nointernet_nogroups));
			}
		});

		findViewById(R.id.select_submit).setOnClickListener(this);
	}

	public void showItems (List<Json> data) {
		for (Json item : data) {
			items.add(item.at("groupId").asString());
		}

		spinner = (Spinner) findViewById(R.id.select_spinner_group);
		ArrayAdapter<?> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);;
		spinner.setAdapter(adapter);
	}

	public void onClick (View v) {
		switch (v.getId()) {
			case R.id.select_submit:
				if (spinner == null) {
					u.toast(getString(R.string.alert_incorrect_group));
					return;
				}
				String group = spinner.getSelectedItem().toString();
				u.setString(DrawerActivity.KEY_GID, group);
				startActivity(new Intent(this, DrawerActivity.class));
				finish();
		}
	}

}
