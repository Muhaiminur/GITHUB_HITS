package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.REPOSITORY.RepositoryRepo;

public class RepositoryUiViewModel extends AndroidViewModel {
    private RepositoryRepo repositoryRepo;
    private LiveData<GITHUBREPO> githubrepoLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public RepositoryUiViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String query) {
        repositoryRepo = new RepositoryRepo();
        searchbanklist(query);
        githubrepoLiveData = repositoryRepo.getApi_responseLiveData();
        progressbarObservable = repositoryRepo.getProgress();
    }

    public void searchbanklist(String q) {
        repositoryRepo.get_hits_repo(q);
    }

    public LiveData<GITHUBREPO> getResponseLiveData() {
        return githubrepoLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}