package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileFragment extends Fragment {

    private ImageView editProfileBtn;
    private ImageView profileImage;
    private TextView profileName;
    private TextView profileLastName;
    private TextView profileEmail;
    private TextView avg_score;
    private TextView num_comments;
    private TextView percentage_commenters_below;
    private ImageView log_out;
    private SharedPreferences sh;

    public UserProfileFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = view.findViewById(R.id.imageProfile);
        profileName = view.findViewById(R.id.name_user);
        profileLastName = view.findViewById(R.id.last_name_profile);
        profileEmail = view.findViewById(R.id.email_profile);
        avg_score = view.findViewById(R.id.avg_score);
        num_comments = view.findViewById(R.id.num_comments);
        percentage_commenters_below = view.findViewById(R.id.percentage_comments_below);
        editProfileBtn = view.findViewById(R.id.editProfile);
        log_out = view.findViewById(R.id.log_out);



        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOut();
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences("sh", Context.MODE_PRIVATE);

        String id_final = preferences.getString("id","0");

        SharedPreferences.Editor editor = preferences.edit();

        int id = Integer.parseInt(id_final);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdateActivity(id);
            }
        });


        setProfileInformation(id,profileImage,profileName,profileLastName,profileEmail, avg_score, num_comments,percentage_commenters_below);
        setStadistics(id);
        return view;
    }

    public void setStadistics(int id) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        Call<User> call = service.getStatistics("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        User user = response.body();
                        avg_score.setText(user.getAvg_score());
                        num_comments.setText(String.valueOf(user.getNum_comments()));
                        percentage_commenters_below.setText(user.getPercentage_commenters_below());
                    }
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void setProfileInformation(int id, ImageView image, TextView name, TextView last_name, TextView email, TextView avg_score, TextView num_comments, TextView percentage_commenters_below) {

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
                        name.setText(user.get(0).getName());
                        last_name.setText(user.get(0).getLast_name());
                        email.setText(user.get(0).getEmail());
                        setImage(user.get(0).getImage(), image);
                    }
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
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
        Glide.with(getContext())
                .applyDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }


    private void loginOut() {
        deleteToken();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteToken() {
        sh = requireContext().getSharedPreferences("sh", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.remove("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM");
        editor.apply();
    }

     private void startUpdateActivity(int id) {
       //  Intent intent = UserProfileEditActivity.newIntent(getContext());
         Intent intent = new Intent(getContext(),UserProfileEditActivity.class);
         intent.putExtra("id", id);
         startActivity(intent);

    }



}
