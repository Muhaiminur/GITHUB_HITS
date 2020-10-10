package com.misfit.githubhits.VIEW.FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEWMODEL.LoginUiViewModel;
import com.misfit.githubhits.VIEWMODEL.RepositoryUiViewModel;
import com.misfit.githubhits.databinding.LoginUiFragmentBinding;

public class LoginUi extends Fragment {

    LoginUiFragmentBinding loginUiFragmentBinding;
    Context context;
    Utility utility;
    private LoginUiViewModel loginUiViewModel;
    String token = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (loginUiFragmentBinding == null) {
            loginUiFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_ui_fragment, container, false);
            try {
                if (context == null) {
                    context = getActivity();
                    utility = new Utility(context);
                }
                loginUiFragmentBinding.signHit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(loginUiFragmentBinding.signUser.getText().toString())) {
                            if (!TextUtils.isEmpty(loginUiFragmentBinding.signPass.getText().toString())) {
                                token = "Basic " + utility.encodeBase64(loginUiFragmentBinding.signUser.getText().toString() + ":" + loginUiFragmentBinding.signPass.getText().toString());
                                loginUiViewModel.sign_in_hits(token);
                            } else {
                                loginUiFragmentBinding.signPass.setError(getResources().getString(R.string.password_string));
                                loginUiFragmentBinding.signPass.requestFocusFromTouch();
                            }
                        } else {
                            loginUiFragmentBinding.signUser.setError(getResources().getString(R.string.username_string));
                            loginUiFragmentBinding.signUser.requestFocusFromTouch();
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }


        }
        return loginUiFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            context = getActivity();
            utility = new Utility(context);
            loginUiViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(LoginUiViewModel.class);
            if (utility.getUSER() != null) {
                Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                //for not back to previous fragment
                c.popBackStack();
                c.navigate(R.id.frag_profile, null);
            }
            observeprogressbar();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

    }

    private void observeprogressbar() {
        loginUiViewModel.getProgressbar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean progressObserve) {
                if (progressObserve) {
                    utility.showProgress(false, "LOADING");
                } else {
                    utility.hideProgress();
                }
            }
        });
        loginUiViewModel.get_loginLiveData().observe(getActivity(), new Observer<GET_LOGIN>() {
            @Override
            public void onChanged(GET_LOGIN githubrepo) {
                try {
                    if (githubrepo != null) {
                        utility.logger("login" + githubrepo);
                        utility.setUSER(githubrepo);
                        if (!TextUtils.isEmpty(token)) {
                            utility.setUSER_ID(token);
                        }
                        Bundle args = new Bundle();
                        //args.putString("mbl_number", send_mbl.getPhone());
                        Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        //for not back to previous fragment
                        c.popBackStack();
                        c.navigate(R.id.frag_profile, args);
                    } else {
                        utility.showToast(context.getResources().getString(R.string.not_auth_string));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }

}