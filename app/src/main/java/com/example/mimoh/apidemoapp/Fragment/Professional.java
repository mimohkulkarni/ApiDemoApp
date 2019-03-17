package com.example.mimoh.apidemoapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.InputJson;
import com.example.mimoh.apidemoapp.OutputData;
import com.example.mimoh.apidemoapp.R;
import com.example.mimoh.apidemoapp.RequestInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Professional extends Fragment {

    EditText ETorg,ETdesg;
    CheckBox CBwork;
    Button BTNprosave;
    Data data,datapro;
    Spinner SPstartmonth,SPstartyear,SPendmonth,SPendyear;
    private static final String BASE_URL = "http://139.59.65.145:9090/";
    private boolean condition = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professional, container, false);

        ETorg = view.findViewById(R.id.ETorg);
        ETdesg = view.findViewById(R.id.ETdesg);
        CBwork = view.findViewById(R.id.CBwork);
        BTNprosave = view.findViewById(R.id.BTNprosave);
        SPstartmonth = view.findViewById(R.id.SPstartmonth);
        SPstartyear = view.findViewById(R.id.SPstartyear);
        SPendmonth = view.findViewById(R.id.SPendmonth);
        SPendyear = view.findViewById(R.id.SPendyear);

        data = (Data) getArguments().getSerializable("data");

//        ETorg.setText("sparks foundation");
//        ETdesg.setText("intern");
//        CBwork.setChecked(false);
//        SPstartyear.setSelection(1);
//        SPstartyear.setSelection(3);
//        SPendmonth.setSelection(1);
//        SPendyear.setSelection(4);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final Call<OutputData> call = requestInterface.getprofessional("http://139.59.65.145:9090/user/professionaldetail/" + data.getUid());

        call.enqueue(new Callback<OutputData>() {
            @Override
            public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                OutputData outputData = response.body();
                condition = (outputData != null ? outputData.getData() : null) != null;

            }

            @Override
            public void onFailure(Call<OutputData> call, Throwable t) {
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        BTNprosave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_date = "01-" + getmonthno(SPstartmonth.getSelectedItem().toString()) + "-" + SPstartyear.getSelectedItem().toString();
                String end_date = "01-" + getmonthno(SPendmonth.getSelectedItem().toString()) + "-" + SPendyear.getSelectedItem().toString();

                if (!ETdesg.getText().toString().isEmpty() && ! ETorg.getText().toString().isEmpty()) {
                    InputJson inputJson = new InputJson(end_date, ETorg.getText().toString(), ETdesg.getText().toString(), start_date);

                    if (condition) {
                        Call<OutputData> call1 = requestInterface.saveprofessional("http://139.59.65.145:9090/user/professionaldetail/" + data.getUid(), inputJson);

                        call1.enqueue(new Callback<OutputData>() {
                            @Override
                            public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                OutputData outputData = response.body();
                                datapro = outputData.getData();
//                            Toast.makeText(getContext(),data.getDesignation(),Toast.LENGTH_SHORT).show();
                                if (datapro.getDesignation() != null) {
                                    change();
                                }
                            }

                            @Override
                            public void onFailure(Call<OutputData> call, Throwable t) {
                                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<OutputData> call1 = requestInterface.updateprofessional("http://139.59.65.145:9090/user/educationdetail/" + data.getUid(), inputJson);

                        call1.enqueue(new Callback<OutputData>() {
                            @Override
                            public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                OutputData outputData = response.body();
                                data = outputData.getData();
//                            Toast.makeText(getContext(),data.getDesignation(),Toast.LENGTH_SHORT).show();
                                if (data.getDesignation() != null) {
                                    change();
                                }
                            }

                            @Override
                            public void onFailure(Call<OutputData> call, Throwable t) {
                                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else Toast.makeText(getContext(),"Please provide all values",Toast.LENGTH_SHORT).show();
            }
        });

//        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data",data);
//                Education education = new Education();
//                education.setArguments(bundle);
//                transaction.replace(R.id.Flhome,education);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        return view;
    }

    private void change(){
//        Toast.makeText(getContext(),data.getName(),Toast.LENGTH_SHORT).
        data.setEnd_date(datapro.getEnd_date());
        data.setStart_date(datapro.getStart_date());
        data.setOrganisation1(datapro.getOrganisation());
        data.setDesignation(datapro.getDesignation());

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        Home home = new Home();
        home.setArguments(bundle);
        transaction.replace(R.id.Flhome,home);
        transaction.commit();
    }

    String getmonthno(String monthName) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM", Locale.US);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(inputFormat.parse(monthName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM",Locale.US); // 01-12
        return outputFormat.format(cal.getTime());
    }

}
