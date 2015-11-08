package ru.vlad805.guap.schedule.utils;

import mjson.Json;

public interface APICallback {
	void onResult (Json result);
	void onError (APIError e);
}
