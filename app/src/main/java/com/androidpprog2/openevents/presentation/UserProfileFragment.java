package com.androidpprog2.openevents.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileFragment extends Fragment {

    private ImageButton editProfileBtn;
    private ImageView profileImage;
    private TextView profileName;
    private TextView profileLastName;
    private TextView profileEmail;
    private TextView avg_score;
    private TextView num_comments;
    private TextView percentage_commenters_below;
    private static String accessToken;
    private int id;

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

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdateActivity();
            }
        });



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        id = preferences.getInt("id", getId());


        if (getActivity() instanceof UserProfileActivity){
            ((UserProfileActivity) getActivity()).setProfileInformation(id, profileImage, profileName, profileLastName, profileEmail, avg_score, num_comments, percentage_commenters_below);
        }
        return inflater.inflate(R.layout.fragment_profile, null);
    }

/*
    @RequiresApi(api = Build.VERSION_CODES.O) public static int getUserId() {
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject;
        int id = 0;
        try {
            jsonObject = new JSONObject(payload);
            id = (int) jsonObject.get("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }


 */

     private void startUpdateActivity() {
         Intent intent = UserProfileEditActivity.newIntent(getContext());
         intent.putExtra("id_user",id);
         startActivity(intent);

    }



}
