package com.nasapicturesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;
import com.nasapicturesapp.presentors.NasaPicturesPresentor;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPicturesActivity extends AppCompatActivity implements NasaPicturesMainContract.NasaPicturesView {

    private RecyclerView nasaPicturesRecyclerView;
    private ProgressBar progressBar;
    NasaPicturesPresentor picturesPresenter;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        nasaPicturesRecyclerView = findViewById(R.id.nasa_pictures_recycler_view);
        message = findViewById(R.id.message);
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
        
    }

    @Override
    public void showError(String error) {
        hideProgress();
        message.setText(error);
    }
}
