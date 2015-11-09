package ru.vlad805.guap.schedule.api;

import java.util.List;

/**
 * Created by arktic on 08.11.15.
 */
public class Groups {
	public static class Response {
		public static class Group {
			public String groupId;
			public int gid;
		}

	    public int count;
	    public List<Group> items;
	}

	public Response response;
}
