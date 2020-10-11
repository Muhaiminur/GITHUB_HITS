package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.MODEL.GET.GETBATTLEUSER;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BattleRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<GETBATTLEUSER> useroneMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<GETBATTLEUSER> usertwoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();


    //find user
    public void get_user_one(String name) {
        try {
            progressbarObservable.postValue(true);
            Call<GETBATTLEUSER> call = apiInterface.SEARCH_USER(name);
            call.enqueue(new Callback<GETBATTLEUSER>() {
                @Override
                public void onResponse(Call<GETBATTLEUSER> call, Response<GETBATTLEUSER> response) {
                    try {
                        Log.d("search user", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            GETBATTLEUSER api_response = response.body();
                            Log.d("search user", api_response.toString());
                            useroneMutableLiveData.postValue(api_response);
                        } else {
                            useroneMutableLiveData.postValue(response.body());
                            Log.d("search user", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GETBATTLEUSER> call, Throwable t) {
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

    //find second user
    public void get_user_two(String name) {
        try {
            progressbarObservable.postValue(true);
            Call<GETBATTLEUSER> call = apiInterface.SEARCH_USER(name);
            call.enqueue(new Callback<GETBATTLEUSER>() {
                @Override
                public void onResponse(Call<GETBATTLEUSER> call, Response<GETBATTLEUSER> response) {
                    try {
                        Log.d("search user", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            GETBATTLEUSER api_response = response.body();
                            Log.d("search user", api_response.toString());
                            usertwoMutableLiveData.postValue(api_response);
                        } else {
                            usertwoMutableLiveData.postValue(response.body());
                            Log.d("search user", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GETBATTLEUSER> call, Throwable t) {
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

    public LiveData<GETBATTLEUSER> userone_responseLiveData() {
        return useroneMutableLiveData;
    }

    public LiveData<GETBATTLEUSER> usertwo_responseLiveData() {
        return usertwoMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
