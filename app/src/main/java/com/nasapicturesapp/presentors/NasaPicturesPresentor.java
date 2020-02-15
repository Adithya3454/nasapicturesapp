package com.nasapicturesapp.presentors;

import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;

import java.util.List;

public class NasaPicturesPresentor implements NasaPicturesMainContract.NasaPicturesPresenter,  NasaPicturesMainContract.GetNasaPicturesInteractor.NasaPicturesLoadingFinishedListener{

    private NasaPicturesMainContract.NasaPicturesView nasaPicturesView;
    private GetNasaPicturesImpl getNasaPictures;

    public NasaPicturesPresentor(NasaPicturesMainContract.NasaPicturesView nasaPicturesView, GetNasaPicturesImpl getNasaPictures) {
        this.nasaPicturesView = nasaPicturesView;
        this.getNasaPictures = getNasaPictures;
    }

    @Override
    public void loadNasaPicturesGallery() {
        if (nasaPicturesView != null)
            nasaPicturesView.showProgress();

        getNasaPictures.getPicturesForNasaGallery(this);
    }

    @Override
    public void onDestroy() {
        nasaPicturesView = null;
    }

    @Override
    public void onCompletion(List<NasaPicture> nasaPictureList) {
        if (nasaPicturesView != null){
            nasaPicturesView.hideProgress();
            nasaPicturesView.displayNasaPictures(nasaPictureList);
        }
    }

    @Override
    public void onFailure(String error) {
        if (nasaPicturesView != null){
            nasaPicturesView.hideProgress();
            nasaPicturesView.showError(error);
        }

    }
}
