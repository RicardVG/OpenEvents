package com.androidpprog2.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidpprog2.openevents.api.JsonplaceholderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView sign_up = this.findViewById(R.id.SignUp);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Register.newIntent(Login.this);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonplaceholderAPI service = retrofit.create(JsonplaceholderAPI.class);

        service.getTodo().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("MAIN","TODOOK");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("MAIN","TODOOK");
            }
        });
    }
}