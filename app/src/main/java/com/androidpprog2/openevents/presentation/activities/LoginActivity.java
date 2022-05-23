package com.androidpprog2.openevents.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.androidpprog2.openevents.business.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


//Activitat que conté tota la lògica de iniciar sessió de un usuari.
public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin,passwordLogin;
    private Button sign_in;
    private TextView sign_up;
    private String emailRegister, passwordRegister;
    private User user;

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

        //Aquesta funció ens permet que quan pulsem al text de SignUP ens redirigeixi a través de un Intent a la Register Activity.
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });

        //Aquesta funció ens permet que quan pulsem al buttó de sign_in, primer comproba si els camps estan plens. Si no és així,
        //salta per pantalla un missatge d'error fins que l'usuari entri els dos camps. Un cop entri els dos camps i li doni al buttó
        //s'executa la funció de sign_in.
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

    //Aquesta funció conté la implementació de la crida de la API de iniciar sessió de un usuari.
    //Li passem una instància de l'objecte Login Request que conté l'email i el password.
    private void sign_in(String email, String password){
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        LoginRequest loginRequest = new LoginRequest(email,password);
        Call<LoginRequest> callLoginRequest = service.loginUser(loginRequest);

        callLoginRequest.enqueue(new Callback<LoginRequest>() {

            @Override
            public void onResponse(Call<LoginRequest> callLoginRequest, Response<LoginRequest> response) {
                //Si la resposta de la API és correcta ens guardem a Shared Preferences el correu de l'usuari i ens
                //dirigim a la Events Activity. Si és incorrecte mostrem missatges d'errors.
                if (response.code() == 200){
                    SharedPreferences sh = getSharedPreferences("sh",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("email", email);
                    editor.apply();
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