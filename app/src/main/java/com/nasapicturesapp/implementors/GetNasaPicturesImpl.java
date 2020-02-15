package com.nasapicturesapp.implementors;

import android.content.Context;

import com.nasapicturesapp.Constants;
import com.nasapicturesapp.R;
import com.nasapicturesapp.contracts.NasaPicturesMainContract;
import com.nasapicturesapp.model.NasaPicture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that implements NasaPicturesMainContract.GetNasaPicturesInteractor
 */
public class GetNasaPicturesImpl implements NasaPicturesMainContract.GetNasaPicturesInteractor {

    private String JSON_FILE_NAME = "data.json";
    private NasaPicturesLoadingFinishedListener nasaPicturesLoadingFinishedListener;
    private Context context;

    public GetNasaPicturesImpl(NasaPicturesLoadingFinishedListener nasaPicturesLoadingFinishedListener, Context context) {
        this.nasaPicturesLoadingFinishedListener = nasaPicturesLoadingFinishedListener;
        this.context = context;
    }

    @Override
    public void getPicturesForNasaGallery() {
        if (nasaPicturesLoadingFinishedListener != null) {
            List<NasaPicture> nasaPictureList = loadNasaPicturesFromAssets(context);
            if (nasaPictureList.size() > 0)
                nasaPicturesLoadingFinishedListener.onCompletion(nasaPictureList);
            else
                nasaPicturesLoadingFinishedListener.onFailure(context.getResources().getString(R.string.no_pictures_available));
        }
    }

    /**
     * Parses the JSONArray and converts it to a List<NasaPictures> and the size of list is 0 in case of an parse error or the if the list is empty.
     *
     * @param context application context of android.
     * @return a List<NasaPicture>
     */
    private List<NasaPicture> loadNasaPicturesFromAssets(Context context) {
        List<NasaPicture> nasaPictureList = new ArrayList<NasaPicture>();

        try {
            JSONArray nasaPicturesJSonArray = new JSONArray(loadJsonFromAssetFile(context, JSON_FILE_NAME));
            String[] nasaPictureObjectKeys = {Constants.copyright_key, Constants.date_key, Constants.explanation_key, Constants.hdurl_key, Constants.media_type_key, Constants.service_version_key, Constants.title_key, Constants.url_key};
            for (int i = 0; i < nasaPicturesJSonArray.length(); i++) {
                JSONObject nasaPictureObject = (JSONObject) nasaPicturesJSonArray.get(i);

                String copyright = null, date = null, explanation = null, hdurl = null, media_type = null, service_version = null, title = null, url = null;

                for (String key : nasaPictureObjectKeys)
                    if (nasaPictureObject.has(key)) {
                        switch (key) {
                            case Constants.copyright_key:
                                copyright = nasaPictureObject.getString(Constants.copyright_key);
                                break;
                            case Constants.date_key:
                                date = nasaPictureObject.getString(Constants.date_key);
                                break;
                            case Constants.explanation_key:
                                explanation = nasaPictureObject.getString(Constants.explanation_key);
                                break;
                            case Constants.hdurl_key:
                                hdurl = nasaPictureObject.getString(Constants.hdurl_key);
                                break;
                            case Constants.media_type_key:
                                media_type = nasaPictureObject.getString(Constants.media_type_key);
                                break;
                            case Constants.service_version_key:
                                service_version = nasaPictureObject.getString(Constants.service_version_key);
                                break;
                            case Constants.title_key:
                                title = nasaPictureObject.getString(Constants.title_key);
                                break;
                            case Constants.url_key:
                                url = nasaPictureObject.getString(Constants.url_key);
                                break;
                        }
                    }

                NasaPicture nasaPicture = new NasaPicture(copyright, date, explanation, hdurl, media_type, service_version, title, url);
                nasaPictureList.add(nasaPicture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            nasaPictureList.clear();
            return nasaPictureList;
        }

        return nasaPictureList;
    }

    /**
     * Reads the file from assets directory and returns a String with the contents of the file name.
     *
     * @param context application context of android.
     * @param file    name of the file to read.
     * @return String
     */
    private String loadJsonFromAssetFile(Context context, String file) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}