package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsAdapter adapter;


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EventsActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        recyclerView = findViewById(R.id.eventsRecyclerView);



        getEvents();
        createEvent();
    }

    public void createEvent(){
        CreateEventFragment createEventFragment = new CreateEventFragment();
    }
    public void getEvents() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        //HEM DE COMPROBAR EL BEARER TOKEN

        SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","");

        adapter = new EventsAdapter(new ArrayList<>(), getApplicationContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        Call<ArrayList<Event>> call = service.getEvents(accessToken);
        call.enqueue(new Callback<ArrayList<Event>>() {

            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<Event> created = response.body();
                        adapter = new EventsAdapter(created, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect data. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();

        }
    });

    }
}
