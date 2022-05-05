package com.androidpprog2.openevents.persistance;

import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.business.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OpenEventsAPI {

    @POST("users/login/")
    Call<LoginRequest> loginUser(
            @Body LoginRequest userAToken
    );


    @POST("users/")
    Call<User> registerUser(
            @Body User user
    );

    @GET("events")
    Call<Event> generalEvent();


}