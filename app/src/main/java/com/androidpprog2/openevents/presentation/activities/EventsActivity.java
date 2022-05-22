package com.androidpprog2.openevents.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.presentation.fragments.CreateEventFragment;
import com.androidpprog2.openevents.presentation.fragments.HomeFragment;
import com.androidpprog2.openevents.presentation.fragments.UserProfileFragment;
import com.androidpprog2.openevents.presentation.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EventsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public EventsActivity() {
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EventsActivity.class);
        return intent;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                break;

            case R.id.createEvent:
                fragment = new CreateEventFragment();
                break;

            case R.id.profile:
                fragment = new UserProfileFragment();
                break;

            case R.id.users:
                fragment = new UsersFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
