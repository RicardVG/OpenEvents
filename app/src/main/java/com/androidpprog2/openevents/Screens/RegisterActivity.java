package com.androidpprog2.openevents.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.User;
import com.androidpprog2.openevents.api.OpenEventsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText first_name, last_name, password, email, image;
    Button create_account;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        first_name = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText4);
        last_name = (EditText) findViewById(R.id.editText6);
        image = (EditText) findViewById(R.id.editText8);
        create_account = (Button) findViewById(R.id.create_account);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || first_name.getText().toString().isEmpty() || last_name.getText().toString().isEmpty() || image.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }
                create_account(email.getText().toString(), password.getText().toString(), first_name.getText().toString(), last_name.getText().toString(), image.getText().toString());
            }
        });
    }

    private void create_account(String first_name, String last_name, String email, String password, String image) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.205.68/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        User user = new User(first_name, last_name, email, password, image);
        Call<User> call = service.createPost(user);

        call.enqueue(new Callback<User>() {
            @Override

            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(RegisterActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                Log.d("MAIN", "TODOOK");
                Intent intent = LoginActivity.newIntent(RegisterActivity.this);
               ;
                startActivity(intent);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Incorrect data!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

