package com.androidpprog2.openevents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.api.JsonplaceholderAPI;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText first_name, last_name, password, email, image;
    Button create_account;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Register.class);
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
                    Toast.makeText(Register.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }
                create_account(email.getText().toString(), password.getText().toString(), first_name.getText().toString(), last_name.getText().toString(), image.getText().toString());
            }
        });
    }

    private void create_account(String first_name, String last_name, String email, String password, String image) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonplaceholderAPI service = retrofit.create(JsonplaceholderAPI.class);
        User user = new User(first_name, last_name, email, password, image);
        Call<User> call = service.createPost(user);

        call.enqueue(new Callback<User>() {
            @Override

            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(Register.this, "Data added to API", Toast.LENGTH_SHORT).show();
                Log.d("MAIN", "TODOOK");
                Intent intent = Login.newIntent(Register.this);
                startActivity(intent);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Incorrect data!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

/*
                    //ASSIGNAR CADA VARIABLE DE USER AMB LA INFO QUE S'ENTRA.
                    //LI PASO VERDADERAMENT EL JSON AMB LA INFO? CREC QUE PER AIXO FALLA I SEMPRE SURT TOAST INCORRECTE

                    //A LA CLASSE DE LOGIN HAURE DE RECUPERAR LA INFO DE REGISTER I COMPROBAR QUE ES CORRECTE.

                    service.getTodo().enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("MAIN", "TODOOK");
                            Intent intent = Login.newIntent(Register.this);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Incorrect data!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


        });
    }


} */
