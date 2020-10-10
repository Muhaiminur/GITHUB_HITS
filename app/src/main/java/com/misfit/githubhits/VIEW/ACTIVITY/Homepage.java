package com.misfit.githubhits.VIEW.ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.R;
import com.misfit.githubhits.VIEW.FRAGMENT.DeveloperUi;
import com.misfit.githubhits.VIEW.FRAGMENT.RepositoryUi;
import com.misfit.githubhits.VIEWMODEL.HomeViewModel;
import com.misfit.githubhits.databinding.ActivityMainBinding;

public class Homepage extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    Context context;
    Utility utility;
    int item = 2;
    private HomeViewModel homeViewModel;
    GET_LOGIN getLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            context = Homepage.this;
            utility = new Utility(context);
            activityMainBinding.categoryViewpager.setAdapter(new TAB_ADAPTER((getSupportFragmentManager())));
            activityMainBinding.categoryViewpager.setOffscreenPageLimit(2);
            if (null != context) {
                activityMainBinding.categoryTabs.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            activityMainBinding.categoryTabs.setupWithViewPager(activityMainBinding.categoryViewpager);
                            changeTabsFont();
                        } catch (Exception ex) {
                            //utility.call_error(ex);
                        }
                    }
                });
            }
            activityMainBinding.categorySign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, WorkingUi.class));
                    finish();
                }
            });
            getLogin = utility.getUSER();
            if (getLogin != null && !TextUtils.isEmpty(getLogin.getLogin())) {
                activityMainBinding.categorySign.setText(getLogin.getLogin());
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    class TAB_ADAPTER extends FragmentPagerAdapter {

        public TAB_ADAPTER(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RepositoryUi();
                case 1:
                    return new DeveloperUi();
            }
            return null;
        }

        @Override
        public int getCount() {
            return item;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return context.getString(R.string.repositories_string);
                }
                case 1: {
                    return context.getString(R.string.developers_string);
                }
            }
            return null;
        }
    }


    private void changeTabsFont() {
        try {
            ViewGroup vg = (ViewGroup) activityMainBinding.categoryTabs.getChildAt(0);
            int tabsCount = vg.getChildCount();
            for (int j = 0; j < tabsCount; j++) {
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        ((TextView) tabViewChild).setTextSize(30);
                    }
                }
            }
        } catch (Exception ex) {
            //utility.call_error(ex);
        }
    }
}