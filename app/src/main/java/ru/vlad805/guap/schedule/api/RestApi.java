package ru.vlad805.guap.schedule.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by arktic on 08.11.15.
 */
public interface RestApi {

    @GET("/guap.parseSchedule")
    Schedule parseSchedule(@Query("groupId") int groupId);

    @GET("/guap.getGroups")
    Call<Groups> getGroups();

}
