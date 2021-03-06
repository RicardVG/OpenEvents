package com.androidpprog2.openevents.persistance;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Classe que conté Retrofit i ens serveix per cridar la funció un sol cop en qualsevol lloc (agafar la instància)
public class APIClient {
    private static final String API_URL ="http://172.16.205.68/api/v2/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
