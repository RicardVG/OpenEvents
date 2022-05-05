package com.androidpprog2.openevents.persistance;

import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.business.LoginRequest;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface OpenEventsAPI {

    @POST("users/login/")
    Call<LoginRequest> loginUser(
            @Body LoginRequest userAToken
    );


    @POST("users/")
    Call<User> registerUser(
            @Body User user
    );

    @POST("events")
    Call<Event> createEvent(@Header("authorization") String token,
                            @Part("name") RequestBody name,
                            @Part MultipartBody.Part image,
                            @Part("location") RequestBody location,
                            @Part("description") RequestBody description,
                            @Part("eventStart_date") Date eventStart_date,
                            @Part("eventEnd_date") Date eventEnd_date,
                            @Part("n_participators") RequestBody n_participators,
                            @Part("type") RequestBody type);


}