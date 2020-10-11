package com.misfit.githubhits.VIEW.FRAGMENT;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GETBATTLEUSER;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEWMODEL.BattelUiViewModel;
import com.misfit.githubhits.VIEWMODEL.LoginUiViewModel;
import com.misfit.githubhits.databinding.BattelUiFragmentBinding;

public class BattelUi extends Fragment {
    BattelUiFragmentBinding battelUiFragmentBinding;
    Context context;
    Utility utility;
    private BattelUiViewModel battelUiViewModel;
    GETBATTLEUSER userone;
    GETBATTLEUSER usertwo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (battelUiFragmentBinding == null) {
            battelUiFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.battel_ui_fragment, container, false);
            try {
                if (context == null) {
                    context = getActivity();
                    utility = new Utility(context);
                }
                //search one user
                battelUiFragmentBinding.battleOneuserSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(battelUiFragmentBinding.battleOneuser.getText().toString())) {
                            battelUiViewModel.search_user_one(battelUiFragmentBinding.battleOneuser.getText().toString());
                        } else {
                            battelUiFragmentBinding.battleOneuser.setError(getResources().getString(R.string.gituser_string));
                            battelUiFragmentBinding.battleOneuser.requestFocusFromTouch();
                        }
                    }
                });
                //search two user
                battelUiFragmentBinding.battleTwouserSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(battelUiFragmentBinding.battleTwouser.getText().toString())) {
                            battelUiViewModel.search_user_two(battelUiFragmentBinding.battleTwouser.getText().toString());
                        } else {
                            battelUiFragmentBinding.battleTwouser.setError(getResources().getString(R.string.gituser_string));
                            battelUiFragmentBinding.battleTwouser.requestFocusFromTouch();
                        }
                    }
                });
                //user one reset
                battelUiFragmentBinding.battleOneuserReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userone = null;
                        battelUiFragmentBinding.battleOneuserStageOne.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleOneuserStageTwo.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleOneuserStageThree.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleAgain.setVisibility(View.GONE);
                    }
                });
                //user2 reset
                battelUiFragmentBinding.battleTwouserReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usertwo = null;
                        battelUiFragmentBinding.battleTwouserStageOne.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleTwouserStageTwo.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleTwouserStageThree.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleAgain.setVisibility(View.GONE);
                    }
                });
                //comparison between two user
                battelUiFragmentBinding.battleHit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userone != null && usertwo != null) {
                            battelUiFragmentBinding.battleOneuserStageOne.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleOneuserStageTwo.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleOneuserStageThree.setVisibility(View.VISIBLE);
                            battelUiFragmentBinding.battleTwouserStageOne.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleTwouserStageTwo.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleTwouserStageThree.setVisibility(View.VISIBLE);

                            battlework();

                        } else {
                            utility.showToast(context.getResources().getString(R.string.gituser_string));
                        }
                    }
                });
                //battle reset
                battelUiFragmentBinding.battleAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userone = null;
                        battelUiFragmentBinding.battleOneuserStageOne.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleOneuserStageTwo.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleOneuserStageThree.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleAgain.setVisibility(View.GONE);
                        usertwo = null;
                        battelUiFragmentBinding.battleTwouserStageOne.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleTwouserStageTwo.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleTwouserStageThree.setVisibility(View.GONE);
                    }
                });
                //battle with me
                battelUiFragmentBinding.battleMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GET_LOGIN get_login = utility.getUSER();
                        if (get_login != null && !TextUtils.isEmpty(get_login.getName())) {
                            userone = null;
                            battelUiFragmentBinding.battleOneuserStageOne.setVisibility(View.VISIBLE);
                            battelUiFragmentBinding.battleOneuserStageTwo.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleOneuserStageThree.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleAgain.setVisibility(View.GONE);
                            usertwo = null;
                            battelUiFragmentBinding.battleTwouserStageOne.setVisibility(View.VISIBLE);
                            battelUiFragmentBinding.battleTwouserStageTwo.setVisibility(View.GONE);
                            battelUiFragmentBinding.battleTwouserStageThree.setVisibility(View.GONE);
                            battelUiViewModel.search_user_one(get_login.getLogin());
                        } else {
                            utility.showToast(context.getResources().getString(R.string.no_data_string));
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return battelUiFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            context = getActivity();
            utility = new Utility(context);
            battelUiViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(BattelUiViewModel.class);
            observeprogressbar();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void observeprogressbar() {
        battelUiViewModel.getProgressbar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean progressObserve) {
                if (progressObserve) {
                    utility.showProgress(false, "LOADING");
                } else {
                    utility.hideProgress();
                }
            }
        });
        battelUiViewModel.get_user_one().observe(getActivity(), new Observer<GETBATTLEUSER>() {
            @Override
            public void onChanged(GETBATTLEUSER githubrepo) {
                try {
                    if (githubrepo != null) {
                        utility.logger("Search User 1" + githubrepo);
                        userone = githubrepo;
                        Glide.with(context).load(userone.getAvatarUrl()).apply(utility.Glide_Cache_On()).into(battelUiFragmentBinding.battleOneuserImageone);
                        battelUiFragmentBinding.battleOneuserUsername1.setText(userone.getLogin());
                        battelUiFragmentBinding.battleOneuserStageOne.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleOneuserStageTwo.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleOneuserStageThree.setVisibility(View.GONE);
                        if (userone != null && usertwo != null) {
                            battelUiFragmentBinding.battleHit.setVisibility(View.VISIBLE);
                        } else {
                            battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                        }

                    } else {
                        utility.showToast(context.getResources().getString(R.string.no_data_string));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
        battelUiViewModel.get_user_two().observe(getActivity(), new Observer<GETBATTLEUSER>() {
            @Override
            public void onChanged(GETBATTLEUSER githubrepo) {
                try {
                    if (githubrepo != null) {
                        utility.logger("Search User 2" + githubrepo);
                        usertwo = githubrepo;
                        Glide.with(context).load(usertwo.getAvatarUrl()).apply(utility.Glide_Cache_On()).into(battelUiFragmentBinding.battleTwouserImageone);
                        battelUiFragmentBinding.battleTwouserUsername1.setText(usertwo.getLogin());
                        battelUiFragmentBinding.battleTwouserStageOne.setVisibility(View.GONE);
                        battelUiFragmentBinding.battleTwouserStageTwo.setVisibility(View.VISIBLE);
                        battelUiFragmentBinding.battleTwouserStageThree.setVisibility(View.GONE);
                        if (userone != null && usertwo != null) {
                            battelUiFragmentBinding.battleHit.setVisibility(View.VISIBLE);
                        } else {
                            battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
                        }
                    } else {
                        utility.showToast(context.getResources().getString(R.string.no_data_string));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }


    private void battlework() {
        if (userone != null && usertwo != null) {
            Glide.with(context).load(userone.getAvatarUrl()).apply(utility.Glide_Cache_On()).into(battelUiFragmentBinding.battleOneuserImagesecond);
            Glide.with(context).load(usertwo.getAvatarUrl()).apply(utility.Glide_Cache_On()).into(battelUiFragmentBinding.battleTwouserImagesecond);
            battelUiFragmentBinding.battleOneuserUsername2.setText(userone.getLogin());
            battelUiFragmentBinding.battleTwouserUsername2.setText(usertwo.getLogin());
            battelUiFragmentBinding.battleOneuserName.setText(userone.getName());
            battelUiFragmentBinding.battleTwouserName.setText(usertwo.getName());
            battelUiFragmentBinding.battleOneuserAddress.setText(userone.getLocation());
            battelUiFragmentBinding.battleTwouserAddress.setText(usertwo.getLocation());
            battelUiFragmentBinding.battleOneuserFollower.setText(context.getResources().getString(R.string.follower_string) + " " + userone.getFollowers());
            battelUiFragmentBinding.battleTwouserFollower.setText(context.getResources().getString(R.string.follower_string) + " " + usertwo.getFollowers());
            battelUiFragmentBinding.battleOneuserFollowing.setText(context.getResources().getString(R.string.following_string) + " " + userone.getFollowing());
            battelUiFragmentBinding.battleTwouserFollowing.setText(context.getResources().getString(R.string.following_string) + " " + usertwo.getFollowing());
            battelUiFragmentBinding.battleOneuserPublicrepo.setText(context.getResources().getString(R.string.pub_repo_string) + " " + userone.getPublicRepos());
            battelUiFragmentBinding.battleTwouserPublicrepo.setText(context.getResources().getString(R.string.pub_repo_string) + " " + usertwo.getPublicRepos());
            int one = userone.getFollowers() + userone.getFollowing() + userone.getPublicRepos() + userone.getPublicGists();
            int two = usertwo.getFollowers() + usertwo.getFollowing() + usertwo.getPublicRepos() + usertwo.getPublicGists();

            battelUiFragmentBinding.battleOneuserScore.setText(context.getResources().getString(R.string.score_string) + " " + one);
            battelUiFragmentBinding.battleTwouserScore.setText(context.getResources().getString(R.string.score_string) + " " + two);
            if (one > two) {
                battelUiFragmentBinding.battleOneuserResult.setText(context.getResources().getString(R.string.winner_string));
                battelUiFragmentBinding.battleTwouserResult.setText(context.getResources().getString(R.string.looser_string));
            } else {
                battelUiFragmentBinding.battleOneuserResult.setText(context.getResources().getString(R.string.looser_string));
                battelUiFragmentBinding.battleTwouserResult.setText(context.getResources().getString(R.string.winner_string));
            }
            battelUiFragmentBinding.battleHit.setVisibility(View.GONE);
            battelUiFragmentBinding.battleAgain.setVisibility(View.VISIBLE);

        } else {
            utility.showToast(context.getResources().getString(R.string.gituser_string));
        }
    }
}