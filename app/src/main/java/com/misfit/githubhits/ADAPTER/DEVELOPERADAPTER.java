package com.misfit.githubhits.ADAPTER;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.misfit.githubhits.LIBRARY.Utility;
import com.misfit.githubhits.MODEL.GET.GITHUBDEVO;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;
import com.misfit.githubhits.MODEL.GET.GITREPO.DEVOLIST;
import com.misfit.githubhits.R;

import java.util.List;

public class DEVELOPERADAPTER extends RecyclerView.Adapter<DEVELOPERADAPTER.Todo_View_Holder> {
    Context context;
    List<DEVOLIST> devo_list;
    Utility utility;


    public DEVELOPERADAPTER(List<DEVOLIST> to, Context c) {
        devo_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {

        CardView dev_view;
        ImageView dev_image;
        TextView dev_name;
        TextView dev_username;
        TextView dev_popular;
        TextView dev_des;

        public Todo_View_Holder(View view) {
            super(view);
            dev_view = view.findViewById(R.id.recycler_developer);
            dev_image = view.findViewById(R.id.dev_image);
            dev_name = view.findViewById(R.id.dev_name);
            dev_username = view.findViewById(R.id.dev_username);
            dev_popular = view.findViewById(R.id.dev_popular);
            dev_des = view.findViewById(R.id.dev_des);
        }
    }

    @Override
    public DEVELOPERADAPTER.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_developer, parent, false);

        return new DEVELOPERADAPTER.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final DEVELOPERADAPTER.Todo_View_Holder holder, int position) {
        final DEVOLIST bodyResponse = devo_list.get(position);
        try {
            Glide.with(context.getApplicationContext()).load(bodyResponse.getAvatarUrl()).apply(utility.Glide_Cache_On()).into(holder.dev_image);
            holder.dev_name.setText(bodyResponse.getLogin());
            holder.dev_username.setText(bodyResponse.getType());
            holder.dev_popular.setText(bodyResponse.getReposUrl());
            holder.dev_view.setOnClickListener(new View.OnClickListener() {
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
        return devo_list.size();
    }
}