package com.androidpprog2.openevents.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
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

public class EventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private String token;
    private CreateEventFragment createEventFragment;
    private ImageView addEvent;
    private TextView titleMyEvents;
    private TextView titleCreateEvent;
    private SharedPreferences sh;

    public EventsActivity() {

    }


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EventsActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        recyclerView = findViewById(R.id.eventsRecyclerView);
        titleMyEvents = findViewById(R.id.titleMyEvents);
        titleCreateEvent = findViewById(R.id.titleCreateEvent);
        getEvents();
        sh = getSharedPreferences("sh",MODE_PRIVATE);


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
                .replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        System.out.println("HOLA");
    }


    @SuppressLint("SimpleDateFormat")
    public void insertEvent(String name, String image, String location, String description, String eventStart_date,
                            String eventEnd_date, String type, String n_participators) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventStart_date);
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventEnd_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("image", image, requestFile);
        Call<Event> call = service.createEvent(
                accessToken,
                RequestBody.create(MultipartBody.FORM, name),
                requestImage,
                RequestBody.create(MultipartBody.FORM, location),
                RequestBody.create(MultipartBody.FORM, description),
                startDate,
                endDate,
                RequestBody.create(MultipartBody.FORM, n_participators),
                RequestBody.create(MultipartBody.FORM, type));

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                createEventFragment.loading(false);
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        createEventFragment.refreshTextFields();
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
                createEventFragment.loading(false);
            }

        });


    }


    public void getEvents() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        //HEM DE COMPROBAR EL BEARER TOKEN

        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

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
    public void delete_event(){
        deleteEvent();

    }

    private void deleteEvent(){
        Call<Void> call = OpenEventsAPI.deleteEvent(1);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Deleted Event");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Not Deleted");
            }
        });
    }
}
