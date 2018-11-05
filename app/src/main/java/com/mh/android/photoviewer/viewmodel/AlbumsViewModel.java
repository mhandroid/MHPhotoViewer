package com.mh.android.photoviewer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mh.android.photoviewer.UserRepository;
import com.mh.android.photoviewer.exception.NoNetworkException;
import com.mh.android.photoviewer.model.Album;
import com.mh.android.photoviewer.model.Resource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * View model class to provide list of album item
 * Created by @author Mubarak Hussain.
 */
public class AlbumsViewModel extends ViewModel {
    private final String TAG = AlbumsViewModel.class.getSimpleName();
    private UserRepository userRepository;
    private Call<List<Album>> call;
    private MutableLiveData<Resource<List<Album>>> albumListMutableLiveData = new MutableLiveData<>();

    @Inject
    public AlbumsViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Method to fetch list of albums
     *
     * @return
     */
    public LiveData<Resource<List<Album>>> getListAlbums() {

        call = userRepository.getAlbumsOfPhotos();

        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.body() != null)
                    setData(Resource.success(response.body()));
                else {
                    setData(Resource.error("Something went wrong!!"));
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                if (t instanceof NoNetworkException) {
                    Log.d(TAG, "No connectivity exception");
                    setData(Resource.noInternet(t.getMessage()));
                } else
                    setData(Resource.error(t.getMessage()));
            }
        });


        return albumListMutableLiveData;
    }

    /**
     * method to set data
     * @param resource
     */
    public void setData(Resource<List<Album>> resource) {
        if (resource != null)
            albumListMutableLiveData.setValue(resource);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * If activity get stop cancel the network call
     */
    public void stop() {
        if (call != null) {
            call.cancel();
        }
    }
}
