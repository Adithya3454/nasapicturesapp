package com.nasapicturesapp.presentors;

import com.nasapicturesapp.adapters.NasaPictureGalleryAdapter;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.dialog.ImageOptionsDialogFragment;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;

import java.util.List;

/**
 * class implementing presentor and interactor to facilitate presentation of the data on the activity
 */
public class NasaPicturesPresentor implements NasaPicturesMainContract.NasaPicturesPresenter, NasaPicturesMainContract.GetNasaPicturesInteractor.NasaPicturesLoadingFinishedListener, NasaPictureGalleryAdapter.NasaPictureGalleryAdpterItemClickListener, ImageOptionsDialogFragment.ImageOptionsDialogFragmentOptionSelectionListener {

    private NasaPicturesMainContract.NasaPicturesView nasaPicturesView;
    private GetNasaPicturesImpl getNasaPictures;
    private NasaPicturesMainContract.DialogForSelectedNasaPicture dialogForSelectedNasaPicture;

    public NasaPicturesPresentor(NasaPicturesMainContract.NasaPicturesView nasaPicturesView, GetNasaPicturesImpl getNasaPictures, NasaPicturesMainContract.DialogForSelectedNasaPicture dialogForSelectedNasaPicture) {
        this.nasaPicturesView = nasaPicturesView;
        this.getNasaPictures = getNasaPictures;
        this.dialogForSelectedNasaPicture = dialogForSelectedNasaPicture;
    }

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
        if (nasaPicturesView != null) {
            nasaPicturesView.hideProgress();
            nasaPicturesView.displayNasaPictures(nasaPictureList);
        }
    }

    @Override
    public void onFailure(String error) {
        if (nasaPicturesView != null) {
            nasaPicturesView.hideProgress();
            nasaPicturesView.showError(error);
        }

    }

    @Override
    public void onItemLongClickListener(NasaPicture nasaPicture) {
        if (dialogForSelectedNasaPicture != null)
            dialogForSelectedNasaPicture.showDialogForPicture(nasaPicture);
    }

    @Override
    public void optionSelected(int selectedOption, NasaPicture nasaPicture) {
        if (dialogForSelectedNasaPicture != null)
            dialogForSelectedNasaPicture.selectedOptionForNasaPicture(selectedOption, nasaPicture);
    }
}
