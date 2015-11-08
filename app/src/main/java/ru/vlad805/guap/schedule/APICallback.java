package ru.vlad805.guap.schedule;

import mjson.Json;

public interface APICallback {
	void onResult (Json result);
	void onError (APIError e);
}
