package com.nasapicturesapp.Utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.app.ShareCompat;

import com.nasapicturesapp.R;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class Utils {

    public void setWallPaper(final Context context, String url, final NasaPicturesMainContract.NasaPicturesView picturesView){
        Target imageToBeDownloaded = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                try {
                    wallpaperManager.setBitmap(bitmap);
                    picturesView.hideProgress();
                    Toast.makeText(context, "Wallpaper has been set successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    picturesView.hideProgress();
                    Toast.makeText(context, "Could not set wallpaper, please try again!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                picturesView.hideProgress();
                Toast.makeText(context, "Could not set wallpaper, please try again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                Toast.makeText(context, "load prepared...", Toast.LENGTH_SHORT).show();
            }
        };
        picturesView.showProgress();
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
