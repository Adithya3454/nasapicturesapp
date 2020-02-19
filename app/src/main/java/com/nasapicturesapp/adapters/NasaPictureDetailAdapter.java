package com.nasapicturesapp.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nasapicturesapp.R;
import com.nasapicturesapp.model.NasaPicture;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPictureDetailAdapter extends RecyclerView.Adapter<NasaPictureDetailAdapter.NasaDetailItem> {

    private List<NasaPicture> nasaPictureList;
    private Context context;

    /**
     * constructor for picture detail adapter
     *
     * @param nasaPictureList a list of pictures to be displayed
     */
    public NasaPictureDetailAdapter(List<NasaPicture> nasaPictureList, Context context) {
        this.nasaPictureList = nasaPictureList;
        this.context = context;
    }

    @NonNull
    @Override
    public NasaDetailItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View detailItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_picture_detail_item, parent, false);
        return new NasaDetailItem(detailItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final NasaDetailItem holder, int position) {

        //show when binding
        holder.leftIndicator.setVisibility(View.VISIBLE);
        holder.rightIndicator.setVisibility(View.VISIBLE);

        if (position == 0)
            holder.leftIndicator.setVisibility(View.INVISIBLE);
        else if(position == nasaPictureList.size() - 1)
            holder.rightIndicator.setVisibility(View.INVISIBLE);

        holder.rightIndicator.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.rightIndicator.setVisibility(View.INVISIBLE);
            }
        }, 1500);

        holder.leftIndicator.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.leftIndicator.setVisibility(View.INVISIBLE);
            }
        }, 1500);

        final NasaPicture nasaPicture = nasaPictureList.get(position);
        holder.title.setText(nasaPicture.getTitle());
        holder.explanation.setText("Explanation" + "\n" + nasaPicture.getExplanation());
        if (nasaPicture.getCopyright() != null && nasaPicture.getCopyright().length() > 0)
            holder.copyright.setText("Copyright: " + nasaPicture.getCopyright());
        else
            holder.copyright.setVisibility(View.GONE);
        holder.date.setText("Date: " + nasaPicture.getDate());
        holder.url.setText("URL: " + nasaPicture.getUrl());
        holder.hdurl.setText("HD URL: " + nasaPicture.getHdurl());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(nasaPicture.getHdurl()));
                context.startActivity(browserIntent);
            }
        });

        Picasso.get().load(nasaPicture.getHdurl()).placeholder(R.drawable.ic_nasa_vector_logo).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return nasaPictureList.size();
    }

    /**
     * class that represents an item in the list
     */
    public class NasaDetailItem extends RecyclerView.ViewHolder {

        private TextView title, explanation, copyright, date, url, hdurl;
        private ImageView image, leftIndicator, rightIndicator;

        public NasaDetailItem(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            explanation = itemView.findViewById(R.id.explanation);
            copyright = itemView.findViewById(R.id.copyright);
            date = itemView.findViewById(R.id.date);
            url = itemView.findViewById(R.id.url);
            hdurl = itemView.findViewById(R.id.hdurl);
            image = itemView.findViewById(R.id.image);
            leftIndicator = itemView.findViewById(R.id.left_indicator);
            rightIndicator = itemView.findViewById(R.id.right_indicator);
        }
    }

}
