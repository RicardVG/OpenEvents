package com.androidpprog2.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidpprog2.openevents.api.JsonplaceholderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

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

        Button sign_in = this.findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://puigmal.salle.url.edu/api/v2")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonplaceholderAPI service = retrofit.create(JsonplaceholderAPI.class);

                service.getTodos().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("MAIN","TODOOK");
                        SharedPreferences sh = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        int nuevoAccessToken = 0;
                        editor.putInt("accessToken", nuevoAccessToken);
                        editor.commit();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Incorrect data! Please try again",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}