package ru.vlad805.guap.schedule.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.activities.DrawerActivity;
import ru.vlad805.guap.schedule.adapters.DayAdapter;
import ru.vlad805.guap.schedule.api.RestApiImpl;
import ru.vlad805.guap.schedule.api.Schedule;
import ru.vlad805.guap.schedule.utils.Utils;

public class ScheduleListFragment extends Fragment {

	@Bind(R.id.content_updated) TextView mContentUpdated;
	@Bind(R.id.content_settings) LinearLayout mContentSettings;
	@Bind(R.id.switcher_parity) SwitchCompat mSwitch;
	@Bind(R.id.recycle) RecyclerView mRecyclerView;

	private Utils u;
	private int isParityNow;
	private ProgressDialog progress;
	private DayAdapter mDayAdapter;

	public ScheduleListFragment() {
		u = new Utils(getContext());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		u = new Utils(getContext());
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_content, container, false);
		ButterKnife.bind(this, root);
		return root;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		String cache = u.getString(DrawerActivity.KEY_STORED);
		if (cache != null && !cache.isEmpty() && isAdded()) {
			Schedule data = new Gson().fromJson(cache, Schedule.class);
			init(data);
			if (new Date().getTime() - u.getSettings().getLong(DrawerActivity.KEY_STORED_TIME, 0L) > 60 * 60 * 1000) {
				loadAll();
			}
		} else {
			loadAll();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.refresh_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_refresh) {
			loadAll();
		}
		return true;
	}

	public void loadAll () {
		String groupId = u.getString(DrawerActivity.KEY_GID);
		progress = u.showProgress(getString(R.string.alert_updating));

		AsyncTask<Void, Void, Schedule> asyncTask = new AsyncTask<Void, Void, Schedule>() {
			@Override
			protected Schedule doInBackground(Void... params) {
				try {
					return RestApiImpl.INSTANCE.getApi().parseSchedule(groupId).execute().body();
				} catch (Exception e) {
					return null;
				}
			}
			@Override
			protected void onPostExecute(Schedule schedule) {
				if (schedule != null) {
					u.setString(DrawerActivity.KEY_STORED, new Gson().toJson(schedule));
					u.getSettings().edit().putLong(DrawerActivity.KEY_STORED_TIME, new Date().getTime()).apply();
					if (isAdded()) {
						init(schedule);
					}
				} else {
					if (u.hasString(DrawerActivity.KEY_STORED)) {
						u.toast(getString(R.string.alert_nointernet));
					} else {
						u.toast(getString(R.string.alert_nointernet_nothing2show));
					}
				}
				progress.cancel();
			}
		}.execute();
	}

	public void init (final Schedule data) {
		mContentUpdated.setText(String.format(getString(R.string.schedule_from), data.response.parseDate));
		mContentSettings.setVisibility(View.VISIBLE);

		isParityNow = (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2) + 1;

		mSwitch.setChecked(isParityNow == 2);

		mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
			isParityNow = !isChecked ? 1 : 2;
			mDayAdapter.setData(data.response, isParityNow);
		});
		mDayAdapter = new DayAdapter(getContext());

		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(mDayAdapter);
		mRecyclerView.setHasFixedSize(true);
		mDayAdapter.setData(data.response, isParityNow);
	}

}
