package ru.vlad805.guap.schedule;

import java.util.HashMap;
import java.util.List;

import mjson.Json;

public class ScheduleAdapter {

	public String updated;
	public Day[] schedule;
	public HashMap<Byte, CoupleTime> couples = new HashMap<>();

	public ScheduleAdapter (Json d) {
		updated = d.at("parseDate").asString();
		parseSchedule(d.at("schedule").asJsonList());
		parseCouples(d.at("couples").asJsonList());
	}

	private void parseCouples(List<Json> c) {
		CoupleTime o;
		for (Json couple : c) {
			o = new CoupleTime(couple);
			couples.put(o.id, o);
		}
	}

	private void parseSchedule(List<Json> d) {
		Day i;
		schedule = new Day[d.size()];

		int k = 0;
		for (Json day : d) {
			i = new Day(day);
			schedule[k++] = i;
		}
	}

	public CoupleTime getCoupleTime (byte i) {
		return couples.get(i);
	}

	public Day getDay (byte d) {
		return d >= 0 && d < schedule.length ? schedule[d] : null;
	}
}
