package com.misfit.githubhits.REPOSITORY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.HTTP.ApiClient;
import com.misfit.githubhits.HTTP.ApiInterface;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN_ERRO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    private MutableLiveData<GET_LOGIN> responseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<GET_LOGIN_ERRO> loginErroMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<>();

    //login without 2FA
    public void get_sign_in(String token, String otp) {
        try {
            progressbarObservable.postValue(true);
            Call<GET_LOGIN> call = apiInterface.SIGN_IN("application/json", "application/json", token, otp);
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
                        } else if (response.code() == 401) {
                            Log.d("sign in", " two factor");
                            get_sign_in_two(token);
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

    //login with 2FA
    public void get_sign_in_two(String token) {
        try {
            //progressbarObservable.postValue(true);
            Call<GET_LOGIN_ERRO> call = apiInterface.SIGN_IN_TwoFactor("application/json", "application/json", token);
            call.enqueue(new Callback<GET_LOGIN_ERRO>() {
                @Override
                public void onResponse(Call<GET_LOGIN_ERRO> call, Response<GET_LOGIN_ERRO> response) {
                    try {
                        Log.d("sign in two", response.toString());
                        //progressbarObservable.postValue(false);
                        GET_LOGIN_ERRO api_response = response.body();

                        if (response.isSuccessful() && response != null && response.code() == 200) {
                            Log.d("sign in two", api_response.toString());
                            loginErroMutableLiveData.postValue(api_response);
                        } else if (response.code() == 401) {
                            Log.d("sign in two", " two factor");
                            //Log.d("sign in two", response.body().toString());
                            loginErroMutableLiveData.postValue(new GET_LOGIN_ERRO("otp send",""));
                        } else {
                            loginErroMutableLiveData.postValue(api_response);
                            Log.d("sign in two", "FAILed");
                        }
                    } catch (Exception e) {
                        //progressbarObservable.postValue(false);
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<GET_LOGIN_ERRO> call, Throwable t) {
                    //progressbarObservable.postValue(false);
                    Log.d("Error", t.toString());
                    //bankMutableLiveData.postValue(null);
                }
            });
        } catch (Exception e) {
            //progressbarObservable.postValue(false);
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public LiveData<GET_LOGIN> getApi_responseLiveData() {
        return responseMutableLiveData;
    }

    public LiveData<GET_LOGIN_ERRO> get_login_erroLiveData() {
        return loginErroMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progressbarObservable;
    }
}
