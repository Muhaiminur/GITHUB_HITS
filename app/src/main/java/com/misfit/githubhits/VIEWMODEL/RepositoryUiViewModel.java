package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.REPOSITORY.HomepageRepo;

public class RepositoryUiViewModel extends AndroidViewModel {
    private HomepageRepo homepageRepo;
    private LiveData<GITHUBREPO> githubrepoLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public RepositoryUiViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String query) {
        homepageRepo = new HomepageRepo();
        searchbanklist(query);
        githubrepoLiveData = homepageRepo.getApi_responseLiveData();
        progressbarObservable = homepageRepo.getProgress();
    }

    public void searchbanklist(String q) {
        homepageRepo.get_hits_repo(q);
    }

    public LiveData<GITHUBREPO> getResponseLiveData() {
        return githubrepoLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}