package com.nasapicturesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nasapicturesapp.R;
import com.nasapicturesapp.model.NasaPicture;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPictureGalleryAdapter extends RecyclerView.Adapter<NasaPictureGalleryAdapter.NasaGalleryItem> {

    private List<NasaPicture> nasaPictureList;
    private Context context;

    public NasaPictureGalleryAdapter(List<NasaPicture> nasaPictureList, Context context) {
        this.nasaPictureList = nasaPictureList;
        this.context = context;
    }

    @NonNull
    @Override
    public NasaGalleryItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View galleryItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_picture_gallery_list_item, parent, false);
        return new NasaGalleryItem(galleryItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaGalleryItem holder, int position) {
        Picasso.get().load(nasaPictureList.get(position).getUrl()).fit().placeholder(R.drawable.ic_nasa_vector_logo).into(holder.galleryItem);

    }

    @Override
    public int getItemCount() {
        return nasaPictureList.size();
    }

    class NasaGalleryItem extends RecyclerView.ViewHolder {
        ImageView galleryItem;
        public NasaGalleryItem(@NonNull View itemView) {
            super(itemView);
            galleryItem = itemView.findViewById(R.id.image);
        }
    }
}
