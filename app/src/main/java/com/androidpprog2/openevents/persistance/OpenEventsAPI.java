package com.androidpprog2.openevents.persistance;

import android.content.SharedPreferences;

import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.business.LoginRequest;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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


    @DELETE("events/{id}")
    Call<Void> deleteEvent(@Header("authorization") String token, @Path("id") int id);

    @GET("users/{id}")
    Call<ArrayList<User>> getUserProfile(@Header("Authorization") String accessToken, @Path("id") int id);


    @GET("events/{id}/assistances")//numero dassistents dins de un event
    Call<ArrayList<User>> getAssistances(@Header("authorization") String token, @Path("id") int id);

    @PUT("users")
    Call<User> updateUser(@Header("authorization") String accessToken,
                                   @Body User user);

}