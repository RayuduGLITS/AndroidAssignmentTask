package com.rayudu.androidassignmenttask.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rayudu.androidassignmenttask.network.ApiClient;
import com.rayudu.androidassignmenttask.network.ApiInterface;
import com.rayudu.androidassignmenttask.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentViewModel extends ViewModel {

    private MutableLiveData<JsonObject> usersData;

    public LiveData<JsonObject> getUsersData() {
        if (usersData == null) {
        usersData = new MutableLiveData<JsonObject>();
        getUserDataApi();
        }
        return usersData;
    }

    private void getUserDataApi() {

        ApiInterface apiInterface = ApiClient.getClient(Constants.SERVER_BASE_URL).create(ApiInterface.class);
        Call<JsonObject> project = apiInterface.getUserRepository();
        project.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response != null && response.isSuccessful()){
                    usersData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TAG_NAME", t.toString());
            }
        });

    }

}
