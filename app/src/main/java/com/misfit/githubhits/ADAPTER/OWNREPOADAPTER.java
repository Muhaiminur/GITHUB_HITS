package com.misfit.githubhits.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;
import com.misfit.githubhits.R;

import java.util.List;

public class OWNREPOADAPTER extends RecyclerView.Adapter<OWNREPOADAPTER.Todo_View_Holder> {
    Context context;
    List<GET_OWNREPO> repo_list;
    Utility utility;


    public OWNREPOADAPTER(List<GET_OWNREPO> to, Context c) {
        repo_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView repo_view;
        TextView repo_title;
        TextView repo_star;

        public Todo_View_Holder(View view) {
            super(view);
            repo_view = view.findViewById(R.id.recycler_own_repo);
            repo_title = view.findViewById(R.id.repo_title);
            repo_star = view.findViewById(R.id.repo_star);
        }
    }

    @Override
    public OWNREPOADAPTER.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_own_repository, parent, false);
        return new OWNREPOADAPTER.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final OWNREPOADAPTER.Todo_View_Holder holder, int position) {
        final GET_OWNREPO bodyResponse = repo_list.get(position);
        try {
            holder.repo_title.setText(bodyResponse.getFullName());
            holder.repo_star.setText(bodyResponse.getStargazersCount().toString());
            holder.repo_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open_link(bodyResponse.getHtmlUrl());
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return repo_list.size();
    }

    //open link to browser
    public void open_link(String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            try {
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            } catch (Exception e2) {
                Log.d("Error Line Number", Log.getStackTraceString(e2));
                utility.showDialog(context.getResources().getString(R.string.no_browser_string));
            }
        }
    }
}