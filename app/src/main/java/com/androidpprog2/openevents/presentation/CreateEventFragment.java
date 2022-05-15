package com.androidpprog2.openevents.presentation;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.Calendar;

import javax.security.auth.callback.Callback;

public class CreateEventFragment extends Fragment implements Callback {

    private String DEFAULT_IMG;
    private MaterialButton createBtn;
    private TextInputLayout nameInput;
    private TextInputLayout locationInput;
    private TextInputLayout descriptionInput;
    private TextInputLayout startDateInput;
    private TextInputLayout endDateInput;
    private TextInputLayout startTimeInput;
    private TextInputLayout endTimeInput;
    private TextInputLayout capacityInput;
    private TextInputLayout categoryInput;

    private String[] categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_create_event, container, false);
        createBtn = (MaterialButton) view.findViewById(R.id.create_btn);

        categories = new String[] {getString(R.string.category_art), getString(R.string.category_cultural),
                getString(R.string.category_education), getString(R.string.category_games), getString(R.string.category_music),
                getString(R.string.category_politics), getString(R.string.category_science), getString(R.string.category_sport),
                getString(R.string.category_technology), getString(R.string.category_others)};



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

        //validationListeners();

        createBtn.setOnClickListener(v -> { create_Event(); });

        return view;
    }

    private void create_Event() {
        if(validateData()){
            String name = nameInput.getEditText().getText().toString();
            String location = locationInput.getEditText().getText().toString();
            String description = descriptionInput.getEditText().getText().toString();
            String startDate = startDateInput.getEditText().getText().toString();
            String endDate = endDateInput.getEditText().getText().toString();
            String category = categoryInput.getEditText().getText().toString();
            String capacity = capacityInput.getEditText().getText().toString();

            loading(true);

            setDefaultImage(category);
            EventsActivity eventsActivity = new EventsActivity();
            eventsActivity.insertEvent(name, DEFAULT_IMG, location, description, startDate, endDate, category, capacity);

        }
    }

    public void loading(boolean state) {
        boolean enable = !state;

        createBtn.setEnabled(enable);
        nameInput.setEnabled(enable);
        locationInput.setEnabled(enable);
        descriptionInput.setEnabled(enable);
        startDateInput.setEnabled(enable);
        startTimeInput.setEnabled(enable);
        endDateInput.setEnabled(enable);
        endTimeInput.setEnabled(enable);
        categoryInput.setEnabled(enable);
        capacityInput.setEnabled(enable);

        if (state) {
            createBtn.setVisibility(View.GONE);
        } else {
            createBtn.setVisibility(View.VISIBLE);
        }
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


    public void setDefaultImage(String category){
        String[] urls = new String[] {"https://images.unsplash.com/photo-1547826039-bfc35e0f1ea8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8YXJ0fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80",
                "https://nucleovisual.com/wp-content/uploads/2019/12/Cultura-material-e-inmaterial-01.jpg",
                "https://www.unir.net/wp-content/uploads/2019/10/iStock-1000887536.jpg",
                "https://i.pcmag.com/imagery/roundups/04nBPwuUCcHJKEjeohmzNEA-5.fit_lim.size_1050x.jpg",
                "https://storage.googleapis.com/gweb-uniblog-publish-prod/original_images/YT_Music.jpg",
                "https://www.voicesofyouth.org/sites/voy/files/styles/blog_teaser/public/images/2019-01/politics3.jpg?h=ae1281eb&itok=7pVud1WX",
                "https://www.rd-alliance.org/sites/default/files/Banner%20DNA%20ORION%20MOOC.jpg",
                "https://www.okomeds.com/wp-content/uploads/2016/02/p18lq7ediepl816p6s04171vo23.jpg",
                "https://i.hurimg.com/i/hdn/75/0x0/5da425cc0f25441cf4279f1d.jpg",
                "https://sloanreview.mit.edu/wp-content/uploads/2017/04/DL-Mukherjee-Digital-Leadership-Neutrality-Neutral-Culture-1200.jpg"};

        for (int i = 0; i < categories.length; i++) {
            if(category.equals(categories[i])){
                DEFAULT_IMG = (urls[i]);
            }
        }
    }

    void refreshTextFields() {
        nameInput.getEditText().getText().clear();
        nameInput.setErrorEnabled(false);
        locationInput.getEditText().getText().clear();
        locationInput.setErrorEnabled(false);
        descriptionInput.getEditText().getText().clear();
        descriptionInput.setErrorEnabled(false);
        startDateInput.getEditText().getText().clear();
        startDateInput.setErrorEnabled(false);
        startTimeInput.getEditText().getText().clear();
        startTimeInput.setErrorEnabled(false);
        endDateInput.getEditText().getText().clear();
        endDateInput.setErrorEnabled(false);
        endTimeInput.getEditText().getText().clear();
        endTimeInput.setErrorEnabled(false);
        categoryInput.getEditText().getText().clear();
        categoryInput.setErrorEnabled(false);
        capacityInput.getEditText().getText().clear();
        capacityInput.setErrorEnabled(false);

        Toast.makeText(getContext(), R.string.createEvent_correct, Toast.LENGTH_SHORT).show();
    }


}
