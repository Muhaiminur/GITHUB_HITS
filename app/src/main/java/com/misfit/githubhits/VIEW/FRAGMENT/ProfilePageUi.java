package com.misfit.githubhits.VIEW.FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misfit.githubhits.ADAPTER.OWNREPOADAPTER;
import com.misfit.githubhits.ADAPTER.REPOADAPTER;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEWMODEL.LoginUiViewModel;
import com.misfit.githubhits.VIEWMODEL.ProfilePageUiViewModel;
import com.misfit.githubhits.databinding.ProfilePageUiFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageUi extends Fragment {

    ProfilePageUiFragmentBinding pageUiFragmentBinding;
    GET_LOGIN user;
    Context context;
    Utility utility;
    List<GET_OWNREPO> repolists;
    OWNREPOADAPTER repoadapter;
    private ProfilePageUiViewModel profilePageUiViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (pageUiFragmentBinding == null) {
            pageUiFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.profile_page_ui_fragment, container, false);
            try {
                if (context == null) {
                    context = getActivity();
                    utility = new Utility(context);
                }
                initial_repo();
                pageUiFragmentBinding.profileBattle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        //for not back to previous fragment
                        //c.popBackStack();
                        c.navigate(R.id.frag_battel, null);
                    }
                });
                pageUiFragmentBinding.profileLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            utility.clearUSER();
                            utility.clearUSER_ID();
                            Intent i = getContext().getPackageManager()
                                    .getLaunchIntentForPackage(getContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            getActivity().finish();
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }


        }
        return pageUiFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            context = getActivity();
            utility = new Utility(context);
            profilePageUiViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(ProfilePageUiViewModel.class);
            user = utility.getUSER();
            if (user != null) {
                utility.logger("user " + user.toString());
                if (user.getReposUrl() != null && !TextUtils.isEmpty(user.getReposUrl())) {
                    profilePageUiViewModel.check_own_repo(utility.findUserrepo(user.getReposUrl()));
                    pageUiFragmentBinding.profileUsername.setText(user.getLogin());
                } else {
                    utility.showToast(context.getString(R.string.no_data_string));
                }
            }
            observeprogressbar();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void initial_repo() {
        repolists = new ArrayList<>();
        repoadapter = new OWNREPOADAPTER(repolists, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        pageUiFragmentBinding.ownRepoRecycler.setLayoutManager(mLayoutManager);
        pageUiFragmentBinding.ownRepoRecycler.setItemAnimator(new DefaultItemAnimator());
        pageUiFragmentBinding.ownRepoRecycler.setAdapter(repoadapter);
    }

    private void observeprogressbar() {
        profilePageUiViewModel.getProgressbar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean progressObserve) {
                if (progressObserve) {
                    utility.showProgress(false, "LOADING");
                } else {
                    utility.hideProgress();
                }
            }
        });
        profilePageUiViewModel.get_own_repo_list().observe(getActivity(), new Observer<List<GET_OWNREPO>>() {
            @Override
            public void onChanged(List<GET_OWNREPO> githubrepo) {
                try {
                    if (githubrepo != null && githubrepo.size() > 0) {
                        utility.logger("own repo" + githubrepo.size());
                        repolists.clear();
                        repolists.addAll(githubrepo);
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