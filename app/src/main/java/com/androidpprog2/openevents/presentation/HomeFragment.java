package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {


    private RecyclerView events_recyclerView;
    private EventsAdapter eventsAdapter;
    private TextView titleMyEvents;

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



    public void getEvents() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

      /*  SharedPreferences sh = getSharedPreferences("sh", Context.MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");
        System.out.println(accessToken);

       */

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


    public void deleteEvent(View view){
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        int id = ((Integer) view.getTag());

     /*   SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

      */

        Call<Event> call = service.deleteEvent("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                System.out.println("Deleted Event");

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                System.out.println("Not Deleted");
            }
        });

    }

}
