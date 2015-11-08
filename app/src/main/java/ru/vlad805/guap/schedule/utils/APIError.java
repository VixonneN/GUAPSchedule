package ru.vlad805.guap.schedule.utils;

import mjson.Json;
import ru.vlad805.guap.schedule.utils.API;

public class APIError {
	private int errorId;
	private String errorInfo;

	public APIError (Json j) {
		if (j.has(API.RESPONSE))
			j = j.at(API.RESPONSE);
		errorId = j.at("errorId").asInteger();
		errorInfo = j.at("errorInfo").asString();
	}
	public String getString () {
		return "Error API: " + errorId + ": " + errorInfo;
	}

	public int getErrorId() {
		return errorId;
	}

	public String getErrorInfo() {
		return errorInfo;
	}
}
