package com.androidpprog2.openevents.presentation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
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
    private SharedPreferences sh;

    private Event event;

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
        participantsEvent = findViewById(R.id.event_participants);

        findEvent();
    }


    //pilar la id i passarli
    private void findEvent() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        int id = getIntent().getExtras().getInt("id");
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");
        Call<ArrayList<Event>> call = service.getEvent(accessToken, id);
        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200) {
                        ArrayList<Event> events = response.body();
                        if (events.get(0)!= null) {
                            event = events.get(0);
                            printEvent(events.get(0));
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
                
            }
            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void printEvent(Event event) {

        eventName.setText(event.getName());
        typeEvent.setText(event.getType());
     //   participantsEvent.setText(event.getNumParticipants());
        startDateEvent.setText(event.getStartDate());
        endDateEvent.setText(event.getEndDate());
        locationEvent.setText(event.getLocation());
        descriptionEvent.setText(event.getDescription());

        String url = "";

        if (this.event.getImage() != null) {
            if (this.event.getImage().startsWith("https")) {
                url = this.event.getImage();
            } else {
                url = "https://172.16.205.68/img/" + this.event.getImage();
            }
        }
        Glide.with(getApplicationContext())
                .load(url)
                .apply(RequestOptions
                        .bitmapTransform(new BlurTransformation(8, 1))
                        .placeholder(R.drawable.default_event)
                        .error(R.drawable.default_event))
                .into(imageEvent);


    }
}


