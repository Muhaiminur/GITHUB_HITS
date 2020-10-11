package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.LIBRARY.KeyWord;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<GITHUBREPO> responseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();
    //Utility utility = new Utility();


    //get hits repo
    public void get_hits_repo(String query) {
        try {
            progressbarObservable.postValue(true);
            Call<GITHUBREPO> call = apiInterface.GIT_HITS_REPO(query, KeyWord.HITS_STAR, KeyWord.DESC, KeyWord.PER_PAGE);
            call.enqueue(new Callback<GITHUBREPO>() {
                @Override
                public void onResponse(Call<GITHUBREPO> call, Response<GITHUBREPO> response) {
                    try {
                        Log.d("REPO SEARCH", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            GITHUBREPO api_response = response.body();
                            Log.d("abir", api_response.toString());
                            responseMutableLiveData.postValue(response.body());
                        } else {
                            Log.d("REPO SEARCH", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GITHUBREPO> call, Throwable t) {
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

    public LiveData<GITHUBREPO> getApi_responseLiveData() {
        return responseMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
