package com.example.mimoh.apidemoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.Fragment.Education;
import com.example.mimoh.apidemoapp.Fragment.Home;
import com.example.mimoh.apidemoapp.Fragment.Personal;
import com.example.mimoh.apidemoapp.Fragment.Professional;
import com.example.mimoh.apidemoapp.OutputData;
import com.example.mimoh.apidemoapp.R;
import com.example.mimoh.apidemoapp.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    Personal personal;
    FragmentTransaction transaction;
    Home home;
    Data data;
    Education education;
    Professional professional;
    int back = 1;
    private static final String BASE_URL = "http://139.59.65.145:9090/";
    boolean condition,condition1;
    RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        data = (Data) getIntent().getSerializableExtra("data");
//        Toast.makeText(this,data.getName(),Toast.LENGTH_SHORT).show();
//        boolean ed = getIntent().getBooleanExtra("ed",false);

        home = new Home();
        education = new Education();
        personal = new Personal();
        professional = new Professional();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
//        education.setArguments(bundle);

//        transaction.add(R.id.Flhome, education);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestInterface = retrofit.create(RequestInterface.class);

        transaction = getSupportFragmentManager().beginTransaction();

        if (data.getName() != null) {
            Toast.makeText(HomePage.this,"No personal data found,Please add.",Toast.LENGTH_SHORT).show();
            personal.setArguments(bundle);
            transaction.add(R.id.Flhome, personal);
        }
//        else if(data.getDegree() == null){
//            education.setArguments(bundle);
//            transaction.add(R.id.Flhome, education);
//        }
//        else if (data.getDesignation() == null){
//            professional.setArguments(bundle);
//            transaction.add(R.id.Flhome, professional);
//        }
//        else {
//            home.setArguments(bundle);
//            transaction.add(R.id.Flhome, home);
//        }

//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (back == 2) {
            startActivity(new Intent(HomePage.this,LoginPage.class));
        }
        else{
            Toast.makeText(HomePage.this,"Press back again to logout",Toast.LENGTH_SHORT).show();
            back++;
        }
    }


}
