package ru.vlad805.guap.schedule.fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.vlad805.guap.schedule.R;

/**
 * Created by arktic on 08.11.15.
 */
public class AboutFragment extends Fragment {

	@Bind(R.id.about_copyrights) TextView tvCopyrights;
	@Bind(R.id.about_version) TextView tvVersion;

	@Nullable
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_about, container, false);
		ButterKnife.bind(this, root);
	    return root;
	}

	@Override
	public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
	    super.onViewCreated(view, savedInstanceState);
	    String version = "1.0";

	    try {
	        PackageInfo data = getContext().getPackageManager().getPackageInfo(getContext().getApplicationInfo().packageName, PackageManager.GET_CONFIGURATIONS);
	        version = data.versionName;
	    } catch (PackageManager.NameNotFoundException e) {
	        e.printStackTrace();
	    }

	    tvVersion.setText("v" + version);

		// нифига не пашет TODO: исправить кликабильность линков на профили
//		tvCopyrights.setLinksClickable(true);
		tvCopyrights.setText(Html.fromHtml(getString(R.string.app_copyrights)));
	}

	@OnClick(R.id.about_web_version)
	void onWebVersionClicked () {
		Log.i("onClick", "clicked!");
		openLink("http://guap.vlad805.ru/");
	}

	public void openLink (String url) {
	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    startActivity(intent);
	}
}
