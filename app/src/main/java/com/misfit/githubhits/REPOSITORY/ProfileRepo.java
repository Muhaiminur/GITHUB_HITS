package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<List<GET_OWNREPO>> responseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();

    //GET USER OWN REPO
    public void get_own_repo(String url) {
        try {
            progressbarObservable.postValue(true);
            Call<List<GET_OWNREPO>> call = apiInterface.OWN_REPO(url);
            call.enqueue(new Callback<List<GET_OWNREPO>>() {
                @Override
                public void onResponse(Call<List<GET_OWNREPO>> call, Response<List<GET_OWNREPO>> response) {
                    try {
                        Log.d("own repo", response.toString());
                        progressbarObservable.postValue(false);
                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            List<GET_OWNREPO> api_response = response.body();
                            Log.d("own repo", api_response.toString());
                            responseMutableLiveData.postValue(api_response);
                        } else {
                            responseMutableLiveData.postValue(response.body());
                            Log.d("own repo", "FAILed");
                        }
                    } catch (Exception e) {
                        progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<List<GET_OWNREPO>> call, Throwable t) {
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

    public LiveData<List<GET_OWNREPO>> getApi_responseLiveData() {
        return responseMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
