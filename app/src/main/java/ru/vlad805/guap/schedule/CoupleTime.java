package ru.vlad805.guap.schedule;

import mjson.Json;

public class CoupleTime {
	public byte id;
	public String start;
	public String end;

	public CoupleTime (Json d) {
		id = d.at(0).asByte();
		start = d.at(1).asString();
		end = d.at(2).asString();
	}
}
