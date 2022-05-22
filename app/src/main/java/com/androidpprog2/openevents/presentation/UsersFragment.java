package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersFragment extends Fragment {


    private RecyclerView usersRecycleView;
    private UsersAdapter users_adapter;
    private ArrayList<User> users_list;
    private final String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        usersRecycleView = view.findViewById(R.id.usersRecycleView);
        TextInputEditText textSearch = view.findViewById(R.id.inputUserSearch);
        ImageView searchUsersImageView = view.findViewById(R.id.searchUserButton);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        usersRecycleView.setLayoutManager(llm);

        TextView titleUsers = view.findViewById(R.id.titleUsers);

        getUsers();


        searchUsersImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = textSearch.getText().toString();
                searchUsers(test);
            }
        });

        return view;
    }

    public void searchUsers(String s) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);
        Call<ArrayList<User>> call = service.searchUsers(accessToken, s);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        users_list = response.body();
                        users_adapter = new UsersAdapter(users_list, getContext());
                        usersRecycleView.setAdapter(users_adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect data. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getUsers() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);


        Call<ArrayList<User>> call = service.getUsers(accessToken);

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        users_list = response.body();
                        users_adapter = new UsersAdapter(users_list, getContext());
                        usersRecycleView.setAdapter(users_adapter);

                        SharedPreferences preferences = getActivity().getSharedPreferences("sh", Context.MODE_PRIVATE);

                        String email_rebut = preferences.getString("email",null);

                        SharedPreferences.Editor editor = preferences.edit();

                        for (int i = 0; i < users_list.size(); i++){
                            if(email_rebut.equals(users_list.get(i).getEmail())){
                                int id_final = users_list.get(i).getId();
                                editor.putString("id", String.valueOf(id_final));
                                editor.apply();
                            }
                        }



                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect data. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
