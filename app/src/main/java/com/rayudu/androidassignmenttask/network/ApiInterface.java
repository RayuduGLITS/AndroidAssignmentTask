package com.rayudu.androidassignmenttask.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("users?page=2")
    Call<JsonObject> getUserRepository();


}
