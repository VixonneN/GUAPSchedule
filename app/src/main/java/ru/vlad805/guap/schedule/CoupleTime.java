package ru.vlad805.guap.schedule;

import mjson.Json;

public class CoupleTime {
	public byte id;
	public String start;
	public String end;

	public CoupleTime (Json d) {
		id = d.at("coupleId").asByte();
		start = d.at("start").asString();
		end = d.at("end").asString();
	}
}
