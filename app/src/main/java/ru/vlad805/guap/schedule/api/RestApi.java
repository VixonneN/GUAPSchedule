package ru.vlad805.guap.schedule.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by arktic on 08.11.15.
 */
public interface RestApi {

    @GET("/guap.parseSchedule?v=2.01")
    Call<Schedule> parseSchedule(@Query("groupId") String groupId);

    @GET("/guap.getGroups?v=2.01")
    Call<Groups> getGroups();

}
