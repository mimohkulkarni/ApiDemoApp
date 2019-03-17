package com.example.mimoh.apidemoapp.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Activity.HomePage;
import com.example.mimoh.apidemoapp.Activity.LoginPage;
import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.InputJson;
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

public class Personal extends Fragment {

    EditText ETname,ETemail,ETlinks,Etlocation,ETmobile,ETskills;
    Button BTNpersave;
    ImageButton IBdp;
    Data data;
    private static final String BASE_URL = "http://139.59.65.145:9090/";
    Education education;
    boolean condition = false,image = false;
    private static final int IMG_RQST = 777;
    Bitmap bitmap;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ETname = view.findViewById(R.id.ETpname);
        ETemail = view.findViewById(R.id.ETpemail);
        ETlinks = view.findViewById(R.id.ETplinks);
        ETmobile = view.findViewById(R.id.ETpmobile);
        ETskills = view.findViewById(R.id.ETpskills);
        Etlocation = view.findViewById(R.id.ETplocation);
        BTNpersave = view.findViewById(R.id.BTNpersave);
        IBdp = view.findViewById(R.id.IBdp);

        data = (Data) getArguments().getSerializable("data");
        education = new Education();

//        ETlinks.setText("example.com");
//        ETemail.setText("mimoh@example.com");
//        ETmobile.setText("12345");
//        ETskills.setText("php");
//        ETname.setText("mimoh");
//        Etlocation.setText("pune");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Call<OutputData> call1 = requestInterface.getpersonal("http://139.59.65.145:9090/user/personaldetail/" + data.getUid());
        call1.enqueue(new Callback<OutputData>() {
            @Override
            public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                OutputData outputData = response.body();
                condition = (outputData != null ? outputData.getData() : null) == null;
            }

            @Override
            public void onFailure(Call<OutputData> call, Throwable t) {
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        IBdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        BTNpersave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ETmobile.getText().toString().isEmpty() && !ETname.getText().toString().isEmpty() && !ETlinks.getText().toString().isEmpty() &&
                        !Etlocation.getText().toString().isEmpty() && !ETskills.getText().toString().isEmpty()) {

                    InputJson inputJson = new InputJson(ETmobile.getText().toString(), ETname.getText().toString(), ETlinks.getText().toString(),
                            Etlocation.getText().toString(), data.getId(), ETskills.getText().toString());

                    if (image) {

                        if (!condition) {

                            final Call<OutputData> call = requestInterface.savepersonal("http://139.59.65.145:9090/user/personaldetail/" + data.getUid(), inputJson);

                            call.enqueue(new Callback<OutputData>() {
                                @Override
                                public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                    OutputData outputData = response.body();
                                    data = outputData.getData();
                                    if (data.getName() == null)
                                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                    else {
    //                            Toast.makeText(getContext(),data.getName(),Toast.LENGTH_SHORT).show();
                                        change();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OutputData> call, Throwable t) {
                                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            final Call<OutputData> call = requestInterface.updatepersonal("http://139.59.65.145:9090/user/personaldetail/" + data.getUid(), inputJson);

                            call.enqueue(new Callback<OutputData>() {
                                @Override
                                public void onResponse(Call<OutputData> call, Response<OutputData> response) {
                                    OutputData outputData = response.body();
                                    data = outputData.getData();
                                    if (data.getName() == null)
                                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                    else {
    //                                Toast.makeText(getContext(),"Data saved",Toast.LENGTH_SHORT).show();
                                        change();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OutputData> call, Throwable t) {
                                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        final String imagebase64 = imagetoString();
                        InputJson inputJson1 = new InputJson(imagebase64, data.getUid());
                        Call<OutputMessage> call2 = requestInterface.uploaddp(inputJson1);
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
                    else Toast.makeText(getContext(),"Please provide profile photo",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(),"Please provide all values and photo",Toast.LENGTH_SHORT).show();
            }
        });

//        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
////                startActivity(new Intent(getContext(), LoginPage.class));
//            }
//        });

        return view;
    }

    private void change(){
        Toast.makeText(getContext(),"No education data found,Please add.",Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("data", data);
        education.setArguments(bundle1);
        transaction.replace(R.id.Flhome, education).commit();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_RQST);
    }

    private String imagetoString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_RQST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            image = true;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                IBdp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}