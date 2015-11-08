package ru.vlad805.guap.schedule.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mjson.Json;
import ru.vlad805.guap.schedule.R;
import ru.vlad805.guap.schedule.activities.DrawerActivity;
import ru.vlad805.guap.schedule.utils.API;
import ru.vlad805.guap.schedule.utils.APICallback;
import ru.vlad805.guap.schedule.utils.APIError;
import ru.vlad805.guap.schedule.utils.Utils;

/**
 * Created by arktic on 08.11.15.
 */
public class SelectGroupFragment extends Fragment implements View.OnClickListener {

    private View root;
    private ArrayList<String> items = new ArrayList<>();
    private Spinner spinner;

    private Utils u;
    private ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view;

        u = new Utils(getContext());

        progress = u.showProgress(getString(R.string.alert_loading_groups));

        API.invoke(getContext(), "guap.getGroups", new HashMap<String, String>(), new APICallback() {
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

        view.findViewById(R.id.select_submit).setOnClickListener(this);
    }

    public void showItems (List<Json> data) {
        for (Json item : data) {
            items.add(item.at("groupId").asString());
        }

        spinner = (Spinner) root.findViewById(R.id.select_spinner_group);
        ArrayAdapter<?> adapter =  new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
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
                startActivity(new Intent(getContext(), DrawerActivity.class));
                getActivity().finish();//TODO: bad stuff
        }
    }
}
