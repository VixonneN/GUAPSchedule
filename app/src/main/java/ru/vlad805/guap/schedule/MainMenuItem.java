package ru.vlad805.guap.schedule;

import android.support.v4.app.Fragment;

import ru.vlad805.guap.schedule.fragments.AboutFragment;
import ru.vlad805.guap.schedule.fragments.ScheduleListFragment;
import ru.vlad805.guap.schedule.fragments.SelectGroupFragment;

/**
 * Created by arktic on 08.11.15.
 */
public enum MainMenuItem {
    SCHEDULE (new ScheduleListFragment(), R.id.item_schedule, R.string.drawer_title_schedule_all),
    SELECT (new SelectGroupFragment(), R.id.item_select_group, R.string.drawer_title_settings),
    ABOUT (new AboutFragment(), R.id.item_about, R.string.drawer_title_about);

    public static MainMenuItem defaultItem() {
        return SCHEDULE;
    }

    public final Fragment fragment;
    public final int item;
    public final int title;

    MainMenuItem(Fragment fragment, int item, int title) {
        this.fragment = fragment;
        this.item = item;
        this.title = title;
    }
}
