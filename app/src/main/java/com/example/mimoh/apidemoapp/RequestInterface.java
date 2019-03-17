package com.example.mimoh.apidemoapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface RequestInterface {

    @GET("test")
    Call<Data> test();

    @POST("user/signup")
    Call<OutputData> signup(@Body InputJson inputJson);

    @POST("user/login")
    Call<OutputData> login(@Body InputJson inputJson);

    @GET
    Call<OutputData> getpersonal(@Url String url);

    @POST
    Call<OutputData> savepersonal(@Url String url,@Body InputJson inputJson);

    @PUT
    Call<OutputData> updatepersonal(@Url String url,@Body InputJson inputJson);

    @GET
    Call<OutputData> geteducational(@Url String url);

    @POST
    Call<OutputData> saveeducational(@Url String url,@Body InputJson inputJson);

    @PUT
    Call<OutputData> updateeducational(@Url String url,@Body InputJson inputJson);

    @DELETE
    Call<OutputMessage> deleteeducational(@Url String url);

    @GET
    Call<OutputData> getprofessional(@Url String url);

    @POST
    Call<OutputData> saveprofessional(@Url String url,@Body InputJson inputJson);

    @PUT
    Call<OutputData> updateprofessional(@Url String url,@Body InputJson inputJson);

    @DELETE
    Call<Message> deleteprofessional(@Url String url);

    @POST("user/personaldetail/pp/post")
    Call<OutputMessage> uploaddp(@Body InputJson inputJson);

    @POST("/user/educationdetail/certificate")
    Call<OutputMessage> uploadcer(@Body InputJson inputJson);
}
