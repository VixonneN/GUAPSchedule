package ru.vlad805.guap.schedule.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import mjson.Json;

public class API {
	final public static String API_DOMAIN = "api.vlad805.ru";
	final public static String V = "v";
	final public static String USER_ACCESS_TOKEN = "userAccessToken";
	final public static String USER_ID = "userId";
	final public static String USER_IDS = "userIds";
	final public static String RESPONSE = "response";
	final public static String RESULT = "result";

	private Context context;
	private String method;
	private HashMap<String, String> params;
	private Json result;
	private APICallback callback = null;

	public API (Context ctx, String method, HashMap<String, String> params, APICallback callback) {
		this.context = ctx;
		this.method = method;
		this.params = params == null ? new HashMap<String, String>() : params;
		this.callback = callback;
	}

	public void send () throws NotAvailableInternetException {
		StringBuilder p = new StringBuilder();

		for (String key: params.keySet())
			p.append(key).append("=").append(params.get(key)).append("&");

		Log.i("APIRequestParams<" + method + ">", p.toString());

		new AsyncLoadData().execute("http://" + API_DOMAIN + "/" + method, p.toString());
	}

	public Json getResult () {
		return result;
	}

	private class AsyncLoadData extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... url) {
			Internet request = new Internet(context);
			String buffer = null;
			try {
				buffer = request.send(url[0], url[1]);
			} catch (NotAvailableInternetException e) {
				e.printStackTrace();
				publishProgress(-1);
			}
			return buffer;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
//			if (progress[0] == -1)

		}

		@Override
		protected void onPostExecute (String r) {
			if (r == null) {
				callback.onError(null);
				return;
			}
			result = Json.read(r);
			result = result.at("response");
			if (callback != null) {
				if (result.isObject() && result.has("errorId"))
					callback.onError(new APIError(result));
				else
					callback.onResult(result);
			}
		}
	}

	public static void invoke (Context context, String method, HashMap<String, String> params, APICallback callback) {
		try {
			new API(context, method, params, callback).send();
		} catch (NotAvailableInternetException e) {
			e.printStackTrace();
		}
	}
}
