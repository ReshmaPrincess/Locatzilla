package com.w9530581.letsplan;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.w9530581.letsplan.Models.Result;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SavedActivitiesAdapter extends RecyclerView.Adapter<SavedActivitiesAdapter.ViewHolder> {
    public ArrayList<Result> activities;
    public Activity activityContext;
    private Executor executor = Executors.newSingleThreadExecutor();

    public SavedActivitiesAdapter(Activity activityContext, ArrayList<Result> activities) {
        this.activityContext = activityContext;
        this.activities = activities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int currentPosition = position;
        final Result result = activities.get(position);
        StringBuilder traits = new StringBuilder().append("Name : \t").append(result.getName().getFirst()).append(" ").append(result.getName().getLast()).append("\n")
                .append("Gender : \t").append(result.getGender()).append("\n")
                .append("Email : \t").append(result.getEmail()).append("\n")
                .append("DOB : \t").append(result.getDob().getDate()).append("\n")
                .append("Phone : \t").append(result.getPhone()).append("\n")
                .append("Location : \t").append(result.getLocation().getCity()).append(" ").append(result.getLocation().getState()).append(" ").append(result.getLocation().getCountry());

        holder.tvSavedActivity.setText(traits.toString());

        holder.ivShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, traits.toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Something worthy...");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            activityContext.startActivity(shareIntent);
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activities.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSavedActivity;
        private ImageView ivShare, ivDelete;
        private Activity activity;

        public ViewHolder(View itemView) {
            super(itemView);
            this.activity = activity;
            this.tvSavedActivity = itemView.findViewById(R.id.tvSavedActivity);
            this.ivShare = itemView.findViewById(R.id.ivShare);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
