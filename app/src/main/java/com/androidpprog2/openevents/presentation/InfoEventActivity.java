package com.androidpprog2.openevents.presentation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class InfoEventActivity extends AppCompatActivity {

    private TextView eventName;
    private TextView typeEvent;
    private TextView participantsEvent;
    private TextView startDateEvent;
    private TextView endDateEvent;
    private TextView locationEvent;
    private TextView descriptionEvent;

    private ImageView imageEvent;

    private int event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_event);


        eventName = findViewById(R.id.event_name);
        typeEvent = findViewById(R.id.event_type);
        imageEvent = findViewById(R.id.event_image);
        startDateEvent = findViewById(R.id.event_start_date);
        endDateEvent = findViewById(R.id.event_end_date);
        locationEvent = findViewById(R.id.event_location);
        descriptionEvent = findViewById(R.id.event_description);

        findEvent();
    }

    private void findEvent() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        Call<ArrayList<Event>> call = service.getEvent(event_id);
        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()){
               //     if (response.code())
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

            }
        });
    }


}


