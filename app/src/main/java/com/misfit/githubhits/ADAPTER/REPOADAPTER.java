package com.misfit.githubhits.ADAPTER;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;
import com.misfit.githubhits.R;

import java.util.List;

public class REPOADAPTER extends RecyclerView.Adapter<REPOADAPTER.Todo_View_Holder> {
    Context context;
    List<REPOLIST> repo_list;
    Utility utility;


    public REPOADAPTER(List<REPOLIST> to, Context c) {
        repo_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView repo_view;
        TextView repo_title;
        ImageView repo_setstar;
        TextView repo_descrp;
        TextView repo_language;
        TextView repo_star;
        TextView repo_fork;

        public Todo_View_Holder(View view) {
            super(view);
            repo_view = view.findViewById(R.id.recycler_repo);
            repo_title = view.findViewById(R.id.repo_title);
            repo_setstar = view.findViewById(R.id.repo_set_star);
            repo_descrp = view.findViewById(R.id.repo_des);
            repo_language = view.findViewById(R.id.repo_language);
            repo_star = view.findViewById(R.id.repo_star);
            repo_fork = view.findViewById(R.id.repo_fork);
        }
    }

    @Override
    public REPOADAPTER.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_repository, parent, false);
        return new REPOADAPTER.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final REPOADAPTER.Todo_View_Holder holder, int position) {
        final REPOLIST bodyResponse = repo_list.get(position);
        try {
            holder.repo_title.setText(bodyResponse.getFullName());
            holder.repo_descrp.setText(bodyResponse.getDescription());
            holder.repo_language.setText(bodyResponse.getLanguage());
            holder.repo_star.setText(bodyResponse.getStargazersCount().toString());
            holder.repo_fork.setText(bodyResponse.getForksCount().toString());
            //Glide.with(context).load(bodyResponse.getProductPicture()).apply(utility.Glide_Cache_On()).into(holder.fish_image);
            holder.repo_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
}