package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
       // setContentView(R.layout.profile_activity);


        userProfileFragment = new UserProfileFragment();

        userProfileFragment.getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, userProfileFragment , userProfileFragment.getTag())
                .commit();




        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String profileNameString = intent.getStringExtra("profileName");
        String profileLastNameString = intent.getStringExtra("profileLastName");
        String profileEmailString = intent.getStringExtra("profileEmail");
        String avg_scoreString = intent.getStringExtra("avg_score");
        String num_commentsString = intent.getStringExtra("num_comments");
        String percentage_commenters_belowString = intent.getStringExtra("percentage_commenters_below");

        ImageView profileImage = new ImageView(this);


        TextView profileName = new TextView(this);
        profileName.setText(profileNameString);

        TextView profileLastName = new TextView(this);
        profileLastName.setText(profileLastNameString);

        TextView profileEmail = new TextView(this);
        profileEmail.setText(profileEmailString);

        TextView avg_score = new TextView(this);
        avg_score.setText(avg_scoreString);

        TextView num_comments = new TextView(this);
        num_comments.setText(num_commentsString);

        TextView percentage_commenters_below = new TextView(this);
        percentage_commenters_below.setText(percentage_commenters_belowString);




        setProfileInformation(id,profileImage,profileName,profileLastName,profileEmail,avg_score,num_comments,percentage_commenters_below);
    }




    public void setProfileInformation(int id, ImageView image, TextView name, TextView last_name, TextView email, TextView avg_score, TextView num_comments, TextView percentage_commenters_below) {

        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        SharedPreferences sh;
        sh = getSharedPreferences("sh",MODE_PRIVATE);
        String accessToken = sh.getString("accessToken","Bearer");

        Call<ArrayList<User>> call = service.getUserProfile("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<User> user = response.body();
                        name.setText(user.get(0).getName());
                        last_name.setText(user.get(0).getLast_name());
                        email.setText(user.get(0).getEmail());
                        avg_score.setText(user.get(0).getAvg_score());
                        num_comments.setText(user.get(0).getNum_comments());
                        percentage_commenters_below.setText(user.get(0).getPercentage_commenters_below());
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
