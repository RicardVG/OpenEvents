package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.User;
import com.androidpprog2.openevents.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
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


}