package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileEditActivity extends AppCompatActivity {

    private ImageView user_image;
    private TextInputLayout user_name_input;
    private TextInputLayout user_last_name_input;
    private TextInputLayout user_email_input;
    private TextInputLayout user_password_input;
    private User user;
    private MaterialButton applyChanges;
/*
    @NonNull
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UserProfileEditActivity.class);

        return intent;
    }


 */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);
        user_name_input = findViewById(R.id.input_name);
        user_last_name_input = findViewById(R.id.input_last_name);
        user_email_input = findViewById(R.id.input_email);
        user_image = findViewById(R.id.user_image);
        user_password_input = findViewById(R.id.input_password);
        applyChanges = findViewById(R.id.apply_changes_btn);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        getUserInformation(id);
       // validationListeners();

        applyChanges.setOnClickListener(v -> { saveChanges(); });
    }

    private void getUserInformation(int id) {

        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        /*
        SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

         */

        Call<ArrayList<User>> call = service.getUserProfile("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<User> user = response.body();
                        user_name_input.getEditText().setText(user.get(0).getName());
                        user_last_name_input.getEditText().setText(user.get(0).getLast_name());
                        user_email_input.getEditText().setText(user.get(0).getEmail());
                        user_password_input.getEditText().setText(user.get(0).getPassword());
                        setImage(user.get(0).getImage());
                    }
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void saveChanges() {
        if(validateData()){
            String name = user_name_input.getEditText().getText().toString();
            String lastName = user_last_name_input.getEditText().getText().toString();
            String email = user_email_input.getEditText().getText().toString();
            String password = user_password_input.getEditText().getText().toString();
            String image = user_image.toString();

            updateUser(name, lastName, email, password, image);

        }
    }

    private void updateUser(String name, String lastName, String email, String password, String image) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        User user = new User(name, lastName, email, password, image);
        Call<User> call = service.updateUser("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM",user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        //Decidir que fer
                        System.out.println("Actualitzat");
                        Intent intent = EventsActivity.newIntent(getApplicationContext());
                        startActivity(intent);
                       // finish();
                    }
                    else if (response.code() == 400){
                        Toast.makeText(getApplicationContext(), getString(R.string.BodyError), Toast.LENGTH_LONG).show();
                    }else if (response.code() == 409){
                        Toast.makeText(getApplicationContext(), getString(R.string.IncorrectInsert), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validateData(){
        boolean error = true;
        if(validateFirstName(user_name_input.getEditText().getText().toString())) error = false;
        if(validateLastName(user_last_name_input.getEditText().getText().toString())) error = false;
        if(validateEmail(user_email_input.getEditText().getText().toString())) error = false;
        if(validatePassword(user_password_input.getEditText().getText().toString())) error = false;
        return error;
    }

    public void validationListeners(){
        user_name_input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        user_last_name_input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        user_email_input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        user_password_input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        user_name_input.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                validateFirstName(user_name_input.getEditText().getText().toString());
        });

        user_last_name_input.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                validateFirstName(user_last_name_input.getEditText().getText().toString());
        });

        user_email_input.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                validateFirstName(user_email_input.getEditText().getText().toString());
        });

        user_email_input.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                saveChanges();
                return true;
            }
            return false;
        });

        user_password_input.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                validatePassword(user_password_input.getEditText().getText().toString());
        });

    }

    private boolean validateFirstName(String firstName) {
        if (firstName.isEmpty()) {
            user_name_input.setError(getString(R.string.NameError));
            return true;
        }
        user_name_input.setErrorEnabled(false);
        return false;
    }

    private boolean validateLastName(String lastName) {
        if (lastName.isEmpty()) {
            user_last_name_input.setError(getString(R.string.LastNameError));
            return true;
        }
        user_last_name_input.setErrorEnabled(false);
        return false;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            user_email_input.setError(getString(R.string.EmailErrorEmpty));
            return true;
        }
        if (!isEmailValid(email)) {
            user_email_input.setError(getString(R.string.EmailErrorSyntax));
            return true;
        }
        user_email_input.setErrorEnabled(false);
        return false;
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            user_password_input.setError(getString(R.string.PasswordError));
            return true;
        }
        user_password_input.setErrorEnabled(false);
        return false;
    }


    private void setImage(String image) {
        String url = "";
        if(image != null) {
            if (image.startsWith("http")) {
                url = image;
            } else {
                url = "http://puigmal.salle.url.edu/img/" + image;
            }
        }
        RequestOptions options = new RequestOptions()
                .error(R.drawable.img_default);
        Glide.with(getApplicationContext())
                .applyDefaultRequestOptions(options)
                .load(url)
                .into(user_image);
    }
}
