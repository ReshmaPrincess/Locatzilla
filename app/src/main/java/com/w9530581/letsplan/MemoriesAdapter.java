package com.w9530581.letsplan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoriesAdapter extends RecyclerView.Adapter<MemoriesAdapter.ViewHolder> {
    private List<DtoMemory> listdata;

    public MemoriesAdapter(List<DtoMemory> listdata) {
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
        holder.relativeLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "click on item: " + myListData.getImageName(), Toast.LENGTH_LONG).show());
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ConstraintLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imgViewMemory);
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
