package com.example.mimoh.apidemoapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Activity.HomePage;
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

public class Login extends Fragment {

//    CallbackManager callbackManager;
//    LoginButton loginButton;
    EditText ETemail,ETpw;
    Button BTNlogin;
    private static final String BASE_URL = "http://139.59.65.145:9090/";
    OutputData outputData;
    RequestInterface requestInterface;
    Data data,dataper;
    boolean condition,condition1,condition3,condition4;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ETemail = view.findViewById(R.id.ETemail);
        ETpw = view.findViewById(R.id.ETpw);
        BTNlogin = view.findViewById(R.id.BTNlogin);

//        loginButton = view.findViewById(R.id.login_button);
//        facebook();
//        ETemail.setText("geeta@abc.com");
//        ETpw.setText("sss");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestInterface = retrofit.create(RequestInterface.class);

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ETemail.getText().toString().isEmpty() && !ETpw.getText().toString().isEmpty()) {
                    final Call<OutputData> call = requestInterface.login(new InputJson(ETemail.getText().toString(), ETpw.getText().toString(), 1));

                    call.enqueue(new Callback<OutputData>() {
                        @Override
                        public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                            outputData = response.body();
                            condition4 = (outputData != null ? outputData.getData() : null) != null;
                            if (condition4){
                                data = outputData.getData();
                                if (!data.getId().isEmpty()) {
                                    final Intent intent = new Intent(getContext(), HomePage.class);

                                    final Call<OutputData> call1 = requestInterface.getpersonal("http://139.59.65.145:9090/user/personaldetail/" + data.getId());
                                    call1.enqueue(new Callback<OutputData>() {
                                        @Override
                                        public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                            outputData = response.body();
                                            condition3 = (outputData != null ? outputData.getData() : null) != null;
//                                            if (outputData.getData() == null) condition3 = false;
//                                            else condition3 = true;
//                                    Toast.makeText(getContext(),"Login successful",Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(),data1.getName(),Toast.LENGTH_SHORT).show();

                                            if (condition3){
                                                data.setMobile_no(outputData.getData().getMobile_no());
                                                data.setSkills(outputData.getData().getSkills());
                                                data.setUid(outputData.getData().getUid());
                                                data.setName(outputData.getData().getName());
                                                data.setLinks(outputData.getData().getLinks());
                                                data.setLocation(outputData.getData().getLocation());
                                                data.setEmail(outputData.getData().getEmail());
                                            }


                                            Call<OutputData> calledu = requestInterface.geteducational("http://139.59.65.145:9090/user/educationdetail/" + data.getUid());
                                            calledu.enqueue(new Callback<OutputData>() {
                                                @Override
                                                public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                                    OutputData outputData = response.body();
                                                    condition = (outputData != null ? outputData.getData() : null) != null;
                                                    if (condition){
                                                        data.setImage_path(outputData.getData().getImage_path());
                                                        data.setStart_year(outputData.getData().getStart_year());
                                                        data.setEnd_year(outputData.getData().getEnd_year());
                                                        data.setDegree(outputData.getData().getDegree());
                                                        data.setOrganisation(outputData.getData().getOrganisation());
                                                        data.setLocation(outputData.getData().getLocation());
                                                    }


                                                    Call<OutputData> callpro = requestInterface.getprofessional("http://139.59.65.145:9090/user/professionaldetail/" + data.getUid());
                                                    callpro.enqueue(new Callback<OutputData>() {
                                                        @Override
                                                        public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                                            OutputData outputData = response.body();
                                                            condition1 = (outputData != null ? outputData.getData() : null) != null;
                                                            if (condition1){
                                                                data.setEnd_date(outputData.getData().getEnd_date());
                                                                data.setStart_date(outputData.getData().getStart_date());
                                                                data.setOrganisation(outputData.getData().getOrganisation());
                                                                data.setDesignation(outputData.getData().getDesignation());
                                                            }

                                                            intent.putExtra("data", data);
                                                            startActivity(intent);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<OutputData> call, Throwable t) {
                                                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }


                                                @Override
                                                public void onFailure(Call<OutputData> call, Throwable t) {
                                                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }


                                        @Override
                                        public void onFailure(Call<OutputData> call, Throwable t) {
                                            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else Toast.makeText(getContext(), "Incorrect combination", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getContext(),"Error1",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<OutputData> call, Throwable t) {
                            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else Toast.makeText(getContext(),"Please provide all values",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    void edudata(){

    }

    void prodata(){

    }

}
