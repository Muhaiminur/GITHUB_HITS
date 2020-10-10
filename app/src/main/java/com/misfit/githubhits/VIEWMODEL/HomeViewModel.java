package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.REPOSITORY.HomepageRepo;

public class HomeViewModel extends AndroidViewModel {

    private HomepageRepo homepageRepo;
    private LiveData<GITHUBREPO> githubrepoLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String query) {
        homepageRepo = new HomepageRepo();
        searchrepolist(query);
        githubrepoLiveData = homepageRepo.getApi_responseLiveData();
        progressbarObservable = homepageRepo.getProgress();
    }

    public void searchrepolist(String q) {
        homepageRepo.get_hits_repo(q);
    }

    public LiveData<GITHUBREPO> getResponseLiveData() {
        return githubrepoLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}