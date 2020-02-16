package com.nasapicturesapp;

import android.content.Context;

import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.implementors.GetNasaPicturesImpl;
import com.nasapicturesapp.model.NasaPicture;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

/**
 * Check if nasa pictures list has values
 */
@RunWith(AndroidJUnit4.class)
public class NasaPicturesTest {
    @Test
    public void checkNasaPictures() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        NasaPicturesMainContract.GetNasaPicturesInteractor.NasaPicturesLoadingFinishedListener loadingFinishedListener = new NasaPicturesMainContract.GetNasaPicturesInteractor.NasaPicturesLoadingFinishedListener() {
            @Override
            public void onCompletion(List<NasaPicture> nasaPictureList) {
                int empty = 0;
                assertTrue(nasaPictureList.size() > empty);
            }

            @Override
            public void onFailure(String error) {

            }
        };

        GetNasaPicturesImpl nasaPictureList = new GetNasaPicturesImpl(appContext);
        nasaPictureList.getPicturesForNasaGallery(loadingFinishedListener);

    }

    @Rule
    public ActivityTestRule<NasaPicturesActivity> nasaPicturesActivityActivityTestRule = new ActivityTestRule<>(NasaPicturesActivity.class);

    /**
     * Check if detail picture activity is opening and detail activity is swiping
     */
   @Test
    public void checkIfNasaPicturesClickIsFunctioning(){
       Intents.init();
       Espresso.onView(withId(R.id.nasa_pictures_recycler_view)).perform(click());
       intended(hasComponent(NasaPictureDetailActivity.class.getName()));

       Espresso.onView(withId(R.id.nasa_picture_detail_recycler_view)).perform(ViewActions.swipeRight());
   }


}
