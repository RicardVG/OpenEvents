package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpenEventsAPI {

    @POST("/users")
    Call<User> getTodo();

    @POST("users/login")
    Call<User> getTodos();

    @FormUrlEncoded
    @POST("users/")
    Call<User> createPost(
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("image") String image
    );


}