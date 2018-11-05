package com.mh.android.photoviewer;

import com.mh.android.photoviewer.api.ApiInterface;
import com.mh.android.photoviewer.model.Album;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by @author Mubarak Hussain.
 */
public class UserRepositoryTest {
    private static final String BASE_URL = "http://192.168.1.12:8089";

    private ApiInterface apiInterface;
    private List<Album> list;

    @Before
    public void setUp() {

        apiInterface = Mockito.mock(ApiInterface.class);
        list = new ArrayList<>();
        genrateListAlbum();

    }

    private void genrateListAlbum() {
        Album album = new Album();
        album.setAlbumId("1");
        album.setId("1");
        album.setThumbnailUrl("http://localhost/photo");
        album.setUrl("https://via.placeholder.com/600/6dd9cb");

        list.add(album);
    }

    @Test
    public void testGetAlbumPhotos() {

        UserRepository userRepository = new UserRepository(apiInterface);
        Assert.assertNotNull("No null", userRepository);

        Mockito.when(userRepository.getAlbumsOfPhotos()).thenReturn(new Call<List<Album>>() {
            @Override
            public Response<List<Album>> execute() throws IOException {
                return Response.success(list);
            }

            @Override
            public void enqueue(Callback<List<Album>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return true;
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
        });
        Call<List<Album>> call = userRepository.getAlbumsOfPhotos();

        try {

            Assert.assertNotNull(call.execute());

            Response<List<Album>> response = call.execute();
            Assert.assertEquals(200, response.code());
            Assert.assertTrue(response.isSuccessful());
            Assert.assertNotNull(response.body());
            Assert.assertEquals(1, response.body().size());
            Assert.assertEquals("Album id should be", "1", response.body().get(0).getAlbumId());
            Assert.assertEquals("Id should be", "1", response.body().get(0).getId());
            Assert.assertEquals("Album thumbnail url should be", "http://localhost/photo", response.body().get(0).getThumbnailUrl());
            Assert.assertEquals("Album id should be", "https://via.placeholder.com/600/6dd9cb", response.body().get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
