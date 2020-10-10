package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.misfit.githubhits.MODEL.GET.GETBATTLEUSER;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;
import com.misfit.githubhits.REPOSITORY.BattleRepo;
import com.misfit.githubhits.REPOSITORY.ProfileRepo;

import java.util.List;

public class BattelUiViewModel extends AndroidViewModel {
    private BattleRepo battleRepo;
    private LiveData<GETBATTLEUSER> useronelivedata;
    private LiveData<GETBATTLEUSER> usertwolivedata;
    private MutableLiveData<Boolean> progressbarObservable;

    public BattelUiViewModel(@NonNull Application application) {
        super(application);
        battleRepo = new BattleRepo();
        useronelivedata = battleRepo.userone_responseLiveData();
        usertwolivedata = battleRepo.usertwo_responseLiveData();
        progressbarObservable = battleRepo.getProgress();
    }

    public void search_user_one(String user) {
        battleRepo.get_user_one(user);
    }

    public void search_user_two(String user) {
        battleRepo.get_user_two(user);
    }

    public LiveData<GETBATTLEUSER> get_user_one() {
        return useronelivedata;
    }

    public LiveData<GETBATTLEUSER> get_user_two() {
        return usertwolivedata;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}