package com.mh.android.photoviewer;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.mh.android.photoviewer.model.Album;
import com.mh.android.photoviewer.model.Resource;
import com.mh.android.photoviewer.viewmodel.AlbumsViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.verify;

/**
 * Class to test Album view model
 * Created by @author Mubarak Hussain.
 */
@RunWith(JUnit4.class)
public class AlbumsViewModelTest {

    @Mock
    private
    UserRepository userRepository;
    private AlbumsViewModel albumsViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        albumsViewModel = new AlbumsViewModel(userRepository);
    }

    @Test
    public void testNull() {

        Assert.assertNotNull(albumsViewModel);
    }

    @Test
    public void testGetListAlbums() {
        Calltest calltest = new Calltest();
        calltest.setResponse(Response.success(getListResult()));
        try {
            Mockito.when(userRepository.getAlbumsOfPhotos()).thenReturn(calltest);
            calltest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TestObserver<Resource<List<Album>>> testObserver = new TestObserver<>();
        albumsViewModel.getListAlbums().observeForever(testObserver);
        Assert.assertNull(testObserver.observedValues.getValue());


        albumsViewModel.setData(Resource.error("Server error"));
        Assert.assertEquals(Resource.Status.ERROR, testObserver.observedValues.getValue().status);
        Assert.assertEquals("Server error", testObserver.observedValues.getValue().message);
        verify(userRepository).getAlbumsOfPhotos();

        List<Album> expectedList = getListResult();
        albumsViewModel.setData(Resource.success(expectedList));
        Assert.assertEquals(Resource.Status.SUCCESS, testObserver.observedValues.getValue().status);
        Assert.assertEquals(expectedList, testObserver.observedValues.getValue().data);
        Assert.assertEquals(expectedList.get(0), testObserver.observedValues.getValue().data.get(0));
        verify(userRepository).getAlbumsOfPhotos();

        albumsViewModel.setData(Resource.noInternet("No internet"));
        Assert.assertEquals(Resource.Status.NO_INTERNET, testObserver.observedValues.getValue().status);
        Assert.assertEquals("No internet", testObserver.observedValues.getValue().message);
        verify(userRepository).getAlbumsOfPhotos();
    }

    private List<Album> getListResult() {
        List<Album> list = new ArrayList<>();
        Album album = new Album();
        list.add(album);
        return list;
    }

    class TestObserver<T> implements Observer<T> {

        MutableLiveData<T> observedValues = new MutableLiveData<T>();

        @Override
        public void onChanged(@Nullable T t) {
            observedValues.setValue(t);
        }


    }

    class Calltest implements Call<List<Album>> {

        Response<List<Album>> response;

        public void setResponse(Response<List<Album>> response) {
            this.response = response;
        }


        @Override
        public Response<List<Album>> execute() throws IOException {

            return response;
        }

        @Override
        public void enqueue(Callback<List<Album>> callback) {

        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call<List<Album>> clone() {
            return null;
        }

        @Override
        public Request request() {
            return null;
        }
    }

}
