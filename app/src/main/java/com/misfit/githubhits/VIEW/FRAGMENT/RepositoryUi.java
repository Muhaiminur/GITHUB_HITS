package com.misfit.githubhits.VIEW.FRAGMENT;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misfit.githubhits.ADAPTER.REPOADAPTER;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEWMODEL.HomeViewModel;
import com.misfit.githubhits.VIEWMODEL.RepositoryUiViewModel;
import com.misfit.githubhits.databinding.RepositoryUiFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUi extends Fragment {

    RepositoryUiFragmentBinding repositoryUiFragmentBinding;
    Context context;
    Utility utility;
    List<REPOLIST> repolists;
    REPOADAPTER repoadapter;
    RepositoryUiViewModel repositoryUiViewModel;

    @SuppressLint("ValidFragment")
    public RepositoryUi() {

    }

    private RepositoryUiViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (repositoryUiFragmentBinding == null) {
            repositoryUiFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.repository_ui_fragment, container, false);
            try {
                if (context == null) {
                    context = getActivity();
                    utility = new Utility(context);
                }
                initial_repo();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return repositoryUiFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        utility = new Utility(context);
        repositoryUiViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(RepositoryUiViewModel.class);
        repositoryUiViewModel.init("all");
        observeprogressbar();
    }

    public void initial_repo() {
        repolists = new ArrayList<>();
        repoadapter = new REPOADAPTER(repolists, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        repositoryUiFragmentBinding.repositoryRecycler.setLayoutManager(mLayoutManager);
        repositoryUiFragmentBinding.repositoryRecycler.setItemAnimator(new DefaultItemAnimator());
        repositoryUiFragmentBinding.repositoryRecycler.setAdapter(repoadapter);
    }

    private void observeprogressbar() {
        repositoryUiViewModel.getProgressbar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean progressObserve) {
                if (progressObserve) {
                    utility.showProgress(false, "LOADING");
                } else {
                    utility.hideProgress();
                }
            }
        });
        repositoryUiViewModel.getResponseLiveData().observe(getActivity(), new Observer<GITHUBREPO>() {
            @Override
            public void onChanged(GITHUBREPO githubrepo) {
                try {
                    if (githubrepo.getREPOLISTS() != null && githubrepo.getREPOLISTS().size() > 0) {
                        repolists.clear();
                        repolists.addAll(githubrepo.getREPOLISTS());
                        repoadapter.notifyDataSetChanged();
                    } else {
                        utility.showToast(context.getResources().getString(R.string.no_data_string));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }

}