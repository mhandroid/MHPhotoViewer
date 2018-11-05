package com.mh.android.photoviewer;

import com.mh.android.photoviewer.api.ApiInterface;
import com.mh.android.photoviewer.model.Album;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Repository class to fetch data
 * Created by @author Mubarak Hussain.
 */
public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();


    private ApiInterface apiInterface;

    @Inject
    public UserRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }


    /**
     * Method to provide list of album
     * @return
     */
    public Call<List<Album>> getAlbumsOfPhotos() {
        return apiInterface.getListOfPhoto();
    }

}
