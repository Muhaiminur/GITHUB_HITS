package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.LIBRARY.KeyWord;
import com.misfit.githubhits.MODEL.GET.GITHUBDEVO;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeveloperRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<GITHUBDEVO> responseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();

    public void get_hits_devo(String query) {
        try {
            progressbarObservable.postValue(true);
            Call<GITHUBDEVO> call = apiInterface.GIT_HITS_DEVELOPERS(query, KeyWord.FOLLOWERS, KeyWord.JOINED, KeyWord.DESC);
            call.enqueue(new Callback<GITHUBDEVO>() {
                @Override
                public void onResponse(Call<GITHUBDEVO> call, Response<GITHUBDEVO> response) {
                    try {
                        Log.d("DEVO SEARCH", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            GITHUBDEVO api_response = response.body();
                            Log.d("DEVO", api_response.toString());
                            responseMutableLiveData.postValue(response.body());
                        } else {
                            Log.d("DEVO SEARCH", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GITHUBDEVO> call, Throwable t) {
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

    public LiveData<GITHUBDEVO> getApi_responseLiveData() {
        return responseMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
