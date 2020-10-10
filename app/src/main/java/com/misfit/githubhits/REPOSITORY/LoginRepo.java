package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.LIBRARY.KeyWord;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<GET_LOGIN> responseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();

    public void get_sign_in(String token) {
        try {
            progressbarObservable.postValue(true);
            Call<GET_LOGIN> call = apiInterface.SIGN_IN("application/json", "application/json", token);
            call.enqueue(new Callback<GET_LOGIN>() {
                @Override
                public void onResponse(Call<GET_LOGIN> call, Response<GET_LOGIN> response) {
                    try {
                        Log.d("sign in", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            GET_LOGIN api_response = response.body();
                            Log.d("sign in", api_response.toString());
                            responseMutableLiveData.postValue(response.body());
                        } else {
                            responseMutableLiveData.postValue(response.body());
                            Log.d("sign in", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GET_LOGIN> call, Throwable t) {
                    progressbarObservable.postValue(false);
                    Log.d("Error", t.toString());
                    //bankMutableLiveData.postValue(null);
                }
            });
        } catch (Exception e) {
            progressbarObservable.postValue(false);
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


    }

    public LiveData<GET_LOGIN> getApi_responseLiveData() {
        return responseMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
