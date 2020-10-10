package com.misfit.githubhits.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.misfit.githubhits.MODEL.GET.GITHUBDEVO;
import com.misfit.githubhits.REPOSITORY.DeveloperRepo;

public class DeveloperUiViewModel extends AndroidViewModel {
    private DeveloperRepo developerRepo;
    private LiveData<GITHUBDEVO> githubrepoLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public DeveloperUiViewModel(@NonNull Application application) {
        super(application);
        developerRepo = new DeveloperRepo();
        githubrepoLiveData = developerRepo.getApi_responseLiveData();
        progressbarObservable = developerRepo.getProgress();
    }

    public void searchdevolist(String q) {
        developerRepo.get_hits_devo(q);
    }

    public LiveData<GITHUBDEVO> getResponseLiveData() {
        return githubrepoLiveData;
    }


    public MutableLiveData<Boolean> getProgressbar() {
        return progressbarObservable;
    }
}