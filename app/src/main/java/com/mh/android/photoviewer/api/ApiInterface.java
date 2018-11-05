package com.mh.android.photoviewer.api;

import com.mh.android.photoviewer.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface of api resource
 * Created by @author Mubarak Hussain.
 */
public interface ApiInterface {

    /**
     * Interface method to of get request for album list
     * @return
     */
    @GET("/photos")
    Call<List<Album>> getListOfPhoto();


}
