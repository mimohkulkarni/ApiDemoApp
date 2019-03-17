package com.example.mimoh.apidemoapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mimoh.apidemoapp.R;

public class LoginStart extends Fragment {

    Button BTNlogin,BTNsignup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginstart, container, false);

        BTNlogin = view.findViewById(R.id.BTNlogin1);
        BTNsignup = view.findViewById(R.id.BTNsignup1);

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FLlogin,new Login());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        BTNsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FLlogin,new SignUp());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
