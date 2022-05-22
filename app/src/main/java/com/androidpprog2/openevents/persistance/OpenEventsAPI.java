package com.androidpprog2.openevents.persistance;

import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.LoginRequest;
import com.androidpprog2.openevents.business.User;
import java.util.ArrayList;
import java.util.Date;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<ArrayList<Event>> getEvents(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("events")
    Call<Event> createEvent(@Header("Authorization") String accessToken,
                            @Field("name") String name,
                            @Field("image") String image,
                            @Field("location") String location,
                            @Field("description") String description,
                            @Field("eventStart_date") Date eventStart_date,
                            @Field("eventEnd_date") Date eventEnd_date,
                            @Field("n_participators") int n_participators,
                            @Field("type") String type);
    @GET("events/{id}")
    Call<ArrayList<Event>> getEvent(@Header("Authorization") String accessToken, @Path("id") int id);

    @GET("users/search")
    Call<ArrayList<User>> searchUsers(@Header("Authorization") String accessToken, @Query ("s") String s);

    @DELETE("events/{id}")
    Call<Event> deleteEvent(@Header("Authorization") String accessToken, @Path("id") int id);

    @GET("users/{id}")
    Call<ArrayList<User>> getUserProfile(@Header("Authorization") String accessToken, @Path("id") int id);

    @GET("users")
    Call<ArrayList<User>> getUsers(@Header("Authorization") String accessToken);

    @GET("users/{id}/statistics")
    Call<User> getStatistics(@Header("Authorization") String accessToken, @Path("id") int id);

    @PUT("users")
    Call<User> updateUser(@Header("authorization") String accessToken, @Body User user);
}