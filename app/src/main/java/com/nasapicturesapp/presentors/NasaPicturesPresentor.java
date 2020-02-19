package com.nasapicturesapp.presentors;

import com.nasapicturesapp.adapters.NasaPictureGalleryAdapter;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;

import java.util.List;

/**
 * class implementing presentor and interactor to facilitate presentation of the data on the activity
 */
public class NasaPicturesPresentor implements NasaPicturesMainContract.NasaPicturesPresenter,  NasaPicturesMainContract.GetNasaPicturesInteractor.NasaPicturesLoadingFinishedListener, NasaPictureGalleryAdapter.NasaPictureGalleryAdpterItemClickListener {

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

    @Override
    public void onItemLongClickListener(NasaPicture nasaPicture) {
        if (nasaPicturesView != null)
            nasaPicturesView.showDialogForPicture(nasaPicture);
    }
}
