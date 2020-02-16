package com.nasapicturesapp.contracts;

import com.nasapicturesapp.model.NasaPicture;

import java.util.List;


/**
 * Main contract that contains all the interfaces for loading and presenting data ont he activity
 */
public class NasaPicturesMainContract {

    /**
     * interface for the view operations
     */
    public interface NasaPicturesView{

        void showProgress();

        void hideProgress();

        void displayNasaPictures(List<NasaPicture> nasaPictureList);

        void showError(String error);
    }

    /**
     * interface for loading data
     */
    public interface NasaPicturesPresenter {

        void loadNasaPicturesGallery();

        void onDestroy();
    }

    /**
     * interface for interactor
     */
    public interface GetNasaPicturesInteractor {

        interface NasaPicturesLoadingFinishedListener{
            void onCompletion(List<NasaPicture> nasaPictureList);
            void onFailure(String error);
        }

        void getPicturesForNasaGallery(NasaPicturesLoadingFinishedListener nasaPicturesLoadingFinishedListener);
    }

}