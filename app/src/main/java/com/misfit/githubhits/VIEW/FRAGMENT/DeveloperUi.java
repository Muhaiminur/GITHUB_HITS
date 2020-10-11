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

import com.misfit.githubhits.ADAPTER.DEVELOPERADAPTER;
import com.misfit.githubhits.ADAPTER.REPOADAPTER;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GITHUBDEVO;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.MODEL.GET.GITREPO.DEVOLIST;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEWMODEL.DeveloperUiViewModel;
import com.misfit.githubhits.VIEWMODEL.HomeViewModel;
import com.misfit.githubhits.VIEWMODEL.RepositoryUiViewModel;
import com.misfit.githubhits.databinding.DeveloperUiFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class DeveloperUi extends Fragment {
    DeveloperUiFragmentBinding uiFragmentBinding;
    Context context;
    Utility utility;
    List<DEVOLIST> devolists;
    DEVELOPERADAPTER developeradapter;
    private DeveloperUiViewModel developerUiViewModel;

    private boolean _hasLoadedOnce = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (uiFragmentBinding == null) {
            uiFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.developer_ui_fragment, container, false);
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
        return uiFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        utility = new Utility(context);
        developerUiViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(DeveloperUiViewModel.class);
        observeprogressbar();
    }

    //intial ui
    public void initial_repo() {
        devolists = new ArrayList<>();
        developeradapter = new DEVELOPERADAPTER(devolists, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        uiFragmentBinding.developerRecycler.setLayoutManager(mLayoutManager);
        uiFragmentBinding.developerRecycler.setItemAnimator(new DefaultItemAnimator());
        uiFragmentBinding.developerRecycler.setAdapter(developeradapter);
    }

    private void observeprogressbar() {
        developerUiViewModel.getProgressbar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean progressObserve) {
                if (progressObserve) {
                    utility.showProgress(false, "LOADING");
                } else {
                    utility.hideProgress();
                }
            }
        });
        developerUiViewModel.getResponseLiveData().observe(getActivity(), new Observer<GITHUBDEVO>() {
            @Override
            public void onChanged(GITHUBDEVO githubrepo) {
                try {
                    if (githubrepo.getREPOLISTS() != null && githubrepo.getREPOLISTS().size() > 0) {
                        devolists.clear();
                        devolists.addAll(githubrepo.getREPOLISTS());
                        developeradapter.notifyDataSetChanged();
                    } else {
                        utility.showToast(context.getResources().getString(R.string.no_data_string));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                developerUiViewModel.searchdevolist("all");
                _hasLoadedOnce = true;
            }
        }
    }

}