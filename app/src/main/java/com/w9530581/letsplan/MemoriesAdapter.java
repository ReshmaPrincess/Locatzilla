package com.w9530581.letsplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MemoriesAdapter extends RecyclerView.Adapter<MemoriesAdapter.ViewHolder> {
    private ArrayList<DtoMemory> listdata;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MemoriesAdapter(ArrayList<DtoMemory> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.memory_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DtoMemory myListData = listdata.get(position);

        holder.imageView.setImageBitmap(getBitmapFromEncodedString(myListData.getImagePath()));
        holder.imageView4.setOnClickListener(v -> {
            executor.execute(() -> DatabaseEngine.getInstance().getDBInstance().memoriesDao().deleteMemory(myListData));
            listdata.remove(position);
            notifyItemRemoved(position);
        });

        holder.relativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PreviewerActivity.class);
            intent.putExtra("ImageUrl", myListData.getImagePath());
            view.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, imageView4;
        public ConstraintLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imgViewMemory);
            this.imageView4 = itemView.findViewById(R.id.imageView4);
            relativeLayout = itemView.findViewById(R.id.clMemoryItem);
        }
    }

    private Bitmap getBitmapFromEncodedString(String encodedString) {
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }

    private void loadImageIntoImageView() {
//        imageView.setImageBitmap(Bitmap.createScaledBitmap(theImage, imageView.getWidth(), imageView.getHeight(), false));
    }
}
