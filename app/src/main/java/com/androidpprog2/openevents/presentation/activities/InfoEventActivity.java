package com.androidpprog2.openevents.presentation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.text.ParseException;
import java.util.ArrayList;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


//Aquesta activity ens permet implementar la funcionalitat de veure més en detall la informació de un Evento.
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


    //Aquesta funció implementa al trucada a la API de agafar de un evento en concret. Per això,
    //nosaltres haurem de rebre la id del evento en concret a través del intent i passarli a la API a part del accessToken corresponent.
    //Si la resposta de la API és correcta ens guardarem el evento en concret i el més important
    //és que li passarem aquest evento guardat a la funcio printEvent().
    private void findEvent() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        int id = getIntent().getExtras().getInt("id");

        Call<ArrayList<Event>> call = service.getEvent("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);
        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200) {
                        ArrayList<Event> events = response.body();
                        if (events.get(0)!= null) {
                            event = events.get(0);
                            try {
                                printEvent(events.get(0));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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

    //Aquesta funció rebre el evento en concret i setejarà tota la informació corresponent del evento.
    private void printEvent(Event event) throws ParseException {
        eventName.setText(event.getName());
        typeEvent.setText(event.getType());
        startDateEvent.setText(event.getStartDate());
        endDateEvent.setText(event.getEndDate());
        locationEvent.setText(event.getLocation());
        participantsEvent.setText(String.valueOf(event.getNumParticipants()));
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


