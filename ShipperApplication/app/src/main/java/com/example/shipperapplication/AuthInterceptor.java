//package com.example.shipperapplication;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class AuthInterceptor implements Interceptor {
//    private final String token;
//
//    public AuthInterceptor(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        Request newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer " + token)
//                .build();
//        return chain.proceed(newRequest);
//    }
//}