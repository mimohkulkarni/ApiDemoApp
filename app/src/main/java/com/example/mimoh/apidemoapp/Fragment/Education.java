package com.example.mimoh.apidemoapp.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.InputJson;
import com.example.mimoh.apidemoapp.Message;
import com.example.mimoh.apidemoapp.OutputData;
import com.example.mimoh.apidemoapp.OutputMessage;
import com.example.mimoh.apidemoapp.R;
import com.example.mimoh.apidemoapp.RequestInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Education extends Fragment {

    EditText ETuni,ETcity,ETstream;
    Spinner SPedstart,SPedend;
    Button BTNedsave;
    ImageButton IMcertificate;
    private static final String BASE_URL = "http://139.59.65.145:9090/";
    private static final int IMG_RQST = 777;
    Data data,dataedu;
    private Bitmap bitmap;
    boolean condition = false,image = false,edit = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        data = (Data) getArguments().getSerializable("data");
        edit = getArguments().getBoolean("edit",false);

//        Toast.makeText(getContext(),data.getName(),Toast.LENGTH_SHORT).show();

        ETcity = view.findViewById(R.id.ETcity);
        ETstream = view.findViewById(R.id.ETstream);
        ETuni = view.findViewById(R.id.ETuni);
        SPedstart = view.findViewById(R.id.SPedstart);
        SPedend = view.findViewById(R.id.SPedend);
        BTNedsave = view.findViewById(R.id.BTNedusave);
        IMcertificate = view.findViewById(R.id.IMcertificate);

//        ETuni.setText("pune");
//        ETstream.setText("computer");
//        ETcity.setText("pune");
//        SPedstart.setSelection(1);
//        SPedend.setSelection(3);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        if (edit){
            ETuni.setText(data.getOrganisation());
            ETstream.setText(data.getDegree());
            ETcity.setText(data.getLocation());
            image = true;
//            Toast.makeText(getContext(),data.getUid(),Toast.LENGTH_SHORT).show();

            Call<OutputMessage> calldelete = requestInterface.deleteeducational("http://139.59.65.145:9090/user/educationdetail/" + data.getUid());
            calldelete.enqueue(new Callback<OutputMessage>() {
                @Override
                public void onResponse(Call<OutputMessage> call, Response<OutputMessage> response) {
                    OutputMessage outputMessage = response.body();
//                    Message message = outputMessage.getMessage();
//                    Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<OutputMessage> call, Throwable t) {
                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                }
            });
        }

        IMcertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit) Toast.makeText(getContext(),"You already uploaded certificate",Toast.LENGTH_SHORT).show();
                else selectImage();
            }
        });

        Call<OutputData> call1 = requestInterface.geteducational("http://139.59.65.145:9090/user/educationdetail/" + data.getUid());
        call1.enqueue(new Callback<OutputData>() {
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

        BTNedsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ETcity.getText().toString().isEmpty() &&!ETstream.getText().toString().isEmpty()
                        && !ETuni.getText().toString().isEmpty()) {

                    if (image) {

                        InputJson inputJson = new InputJson(ETcity.getText().toString(), SPedstart.getSelectedItem().toString(),
                                SPedend.getSelectedItem().toString(), ETstream.getText().toString(), ETuni.getText().toString());

                        if (condition) {

                            Call<OutputData> call = requestInterface.saveeducational("http://139.59.65.145:9090/user/educationdetail/" + data.getUid(), inputJson);

                            call.enqueue(new Callback<OutputData>() {
                                @Override
                                public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                    OutputData outputData = response.body();
                                    dataedu = outputData.getData();

                                    change();
                                }

                                @Override
                                public void onFailure(Call<OutputData> call, Throwable t) {
                                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Call<OutputData> call = requestInterface.updateeducational("http://139.59.65.145:9090/user/educationdetail/" + data.getUid(), inputJson);

                            call.enqueue(new Callback<OutputData>() {
                                @Override
                                public void onResponse(Call<OutputData> call, Response<OutputData> response) {
//                        String image = imagetoString();
                                    OutputData outputData = response.body();
                                    dataedu = outputData.getData();

                                    change();
                                }

                                @Override
                                public void onFailure(Call<OutputData> call, Throwable t) {
                                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        if (!edit) {
                            final String imagebase64 = imagetoString();
                            InputJson inputJson1 = new InputJson(imagebase64, data.getUid());
                            Call<OutputMessage> call2 = requestInterface.uploadcer(inputJson1);
                            call2.enqueue(new Callback<OutputMessage>() {
                                @Override
                                public void onResponse(Call<OutputMessage> call, Response<OutputMessage> response) {
                                    OutputMessage outputMessage = response.body();
                                    if ((outputMessage != null ? outputMessage.getMessage() : null) != null)
                                        Toast.makeText(getContext(), outputMessage.getMessage().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<OutputMessage> call, Throwable t) {
                                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else Toast.makeText(getContext(),"Please provide certificate image",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(),"Please provide all values",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void change(){
//        Toast.makeText(getContext(), data.getName(), Toast.LENGTH_SHORT).show();
        if (dataedu.getStart_year() != null){

            data.setImage_path(dataedu.getImage_path());
            data.setStart_year(dataedu.getStart_year());
            data.setEnd_year(dataedu.getEnd_year());
            data.setDegree(dataedu.getDegree());
            data.setOrganisation(dataedu.getOrganisation());
            data.setLocation(dataedu.getLocation());

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",data);
            if (edit){
                Home home = new Home();
                home.setArguments(bundle);
                transaction.replace(R.id.Flhome,home);
            }
            else {
                Toast.makeText(getContext(),"No professional data found,Please add.",Toast.LENGTH_SHORT).show();
                Professional professional = new Professional();
                professional.setArguments(bundle);
                transaction.replace(R.id.Flhome, professional);
            }
            transaction.commit();
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_RQST);
    }

    private String imagetoString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_RQST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            image = true;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
//                IMcertificate.setImageBitmap(bitmap);
            } catch (IOException
                     e) {
                e.printStackTrace();
            }
        }
    }
}
