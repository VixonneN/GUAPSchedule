package ru.vlad805.guap.schedule.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.vlad805.guap.schedule.utils.Utils;

/**
 * Created by arktic on 08.11.15.
 */
public interface RestApi {

	@GET("/guap.parseSchedule?v=" + Utils.API_VERSION)
	Call<Schedule> parseSchedule(@Query("groupId") String groupId);

	@GET("/guap.getGroups?v=" + Utils.API_VERSION)
	Call<Groups> getGroups();

}
