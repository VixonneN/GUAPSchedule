package ru.vlad805.guap.schedule.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.api.Schedule;
import ru.vlad805.guap.schedule.utils.Utils;

public class DayView extends android.support.v7.widget.CardView {

	private Schedule.Response.Day day;
	private List<Schedule.Response.CoupleTime> coupleTimes;


	final public static int MARGIN_TB = 10;
	final public static int MARGIN_LR = 20;
	final public static int PADDING_TB = 14;
	final public static int PADDING_LR = 20;

	public DayView(Context context) {
		super(context);
	}

	public DayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void populate(Schedule.Response.Day day, List<Schedule.Response.CoupleTime> coupleTimes, int parity) {
		this.day = day;
		this.coupleTimes = coupleTimes;

		initChildren(parity);

		LinearLayout.LayoutParams lp = getDefaultLayoutParams(MATCH_PARENT, WRAP_CONTENT);
		lp.setMargins(MARGIN_LR, MARGIN_TB, MARGIN_LR, MARGIN_TB);
		setLayoutParams(lp);
		setContentPadding(PADDING_LR, PADDING_TB, PADDING_LR, PADDING_TB);
	}

	private void initChildren (int parity) {
		removeAllViews();
		LinearLayout ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_day, null, true);
		((TextView) ll.findViewById(R.id.day_title)).setText(day.title);

		LinearLayout ls;
		Schedule.Response.CoupleTime t;
		int count = 0;

		for (Schedule.Response.Day.Couple c : day.couples) {

			if (c.parity != 0 && c.parity != parity)
				continue;

			ls = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_couple, null, true);
			t = coupleTimes.get(c.coupleId - 1);

			((TextView) ls.findViewById(R.id.couple_number)).setText(String.format(getContext().getString(R.string.couple_number), c.coupleId));
			((TextView) ls.findViewById(R.id.couple_subject)).setText(String.format(getContext().getString(R.string.couple_subject), c.type, c.subject));
			((TextView) ls.findViewById(R.id.couple_time)).setText(t.start + "-" + t.end);
			((TextView) ls.findViewById(R.id.couple_audience)).setText(c.build + "; ауд. " + Utils.getStringFromArray(c.audiences) + (c.teacher.isEmpty() ? "" : "; " + c.teacher));
			((TextView) ls.findViewById(R.id.couple_groups)).setText(String.format(getContext().getString(R.string.couple_groups), Utils.getStringFromArray(c.groups)));

			ll.addView(ls);
			count++;
		}
		if (count == 0) {
			TextView msg = new TextView(getContext());
			msg.setText(getContext().getString(R.string.couple_nothing));
			msg.setLayoutParams(getDefaultLayoutParams(MATCH_PARENT, WRAP_CONTENT));
			msg.setGravity(Gravity.CENTER_HORIZONTAL);
			msg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			msg.setPadding(0, 10, 10, 0);
			msg.setTextColor(Color.GRAY);
			ll.addView(msg);
		}

		addView(ll);
	}

	final public static int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
	final public static int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

	public static LinearLayout.LayoutParams getDefaultLayoutParams (int width, int height) {
		return new LinearLayout.LayoutParams(width, height);
	}
}
