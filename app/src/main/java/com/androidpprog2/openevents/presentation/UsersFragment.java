package com.androidpprog2.openevents.presentation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.APIClient;
import com.androidpprog2.openevents.persistance.OpenEventsAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersFragment extends Fragment {


    private RecyclerView usersRecycleView;
    private UsersAdapter users_adapter;
    private TextView titleUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        usersRecycleView = view.findViewById(R.id.usersRecycleView);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        usersRecycleView.setLayoutManager(llm);

        titleUsers = view.findViewById(R.id.titleUsers);

        getUsers();

        return view;
    }

    public void getUsers() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);


        Call<ArrayList<User>> call = service.getUsers("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM");

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ArrayList<User> users_list = response.body();
                        users_adapter = new UsersAdapter(users_list, getContext());
                        usersRecycleView.setAdapter(users_adapter);


                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        String email_rebut = preferences.getString("email","hola@gmail.com");

                        SharedPreferences.Editor editor = preferences.edit();

                        for (int i = 0; i < users_list.size(); i++){
                            if(email_rebut.equals(users_list.get(0).getEmail())){
                                int id_final = users_list.get(0).getId();
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
