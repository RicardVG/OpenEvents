package com.androidpprog2.openevents.presentation.activities;

import android.content.Context;
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
import java.util.ArrayList;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoUserActivity extends AppCompatActivity {
    private TextView userName;
    private TextView userLastName;
    private TextView user_id;
    private TextView user_email;
    private ImageView user_image;
    private User user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_user);

        userName = findViewById(R.id.userinfoName);
        userLastName = findViewById(R.id.userinfoLastName);
        user_id = findViewById(R.id.userinfoId);
        user_email = findViewById(R.id.userinfoEmail);
        user_image = findViewById(R.id.userinfoImage);

        getAllInfoUser();
    }

    private void getAllInfoUser() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        OpenEventsAPI service = retrofit.create(OpenEventsAPI.class);

        int id = getIntent().getExtras().getInt("id");

        Call<ArrayList<User>> call = service.getUserProfile("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIxMywibmFtZSI6InJpY2FyZCIsImxhc3RfbmFtZSI6InZpw7FvbGFzIiwiZW1haWwiOiJyaWNhcmQxMjM0QGdtYWlsLmNvbSIsImltYWdlIjoicmZpcm5laWZuaSJ9.KstEBEE5wMDMxxiAIKX0jUm718W8DOtotK-KkdyRBoM", id);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200) {
                        ArrayList<User> users = response.body();
                        if (users.get(0)!= null) {
                            user = users.get(0);
                            printUser(users.get(0));
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void printUser(User user) {
        userName.setText(user.getName());
        userLastName.setText(user.getLast_name());
        user_id.setText(String.valueOf(user.getId()));
        user_email.setText(user.getEmail());

        String url = "";

        if (this.user.getImage() != null) {
            if (this.user.getImage().startsWith("https")) {
                url = this.user.getImage();
            } else {
                url = "https://172.16.205.68/img/" + this.user.getImage();
            }
        }
        Glide.with(getApplicationContext())
                .load(url)
                .apply(RequestOptions
                        .bitmapTransform(new BlurTransformation(8, 1))
                        .placeholder(R.drawable.icon_profile_user)
                        .error(R.drawable.icon_profile_user))
                .into(user_image);
    }
}
