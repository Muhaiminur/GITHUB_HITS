package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN_ERRO;
import com.misfit.githubhits.REPOSITORY.LoginRepo;

public class LoginUiViewModel extends AndroidViewModel {
    private LoginRepo loginRepo;
    private LiveData<GET_LOGIN> get_loginLiveData;
    private LiveData<GET_LOGIN_ERRO> login_erroLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public LoginUiViewModel(@NonNull Application application) {
        super(application);
        loginRepo = new LoginRepo();
        get_loginLiveData = loginRepo.getApi_responseLiveData();
        login_erroLiveData = loginRepo.get_login_erroLiveData();
        progressbarObservable = loginRepo.getProgress();
    }

    public void sign_in_hits(String token, String otp) {
        loginRepo.get_sign_in(token, otp);
    }

    public LiveData<GET_LOGIN> get_loginLiveData() {
        return get_loginLiveData;
    }

    public LiveData<GET_LOGIN_ERRO> get_login_erroLiveData() {
        return login_erroLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}