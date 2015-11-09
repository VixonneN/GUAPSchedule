package ru.vlad805.guap.schedule.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.List;

public class Utils {

	final public static String API_VERSION = "2.01";

	private Context context;

	public Utils (Context ctx) {
		context = ctx;
	}

	public SharedPreferences getSettings () {
		return context.getSharedPreferences("vlad805guap", Context.MODE_PRIVATE);
	}

	public boolean hasString (String key) {
		return getSettings().contains(key);
	}

	public String getString (String key) {
		return getSettings().getString(key, "");
	}

	public Utils setString (String key, String value) {
		getSettings().edit().putString(key, value).apply();
		return this;
	}

	public Utils removeString (String key) {
		getSettings().edit().remove(key).apply();
		return this;
	}

	public static String getStringFromArray (String[] s) {
		StringBuilder sb = new StringBuilder();
		int i = s.length;
		for (String a : s) {
			sb.append(a).append(--i > 0 ? ", " : "");
		}
		return sb.toString();
	}

	public static String getStringFromArray (List<String> s) {
		StringBuilder sb = new StringBuilder();
		int i = s.size();
		for (String a : s) {
			sb.append(a).append(--i > 0 ? ", " : "");
		}
		return sb.toString();
	}

	/*public static boolean isParity () {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return (cal.get(Calendar.WEEK_OF_YEAR) % 2) > 0;
	}*/

	public ProgressDialog showProgress (String text) {
		return showProgress(null, text);
	}

	public ProgressDialog showProgress (String title, String text) {
		ProgressDialog pd = new ProgressDialog(context);


		if (title != null) {
			pd.setTitle(title);
		}
		pd.setMessage(text);
		pd.setIndeterminate(true);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(false);
		pd.show();
		return pd;
	}

	public void toast (String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}
