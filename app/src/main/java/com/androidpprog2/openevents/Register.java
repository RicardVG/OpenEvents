package com.androidpprog2.openevents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.api.JsonplaceholderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button create_account = this.findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://puigmal.salle.url.edu/api/v2")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonplaceholderAPI service = retrofit.create(JsonplaceholderAPI.class);

                service.getTodo().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("MAIN","TODOOK");
                        Intent intent = Login.newIntent(Register.this);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Incorrect data!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
