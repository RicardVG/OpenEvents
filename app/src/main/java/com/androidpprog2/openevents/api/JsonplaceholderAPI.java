package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonplaceholderAPI {

    @POST("/users")
    Call<User> getTodo();
}