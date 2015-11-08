package ru.vlad805.guap.schedule.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

import mjson.Json;
import ru.vlad805.guap.schedule.Day;
import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.activities.DrawerActivity;
import ru.vlad805.guap.schedule.adapters.ScheduleAdapter;
import ru.vlad805.guap.schedule.utils.API;
import ru.vlad805.guap.schedule.utils.APICallback;
import ru.vlad805.guap.schedule.utils.APIError;
import ru.vlad805.guap.schedule.utils.Utils;
import ru.vlad805.guap.schedule.views.DayView;

public class ScheduleListFragment extends Fragment {

	private Utils u;
	private int isParityNow;
	private ProgressDialog progress;
	private boolean loaded = false;

	public static ScheduleListFragment newInstance (boolean onlyTomorrow) {
		ScheduleListFragment fragment = new ScheduleListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public ScheduleListFragment() {
		u = new Utils(getContext());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		u = new Utils(getContext());
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_content, container, false);
	}

	@Override
	public void onStart () {
		super.onStart();
		if (!loaded) {
			String cache = u.getString(DrawerActivity.KEY_STORED);
			if (cache != null && !cache.isEmpty()) {
				ScheduleAdapter data = new ScheduleAdapter(Json.read(cache));
				init(data);
				show(data);
			}
			String groupId = u.getString(DrawerActivity.KEY_GID);
			loadAll(groupId);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}



	public void loadAll (String groupId) {

		HashMap<String, String> params = new HashMap<>();
		params.put("groupId", groupId);

		progress = u.showProgress(getString(R.string.alert_updating));
		loaded = true;
		API.invoke(getContext(), "guap.parseSchedule", params, new APICallback() {
			@Override
			public void onResult(Json result) {
				progress.cancel();
				ScheduleAdapter data = new ScheduleAdapter(result);
				u.setString(DrawerActivity.KEY_STORED, result.toString());
				init(data);
				show(data);
			}

			@Override
			public void onError(APIError e) {
				progress.cancel();
				if (u.hasString(DrawerActivity.KEY_STORED)) {
					u.toast(getString(R.string.alert_nointernet));
				} else {
					u.toast(getString(R.string.alert_nointernet_nothing2show));
				}

			}
		});
	}

	public void init (ScheduleAdapter data) {
		((TextView) getActivity().findViewById(R.id.content_updated)).setText(String.format(getString(R.string.schedule_from), data.updated));

		CardView s = ((CardView) getActivity().findViewById(R.id.content_settings));

		s.setContentPadding(DayView.PADDING_LR, DayView.PADDING_TB, DayView.PADDING_LR, DayView.PADDING_TB);

		LinearLayout.LayoutParams lp = DayView.getDefaultLayoutParams(DayView.MATCH_PARENT, DayView.WRAP_CONTENT);
		lp.setMargins(DayView.MARGIN_LR, DayView.MARGIN_TB, DayView.MARGIN_LR, DayView.MARGIN_TB);
		s.setLayoutParams(lp);
		s.setVisibility(View.VISIBLE);

		Calendar c = Calendar.getInstance();
		int w = c.get(Calendar.WEEK_OF_YEAR);
		isParityNow = (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2) + 1;

		Switch sw = (Switch) getActivity().findViewById(R.id.switcher_parity);

		sw.setChecked(isParityNow == 2);

		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isParityNow = !isChecked ? 1 : 2;
				show(globalData);
			}
		});
	}

	private ScheduleAdapter globalData;

	public void show (ScheduleAdapter data) {

		globalData = data;

		Activity act = getActivity();
		LinearLayout list = new LinearLayout(act);
		list.setOrientation(LinearLayout.VERTICAL);
		DayView itemLayout;

		byte j = 0;
		for (Day i : data.schedule) {
			itemLayout = new DayView(act);
			itemLayout.setDay(data, j++, isParityNow);

			list.addView(itemLayout);
		}

		LinearLayout parent = ((LinearLayout) act.findViewById(R.id.content_wrap));

		if (parent.getChildCount() > 0) {
			parent.removeAllViews();
		}
		parent.addView(list);
	}

}
