package com.androidpprog2.openevents.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.androidpprog2.openevents.R;

public class UsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //View view = inflater.inflate(R.layout.users_fragment, container, false);
        return inflater.inflate(R.layout.users_fragment, null);
    }
}
