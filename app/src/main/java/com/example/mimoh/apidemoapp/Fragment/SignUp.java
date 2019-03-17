package com.example.mimoh.apidemoapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.InputJson;
import com.example.mimoh.apidemoapp.OutputData;
import com.example.mimoh.apidemoapp.R;
import com.example.mimoh.apidemoapp.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends Fragment {

    EditText ETemailid,ETpw1,ETpw2;
    Button BTNsignup;
    private static final String BASE_URL = "http://139.59.65.145:9090/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ETemailid = view.findViewById(R.id.ETemailid);
        ETpw1 = view.findViewById(R.id.ETpw1);
        ETpw2 = view.findViewById(R.id.ETpw2);
        BTNsignup = view.findViewById(R.id.BTNsignup);

//        ETemailid.setText("geeta@example.com");
//        ETpw1.setText("sss");
//        ETpw2.setText("sss");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final Call<OutputData> call = requestInterface.signup(new InputJson(ETemailid.getText().toString(),ETpw1.getText().toString(),1));

        BTNsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ETemailid.getText().toString().isEmpty() && !ETpw1.getText().toString().isEmpty()
                        && !ETpw2.getText().toString().isEmpty()) {

                    if (ETpw1.getText().toString().equals(ETpw2.getText().toString())) {

                        call.enqueue(new Callback<OutputData>() {
                            @Override
                            public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                OutputData outputData = response.body();
                                Data data = outputData.getData();
                                if (!data.getId().isEmpty()) {
                                    Toast.makeText(getContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.FLlogin, new LoginStart());
                                    transaction.commit();
                                } else
                                    Toast.makeText(getContext(), "Unknown error occurred", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<OutputData> call, Throwable t) {
                                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(), "Please provide all values", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
