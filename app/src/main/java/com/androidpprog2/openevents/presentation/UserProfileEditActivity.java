package com.androidpprog2.openevents.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id_user", 0);

        user_name_input = findViewById(R.id.input_name);
        user_last_name_input = findViewById(R.id.input_last_name);
        user_email_input = findViewById(R.id.input_email);
        user_image = findViewById(R.id.user_image);
        user_password_input = findViewById(R.id.input_password);

        getUserInformation(id);
       // validationListeners();

      //  apply_changes_btn.setOnClickListener(v -> { saveChanges(); });
    }

    private void getUserInformation(int id) {

        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

        Call<ArrayList<User>> call = service.getUserProfile(accessToken, id);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<User> users = response.body();
                        user = users.get(0);
                        user_name_input.getEditText().setText(user.getName());
                        user_last_name_input.getEditText().setText(user.getLast_name());
                        user_email_input.getEditText().setText(user.getEmail());
                        user_password_input.getEditText().setText(user.getPassword());
                        setImage(user.getImage());
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

  /*  public void saveChanges() {
        if(validateData()){
            String name = nameInput.getEditText().getText().toString();
            String lastName = lastNameInput.getEditText().getText().toString();
            String email = emailInput.getEditText().getText().toString();

      //      loading(true);
            if(mImageFile == null) {
                CallSingelton
                        .getInstance()
                        .updateUser(null, name, lastName, email, this);
            }else {
                CallSingelton
                        .getInstance()
                        .updateUser(mImageFile, name, lastName, email, this);
            }
        }
    }


   */



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
