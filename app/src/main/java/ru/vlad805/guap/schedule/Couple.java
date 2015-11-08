package ru.vlad805.guap.schedule;

import java.util.List;

import mjson.Json;
import ru.vlad805.guap.schedule.utils.Utils;

public class Couple {
	public byte coupleId;
	public String type;
	public String subject;
	public byte parity;
	public String build;
	public String[] audience;
	public String[] groups;
	public String teacher;

	public Couple (Json d) {
		coupleId = d.at("coupleId").asByte();
		type = d.at("type").asString();
		subject = d.at("subject").asString();
		parity = d.at("parity").asByte();
		build = d.at("build").asString();
		teacher = d.at("teacher").asString();

		List<Json> as = d.at("audiences").asJsonList();
		audience = new String[as.size()];
		for (int i = 0, l = as.size(); i < l; ++i) {
			audience[i] = as.get(i).asString();
		}

		List<Json> gs = d.at("groups").asJsonList();
		groups = new String[gs.size()];
		for (int i = 0, l = gs.size(); i < l; ++i) {
			groups[i] = gs.get(i).asString();
		}
	}

	public String getListAudience () {
		return Utils.getStringFromArray(audience);
	}

	public String getListGroups () {
		return Utils.getStringFromArray(groups);
	}
}
