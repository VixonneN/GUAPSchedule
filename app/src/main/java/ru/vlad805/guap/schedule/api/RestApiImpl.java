package ru.vlad805.guap.schedule.api;

import com.google.gson.Gson;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import ru.vlad805.guap.schedule.utils.API;

/**
 * Created by arktic on 08.11.15.
 */
public enum RestApiImpl {
    INSTANCE;

    Retrofit retrofit;

    RestApiImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.vlad805.ru")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public RestApi getApi() {
        return retrofit.create(RestApi.class);
    }
}
