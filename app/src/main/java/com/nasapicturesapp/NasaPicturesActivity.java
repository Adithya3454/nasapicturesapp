package com.nasapicturesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nasapicturesapp.adapters.NasaPictureGalleryAdapter;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.dialog.ImageOptionsDialogFragment;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;
import com.nasapicturesapp.presentors.NasaPicturesPresentor;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NasaPicturesActivity extends AppCompatActivity implements NasaPicturesMainContract.NasaPicturesView, NasaPicturesMainContract.DialogForSelectedNasaPicture {

    private RecyclerView nasaPicturesRecyclerView;
    private ProgressBar progressBar;
    NasaPicturesPresentor picturesPresenter;
    private TextView errorMessage;
    private ImageOptionsDialogFragment imageOptionsDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        nasaPicturesRecyclerView = findViewById(R.id.nasa_pictures_recycler_view);
        errorMessage = findViewById(R.id.message);
        picturesPresenter = new NasaPicturesPresentor(this, new GetNasaPicturesImpl(getApplicationContext()), this);
        imageOptionsDialogFragment = new ImageOptionsDialogFragment(picturesPresenter);
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
        System.out.println("received pictures");
        nasaPicturesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        nasaPicturesRecyclerView.setAdapter(new NasaPictureGalleryAdapter(nasaPictureList, this, picturesPresenter));
    }

    @Override
    public void showError(String error) {
        hideProgress();
        errorMessage.setText(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        picturesPresenter.onDestroy();
    }


    @Override
    public void showDialogForPicture(NasaPicture nasaPicture) {
        imageOptionsDialogFragment.showDialogForItem(getSupportFragmentManager(), nasaPicture);
    }

    @Override
    public void selectedOptionForNasaPicture(int selectedOption, NasaPicture nasaPicture) {
        Toast.makeText(this, "option selected: "+selectedOption, Toast.LENGTH_SHORT).show();
    }
}
