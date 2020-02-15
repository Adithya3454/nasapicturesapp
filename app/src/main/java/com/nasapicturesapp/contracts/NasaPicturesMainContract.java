package com.nasapicturesapp.contracts;

import com.nasapicturesapp.model.NasaPicture;

import java.util.List;

public class NasaPicturesMainContract {

    public interface NasaPicturesView{

        void showProgress();

        void hideProgress();
    }

    public interface NasaPicturesPresenter {

        void loadNasaPicturesGallery();

        void onDestroy();
    }

    public interface GetNasaPicturesInteractor {

        interface NasaPicturesLoadingFinishedListener{
            void onFinishedLoading(List<NasaPicture> nasaPictureList);
        }

        void getPicturesForNasaGallery(NasaPicturesLoadingFinishedListener nasaPicturesLoadingFinishedListener);
    }

}