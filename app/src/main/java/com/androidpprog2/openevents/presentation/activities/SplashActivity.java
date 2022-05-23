package com.androidpprog2.openevents.presentation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.androidpprog2.openevents.R;


//Aquesta activitat és la primera que salta i seria una pantalla que es mostra a l'usuari un total de 3 segons.
//Aquesta pantalla ens permet donar pas a la pantalla inicial de Login.

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}