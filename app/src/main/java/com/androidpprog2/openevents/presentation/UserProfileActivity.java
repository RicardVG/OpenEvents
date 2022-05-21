package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivity extends AppCompatActivity {
    private UserProfileFragment userProfileFragment;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UserProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        userProfileFragment = new UserProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, userProfileFragment, userProfileFragment.getTag())
                .commit();


    }


    public void setProfileInformation(int id, ImageView image, TextView name, TextView last_name, TextView email, TextView avg_score, TextView num_comments, TextView percentage_commenters_below) {

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
                        ArrayList<User> user = response.body();
                        name.setText((CharSequence) user.get(0));
                        last_name.setText((CharSequence) user.get(0));
                        email.setText((CharSequence) user.get(0));
                        avg_score.setText((CharSequence) user.get(0));
                        num_comments.setText((CharSequence) user.get(0));
                        percentage_commenters_below.setText((CharSequence) user.get(0));
                        setImage(user.get(0).getImage(), image);
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
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
}

    private void setImage(String image, ImageView imageView) {
        String url = "";
        if (image != null) {
            if (image.startsWith("http")) {
                url = image;
            } else {
                url = "http://puigmal.salle.url.edu/img/" + image;
            }
        }

        RequestOptions options = new RequestOptions()
                .error(R.drawable.img_default);
        Glide.with(userProfileFragment.requireActivity())
                .applyDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }
    }
