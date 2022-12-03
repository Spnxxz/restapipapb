package com.example.laprak_retrofit.api;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public static ApiService getApiService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8000/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit.create(ApiService.class);
    }
}
