package ru.vlad805.guap.schedule;

import java.util.List;

import mjson.Json;

public class Day {
	public byte dayId;
	public String title;
	public Couple[] couples;

	public Day (Json d) {
		dayId = d.at("dayId").asByte();
		title = d.at("title").asString();
		List<Json> as = d.at("couples").asJsonList();
		couples = new Couple[as.size()];
		byte i = 0;
		for (Json item : as) {
			couples[i++] = new Couple(item);
		}
	}

	public Couple getCouple (byte i) {
		return i >= 0 && i < couples.length ? couples[i] : null;
	}

}
