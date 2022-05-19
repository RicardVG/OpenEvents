package com.androidpprog2.openevents.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidpprog2.openevents.R;
//import com.androidpprog2.openevents.UserAToken;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.androidpprog2.openevents.business.LoginRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin,passwordLogin;
    private Button sign_in;
    private TextView sign_up;
    private String emailRegister, passwordRegister;



    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailRegister = getIntent().getStringExtra("email");
        passwordRegister = getIntent().getStringExtra("password");


        emailLogin = (EditText) findViewById(R.id.editText);
        passwordLogin = (EditText) findViewById(R.id.editText2);
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (TextView) findViewById(R.id.SignUp);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });

        // Button sign_in = this.findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailLogin.getText().toString().isEmpty() || passwordLogin.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                sign_in(emailLogin.getText().toString(), passwordLogin.getText().toString());


            }
        });
    }

    private void sign_in(String email, String password){

        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        LoginRequest loginRequest = new LoginRequest(email,password);
        Call<LoginRequest> callLoginRequest = service.loginUser(loginRequest);

       User user = new User(email, password);
       /*
        ArrayList<User> userArrayList = new ArrayList<>();
        Bundle bundle = new Bundle();

        for (User u : userArrayList) {
            if(email.equals(user.getEmail())){
               bundle.putString("id",getString(user.getId()));
            }
            break;
        }


      */


        callLoginRequest.enqueue(new Callback<LoginRequest>() {

            @Override
            public void onResponse(Call<LoginRequest> callLoginRequest, Response<LoginRequest> response) {
                Log.d("MAIN","TODOOK");
                if (response.code() == 200){
                    SharedPreferences sh = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("accessToken","Bearer" + response.body().getAccessToken() + user.getId()).apply();
                    Intent intent = EventsActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect data! Please try again",Toast.LENGTH_SHORT).show();

                }


            }
            @Override
            public void onFailure(Call<LoginRequest> callUserToken, Throwable t) {
                Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();
            }
        });
    }
}