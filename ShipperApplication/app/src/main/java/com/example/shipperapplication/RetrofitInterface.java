package com.example.shipperapplication;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/driver/auth/signin")
    Call<Driver> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/driver/auth/signup")
    Call<Driver> executeRegister(@Body HashMap<String, String> map);

    @POST("/api/driver/otp/verify-otp")
    Call<Driver> verifyOtp(@Body HashMap<String, String> map);

    @GET("/api/driver/auth/profile")
    Call<DriverResponse> getDriverProfile(@Header("Authorization") String authToken);

    @POST("/api/driver/auth/profile")
    Call<Driver> setDriverProfile(@Body HashMap<String, String> map);

}
