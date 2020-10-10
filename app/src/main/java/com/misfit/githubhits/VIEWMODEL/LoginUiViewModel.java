package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.REPOSITORY.LoginRepo;

public class LoginUiViewModel extends AndroidViewModel {
    private LoginRepo loginRepo;
    private LiveData<GET_LOGIN> get_loginLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public LoginUiViewModel(@NonNull Application application) {
        super(application);
        loginRepo = new LoginRepo();
        get_loginLiveData = loginRepo.getApi_responseLiveData();
        progressbarObservable = loginRepo.getProgress();
    }

    public void sign_in_hits(String token) {
        loginRepo.get_sign_in(token);
    }

    public LiveData<GET_LOGIN> get_loginLiveData() {
        return get_loginLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}