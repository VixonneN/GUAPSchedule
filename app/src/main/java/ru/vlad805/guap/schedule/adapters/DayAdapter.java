package ru.vlad805.guap.schedule.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import ru.vlad805.guap.schedule.api.Schedule;
import ru.vlad805.guap.schedule.views.DayView;

/**
 * Created by arktic on 14.11.15.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    public static class DayHolder extends RecyclerView.ViewHolder {

        public DayView view;

        public DayHolder(DayView itemView) {
            super(itemView);
            view = itemView;
        }
    }

    private Context mContext;
    private Schedule.Response mData;
    private int mIsParityNow;

    public DayAdapter(Context context) {
        mContext = context;
    }

    public void setData(Schedule.Response data, int isParityNow) {
        mData = data;
        mIsParityNow = isParityNow;
        notifyDataSetChanged();
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayHolder(new DayView(mContext));
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        holder.view.populate(
                mData.schedule.get(position),
                mData.couples,
                mIsParityNow
        );
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.schedule.size() : 0;
    }
}
