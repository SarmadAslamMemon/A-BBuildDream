package com.example.abbuilddream.api;


import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static  String baseUrl ="https://api.aandbco.com/";
    // Method to get the Retrofit instance
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) // Set your base URL here
                    .addConverterFactory(GsonConverterFactory.create(new Gson())) // Use GsonConverterFactory with the Gson instance
                    .build();
        }
        return retrofit;
    }
}

