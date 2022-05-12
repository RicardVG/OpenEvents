package com.androidpprog2.openevents.presentation;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.Calendar;

import javax.security.auth.callback.Callback;

public class CreateEventFragment extends Fragment implements Callback {

    private MaterialButton createBtn;
    private MaterialButton uploadBtn;
    private ProgressBar progressBar;
    private ImageView eventImage;
    private File mImageFile;
    private ScrollView scrollView;

    private TextInputLayout nameInput;
    private TextInputLayout locationInput;
    private TextInputLayout descriptionInput;
    private TextInputLayout startDateInput;
    private TextInputLayout endDateInput;
    private TextInputLayout startTimeInput;
    private TextInputLayout endTimeInput;
    private TextInputLayout capacityInput;
    private TextInputLayout categroyInput;
    private AutoCompleteTextView autoCompleteCategory;

    private String selectedCategory;
    private boolean isStartDateOrEnd;
    private final Calendar myCalendar = Calendar.getInstance();
    private String[] categories;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_event, container, false);
        createBtn = (MaterialButton) view.findViewById(R.id.create_btn);

        nameInput = view.findViewById(R.id.createEvent_name);
        locationInput = view.findViewById(R.id.createEvent_location);
        descriptionInput = view.findViewById(R.id.createEvent_description);
        startDateInput = view.findViewById(R.id.createEvent_startDate);
        endDateInput = view.findViewById(R.id.createEvent_endDate);
        startTimeInput = view.findViewById(R.id.createEvent_startTime);
        endTimeInput = view.findViewById(R.id.createEvent_endTime);
        capacityInput = view.findViewById(R.id.createEvent_capacity);
        categroyInput = view.findViewById(R.id.createEvent_category);
        autoCompleteCategory = view.findViewById(R.id.dropdown_menu_Category);

        progressBar = view.findViewById(R.id.progress_bar);

        startDateInput.getEditText().setInputType(InputType.TYPE_NULL);
        endDateInput.getEditText().setInputType(InputType.TYPE_NULL);

        //validationListeners();

      //  createBtn.setOnClickListener(v -> { create_Event(); });

        return view;
    }
}
