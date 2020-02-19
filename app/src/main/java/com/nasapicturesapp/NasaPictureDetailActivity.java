package com.nasapicturesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nasapicturesapp.adapters.NasaPictureDetailAdapter;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;
import com.nasapicturesapp.presentors.NasaPicturesPresentor;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPictureDetailActivity extends AppCompatActivity implements NasaPicturesMainContract.NasaPicturesView {

    private RecyclerView nasaPicturesRecyclerView;
    private ProgressBar progressBar;
    NasaPicturesPresentor picturesPresenter;
    private TextView errorMessage;
    private int itemToStartAt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_picture_detail);

        if (getIntent().getExtras() != null)
            itemToStartAt = getIntent().getExtras().getInt(Constants.ITEM_POSITION);

        progressBar = findViewById(R.id.progress_bar);
        nasaPicturesRecyclerView = findViewById(R.id.nasa_picture_detail_recycler_view);
        errorMessage = findViewById(R.id.message);
        picturesPresenter = new NasaPicturesPresentor(this, new GetNasaPicturesImpl(getApplicationContext()));
        picturesPresenter.loadNasaPicturesGallery();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNasaPictures(List<NasaPicture> nasaPictureList) {
        nasaPicturesRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        nasaPicturesRecyclerView.setAdapter(new NasaPictureDetailAdapter(nasaPictureList));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(nasaPicturesRecyclerView);
        nasaPicturesRecyclerView.scrollToPosition(itemToStartAt);
    }

    @Override
    public void showError(String error) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(error);
    }

    @Override
    public void showDialogForPicture(NasaPicture nasaPicture) {

    }
}
