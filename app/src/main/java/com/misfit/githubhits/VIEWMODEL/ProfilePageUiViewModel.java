package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;
import com.misfit.githubhits.REPOSITORY.ProfileRepo;

import java.util.List;

public class ProfilePageUiViewModel extends AndroidViewModel {
    private ProfileRepo profileRepo;
    private LiveData<List<GET_OWNREPO>> get_loginLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public ProfilePageUiViewModel(@NonNull Application application) {
        super(application);
        profileRepo = new ProfileRepo();
        get_loginLiveData = profileRepo.getApi_responseLiveData();
        progressbarObservable = profileRepo.getProgress();
    }

    public void check_own_repo(String url) {
        profileRepo.get_own_repo(url);
    }

    public LiveData<List<GET_OWNREPO>> get_own_repo_list() {
        return get_loginLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}