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
    private static String accessToken;
    private int id;
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

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdateActivity();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOut();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        id = preferences.getInt("id", getId());


/*
       if (getActivity() instanceof UserProfileActivity){
            ((UserProfileActivity) getActivity()).setProfileInformation(id, profileImage, profileName, profileLastName, profileEmail, avg_score, num_comments, percentage_commenters_below);
        }


 */

        Intent intent = UserProfileActivity.newIntent(getContext());

    //   Intent intent = new Intent(getActivity().getBaseContext(),UserProfileActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("profileName", String.valueOf(profileName));
        intent.putExtra("profileLastName", String.valueOf(profileLastName));
        intent.putExtra("profileEmail", String.valueOf(profileEmail));
        intent.putExtra("avg_score", String.valueOf(avg_score));
        intent.putExtra("num_comments", String.valueOf(num_comments));
        intent.putExtra("percentage_commenters_below", String.valueOf(percentage_commenters_below));

        startActivity(intent);

     //   return inflater.inflate(R.layout.fragment_profile, null);


        return view;
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
        editor.remove(accessToken);
        editor.apply();
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
