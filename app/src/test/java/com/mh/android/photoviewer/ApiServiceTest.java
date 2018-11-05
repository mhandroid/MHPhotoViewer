package com.mh.android.photoviewer;

import com.mh.android.photoviewer.api.ApiInterface;
import com.mh.android.photoviewer.model.Album;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @author Mubarak Hussain.
 */
@RunWith(JUnit4.class)
public class ApiServiceTest {

    private MockWebServer mockWebServer;
    private ApiInterface apiInterface;
    private MockResponse mockResponse;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiInterface = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url("/"))
                .client(new OkHttpClient())
                .build()
                .create(ApiInterface.class);

        mockResponse = new MockResponse();
    }

    @Test
    public void testGetAlbumList() {
        mockResponse.setResponseCode(200);
        mockResponse.setBody(" [ {\n" +
                "        \"albumId\": 100,\n" +
                "        \"id\": 4998,\n" +
                "        \"title\": \"qui quo cumque distinctio aut voluptas\",\n" +
                "        \"url\": \"https://via.placeholder.com/600/315aa6\",\n" +
                "        \"thumbnailUrl\": \"https://via.placeholder.com/150/315aa6\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"albumId\": 100,\n" +
                "        \"id\": 4999,\n" +
                "        \"title\": \"in voluptate sit officia non nesciunt quis\",\n" +
                "        \"url\": \"https://via.placeholder.com/600/1b9d08\",\n" +
                "        \"thumbnailUrl\": \"https://via.placeholder.com/150/1b9d08\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"albumId\": 100,\n" +
                "        \"id\": 5000,\n" +
                "        \"title\": \"error quasi sunt cupiditate voluptate ea odit beatae\",\n" +
                "        \"url\": \"https://via.placeholder.com/600/6dd9cb\",\n" +
                "        \"thumbnailUrl\": \"https://via.placeholder.com/150/6dd9cb\"\n" +
                "    }\n" +
                "]");
        mockWebServer.enqueue(mockResponse);

        try {
            Response<List<Album>> response = apiInterface.getListOfPhoto().execute();
            Assert.assertNotNull(response);
            Assert.assertEquals(200, response.code());
            Assert.assertEquals(3, response.body().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_errorGetAlbumList() {
        mockResponse.setResponseCode(500);
        mockResponse.throttleBody(1024,1,TimeUnit.MILLISECONDS);

        mockWebServer.enqueue(mockResponse);

        try {
            Response<List<Album>> response = apiInterface.getListOfPhoto().execute();
            Assert.assertNotNull(response);
            Assert.assertEquals(500, response.code());
            Assert.assertNull(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @After
    public void stopService() {
        try {
            mockWebServer.shutdown();
            apiInterface = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
