package com.androidpprog2.openevents.presentation.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.androidpprog2.openevents.presentation.activities.EventsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.security.auth.callback.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

//Fragment que ens permet implementar la funcionalitat de Crear un Event.
public class CreateEventFragment extends Fragment implements Callback {

    private String DEFAULT_IMG = "https://images.unsplash.com/photo-1547826039-bfc35e0f1ea8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8YXJ0fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80";
    private MaterialButton createButton;
    private TextInputLayout nameInput;
    private TextInputLayout locationInput;
    private TextInputLayout descriptionInput;
    private TextInputLayout startDateInput;
    private TextInputLayout endDateInput;
    private TextInputLayout startTimeInput;
    private TextInputLayout endTimeInput;
    private TextInputLayout capacityInput;
    private TextInputLayout categoryInput;


    //Inflem un layout el qual té la vista del fragment. A més tindrem un buttó que ens permetrà
    //cridar la funció de crear un evento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_create_event, container, false);

        createButton = view.findViewById(R.id.create_button);
        nameInput = view.findViewById(R.id.createEvent_name);
        locationInput = view.findViewById(R.id.createEvent_location);
        descriptionInput = view.findViewById(R.id.createEvent_description);
        startDateInput = view.findViewById(R.id.createEvent_startDate);
        endDateInput = view.findViewById(R.id.createEvent_endDate);
        startTimeInput = view.findViewById(R.id.createEvent_startTime);
        endTimeInput = view.findViewById(R.id.createEvent_endTime);
        capacityInput = view.findViewById(R.id.createEvent_capacity);
        categoryInput = view.findViewById(R.id.createEvent_category);
        startDateInput.getEditText().setInputType(InputType.TYPE_NULL);
        endDateInput.getEditText().setInputType(InputType.TYPE_NULL);
        TextView titleCreateEvent = view.findViewById(R.id.titleCreateEvent);

        createButton.setOnClickListener(v -> { create_Event(); });

        return view;
    }


    //Aquesta funció validateData() si és true, passa totes les variables a String i les passa a la funció insertEvent().
    private void create_Event() {

        if(validateData()){
            String name = nameInput.getEditText().getText().toString();
            String location = locationInput.getEditText().getText().toString();
            String description = descriptionInput.getEditText().getText().toString();
            String startDate = startDateInput.getEditText().getText().toString();
            String endDate = endDateInput.getEditText().getText().toString();
            String category = categoryInput.getEditText().getText().toString();
            String capacity = capacityInput.getEditText().getText().toString();

            insertEvent(name, DEFAULT_IMG, location, description, startDate, endDate, category, capacity);

        }
    }


    //Aquesta funció rep tota la informació per paràmetre per crear un evento. En aquest cas,
    //com que la informació que ens demana la API com a entrada era una determinada, hem tingut
    //que cambiar el tipus de variable de algunes variables. És el cas del numero de participants
    //que l'hem tingut que cambiar a Integer. En el cas de les dates les hem parsejat.
    @SuppressLint("SimpleDateFormat")
    public void insertEvent(String name, String image, String location, String description, String eventStart_date,
                            String eventEnd_date, String type, String capacity) {

        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

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

        Call<Event> call = service.createEvent(
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM",name,image,location,description,startDate,endDate,n_participators,type);

        //Sí la resposta de la API és correcta mostrarem un missatge per pantalla i tornarem a la activity de Events, sino mostrarem els respectius missatges d'errors.
        call.enqueue(new retrofit2.Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        Toast.makeText(getContext(), R.string.createEvent_correct, Toast.LENGTH_SHORT).show();
                        Intent intent = EventsActivity.newIntent(getContext());
                        startActivity(intent);
                    }
                    else if (response.code() == 400){
                        Toast.makeText(getContext(),"Data is incorrect! Please enter another information", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        System.out.println(response.errorBody().toString());
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validateName(String name){
        if(!name.isEmpty()){
            nameInput.setErrorEnabled(false);
            return true;
        }
        nameInput.setError(getString(R.string.createEvent_name_error));
        return false;
    }

    public boolean validateLocation(String location){
        if(!location.isEmpty()){
            locationInput.setErrorEnabled(false);
            return true;
        }
        locationInput.setError(getString(R.string.createEvent_location_error));
        return false;
    }

    public boolean validateDescription(String description){
        if(!description.isEmpty()){
            descriptionInput.setErrorEnabled(false);
            return true;
        }
        descriptionInput.setError(getString(R.string.createEvent_description_error));
        return false;
    }

    public boolean validateStartDate(String startDate){
        if(!startDate.isEmpty()){
            startDateInput.setErrorEnabled(false);
            return true;
        }
        startDateInput.setError(getString(R.string.createEvent_startDate_error));
        return false;
    }

    public boolean validateEndDate(String endDate){
        if(!endDate.isEmpty()){
            endDateInput.setErrorEnabled(false);
            return true;
        }
        endDateInput.setError(getString(R.string.createEvent_endDate_error));
        return false;
    }

    public boolean validateStartTime(String startTime){
        if(!startTime.isEmpty()){
            startTimeInput.setErrorEnabled(false);
            return true;
        }
        startTimeInput.setError(getString(R.string.createEvent_startTime_error));
        return false;
    }

    public boolean validateEndTime(String endTime){
        if(!endTime.isEmpty()){
            endTimeInput.setErrorEnabled(false);
            return true;
        }
        endTimeInput.setError(getString(R.string.createEvent_endTime_error));
        return false;
    }

    public boolean validateCapacity(String capacity){
        if(!capacity.isEmpty()){
            if(TextUtils.isDigitsOnly(capacity)){
                capacityInput.setErrorEnabled(false);
                return true;
            }
            capacityInput.setError(getString(R.string.createEvent_capacity_error_syntax));
            return false;
        }
        capacityInput.setError(getString(R.string.createEvent_capacity_error));
        return false;
    }

    public boolean validateCategory(String category){
        if(!category.isEmpty()){
            categoryInput.setErrorEnabled(false);
            return true;
        }
        categoryInput.setError(getString(R.string.createEvent_capacity_error));
        return false;
    }

    //Aquesta funció retorna un booleà. Inicialment aquest booleà que detecta si hi ha hagut un error
    //o on en l'entrada de dades està a true. Amb els diferents ifs, comprobar que l'entrada de la info
    //sigui vàlida. En el cas afirmatiu passarem aquest booleà a false i retornarem aquesta variable.
    public boolean validateData(){
        boolean error = true;
        if(!validateName(nameInput.getEditText().getText().toString())) error = false;
        if(!validateLocation(locationInput.getEditText().getText().toString())) error = false;
        if(!validateDescription(descriptionInput.getEditText().getText().toString())) error = false;
        if(!validateCategory(categoryInput.getEditText().getText().toString())) error = false;
        if(!validateCapacity(capacityInput.getEditText().getText().toString())) error = false;
        if(!validateStartDate(startDateInput.getEditText().getText().toString())) error = false;
        if(!validateEndDate(endDateInput.getEditText().getText().toString())) error = false;
        if(!validateStartTime(startTimeInput.getEditText().getText().toString())) error = false;
        if(!validateEndTime(endTimeInput.getEditText().getText().toString())) error = false;
        return error;
    }
}
