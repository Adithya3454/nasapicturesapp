package com.nasapicturesapp.Utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.nasapicturesapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import androidx.core.app.ShareCompat;

public class Utils {

    public void setWallPaper(final Context context, String url){
        Target imageToBeDownloaded = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(context, "Wallpaper has been set successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(context, "Could not set wallpaper, please try again!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(context, "Could not set wallpaper, please try again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                Toast.makeText(context, "load prepared...", Toast.LENGTH_SHORT).show();
            }
        };

        Picasso.get().load(url).into(imageToBeDownloaded);
    }

    public void shareImage(Activity activity, String url) {
        String textToBeShared = activity.getApplicationContext().getResources().getString(R.string.share_text_message) + "\n" + url;
        ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText(textToBeShared)
                .startChooser();
    }
}
