package com.androidpprog2.openevents.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.androidpprog2.openevents.presentation.adapters.EventsAdapter;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Aquest és el fragment inicial que es carrega.
public class HomeFragment extends Fragment {
    private RecyclerView events_recyclerView;
    private EventsAdapter eventsAdapter;
    private TextView titleMyEvents;


    //Inflarem inicialment un layout que contrindrà la recycleview de events i cridem una funció
    //anomenada getEvents(). Finalment retornarem la vista.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        events_recyclerView = view.findViewById(R.id.eventsRecycleView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        events_recyclerView.setLayoutManager(llm);
        titleMyEvents = view.findViewById(R.id.titleMyEvents);

        getEvents();
        return view;
    }

    //Aquesta funció ens permet implementar la crida a la API de mostrar tots els eventos.
    //Li passarem el accessToken indicant que ha fet un login, i si la resposta és correcta
    //ens guardarem a una variable anomenada created la resposta de la API. Aquesta variable
    //serà un arraylist de eventos. Finalment, li passarem al adapter aquesta arraylist i setejarem
    //a la recycleview aquest adapter, perquè ens printi per pantalla tots els eventos.
    public void getEvents() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        Call<ArrayList<Event>> call = service.getEvents("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM");
        call.enqueue(new Callback<ArrayList<Event>>() {

            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<Event> created = response.body();
                        eventsAdapter = new EventsAdapter(created, getContext());
                        events_recyclerView.setAdapter(eventsAdapter);
                        Log.d("MAIN","TODOOKGETUSERRRSSS");
                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect data. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(getContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
