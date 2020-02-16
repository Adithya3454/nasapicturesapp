package com.nasapicturesapp.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nasapicturesapp.Constants;
import com.nasapicturesapp.NasaPictureDetailActivity;
import com.nasapicturesapp.R;
import com.nasapicturesapp.model.NasaPicture;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPictureGalleryAdapter extends RecyclerView.Adapter<NasaPictureGalleryAdapter.NasaGalleryItem> {

    private List<NasaPicture> nasaPictureList;
    private Context context;
    long DURATION = 300;
    private boolean on_attach = true;

    /**
     * constructor for gallery adapter
     * @param nasaPictureList a list of pictures
     * @param context activity context
     */
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
    public void onBindViewHolder(@NonNull NasaGalleryItem holder, final int position) {
        holder.galleryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle pictureToStartWith = new Bundle();
                pictureToStartWith.putInt(Constants.ITEM_POSITION, position);
                Intent openNasaPictureDetails = new Intent(context, NasaPictureDetailActivity.class);
                openNasaPictureDetails.putExtras(pictureToStartWith);
                context.startActivity(openNasaPictureDetails);
            }
        });
        Picasso.get().load(nasaPictureList.get(position).getUrl()).fit().placeholder(R.drawable.ic_nasa_vector_logo).into(holder.galleryItem);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return nasaPictureList.size();
    }

    /**
     * class that represents an item in a gallery
     */
    class NasaGalleryItem extends RecyclerView.ViewHolder {
        ImageView galleryItem;

        NasaGalleryItem(@NonNull View itemView) {
            super(itemView);
            galleryItem = itemView.findViewById(R.id.image);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * animate x to y movement of a view
     * @param itemView view to be animated
     * @param i position of the view in the recycler view
     */
    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(-400f);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }
}
