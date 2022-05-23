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

//Aquesta activitat conté la lògica dels fragments i com ens movem entre ells.
public class EventsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public EventsActivity() {
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EventsActivity.class);
        return intent;
    }

    //Aquesta funció rep un fragment i retorna un booleà. Si es diferent a null, reemplaça, el fragment
    //que rep per paràmetre a un fragment_container. Podriem dir que aquest fragment_container
    //seria l'espai on es reemplaça el fragment corresponent. Si és així retornarem true mentre que si
    //no el reemplaça retornarem false.
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


    //Inicialment, cargem un fragment per defecte, en aquest cas Home Fragment. Això ho fem a través
    //de la funció loadFragment() la qual li passem un fragment. A través BottomNavigationView,
    //podem desplaçarnos a través dels diferents items amb la funció .setOnItemSelectedListener.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setOnItemSelectedListener(this);
    }


    //Aquesta funció ens permet navegar entre els diferents fragments. Això ho fem, a través
    //de un menu inferior el qual quan polsem una icona, ens mou cap al fragment determinat.
    //Ho controlem mitjançant un switch agafant la id del item determinat. Ens retornarà un fragment
    //en qualsevol cas.
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
