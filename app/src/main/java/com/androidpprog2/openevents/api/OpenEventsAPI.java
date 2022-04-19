package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpenEventsAPI {

    @POST("/users")
    Call<User> getTodo();

    @POST("users/login")
    Call<User> getTodos();

    @POST("users/")
    Call<User> createPost(@Body User user);


}