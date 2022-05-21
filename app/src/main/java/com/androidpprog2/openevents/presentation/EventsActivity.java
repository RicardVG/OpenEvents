package com.androidpprog2.openevents.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private String token;
    private CreateEventFragment createEventFragment;
    private ImageView addEvent;
    private TextView titleMyEvents;
    private TextView titleCreateEvent;
    private Event event;

    public EventsActivity() {

    }


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EventsActivity.class);
        return intent;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        loadFragment(new HomeFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setOnItemSelectedListener(this);



        recyclerView = findViewById(R.id.eventsRecyclerView);
        titleMyEvents = findViewById(R.id.titleMyEvents);
        titleCreateEvent = findViewById(R.id.titleCreateEvent);

        getEvents();


        addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.GONE);
                titleMyEvents.setVisibility(View.INVISIBLE);
                titleCreateEvent.setVisibility(View.VISIBLE);
                addEvent.setVisibility(View.INVISIBLE);

                createEvent();
            }
        });




    }

    public void createEvent(){
        CreateEventFragment createEventFragment = new CreateEventFragment();
        moveToFragment(createEventFragment);
    }

    private void moveToFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }


    @SuppressLint("SimpleDateFormat")
    public void insertEvent(String name, String image, String location, String description, String eventStart_date,
                            String eventEnd_date, String type, String capacity) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");
        System.out.println(accessToken);

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventStart_date);
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventEnd_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        int n_participators;
        n_participators = Integer.parseInt(capacity);
    //    Event event = new Event(name,image,location,description,eventStart_date,eventEnd_date,n_participators,type);

        Call<Event> call = service.createEvent(
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM",name,image,location,description,startDate,endDate,n_participators,type);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), R.string.createEvent_correct, Toast.LENGTH_SHORT).show();
                        Intent intent = EventsActivity.newIntent(EventsActivity.this);
                        startActivity(intent);
                    }
                    else if (response.code() == 400){
                        Toast.makeText(getApplicationContext(),"Data is incorrect! Please enter another information", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        System.out.println(response.errorBody().toString());
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            //    createEventFragment.loading(false);
            }

        });


    }


    public void getEvents() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);


        SharedPreferences sh = getSharedPreferences("sh",Context.MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");
        System.out.println(accessToken);

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
                        Log.d("MAIN","TODOOKGETUSERRRSSS");
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

    public void deleteEvent(View view){
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

       int id = ((Integer) view.getTag());

       SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

        Call<Void> call = service.deleteEvent(accessToken, id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Deleted Event");
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Not Deleted");
            }
        });



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                recyclerView.setVisibility(View.VISIBLE);
             //   titleCreateEvent.setVisibility(View.VISIBLE);
             //   addEvent.setVisibility(View.VISIBLE);

                fragment = new HomeFragment();

                break;

            case R.id.profile:
                recyclerView.setVisibility(View.GONE);
                addEvent.setVisibility(View.GONE);
                titleMyEvents.setVisibility(View.INVISIBLE);
                fragment = new UserProfileFragment();


                break;
        }

        return loadFragment(fragment);
    }
}
