package ru.vlad805.guap.schedule.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.vlad805.guap.schedule.R;

/**
 * Created by arktic on 08.11.15.
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String version = "1.0";

        try {
            PackageInfo data = getContext().getPackageManager().getPackageInfo(getContext().getApplicationInfo().packageName, PackageManager.GET_CONFIGURATIONS);
            version = data.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.about_version)).setText("v" + version);
    }
}
