package com.mh.android.photoviewer.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mh.android.photoviewer.api.ApiInterface;
import com.mh.android.photoviewer.utils.ConnectivityInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger module Class to provide net dependency
 * Created by @author Mubarak Hussain.
 */
@Module
public class NetModule {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Cache cache, Application context) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(httpLoggingInterceptor).cache(cache);
        client.addInterceptor(new ConnectivityInterceptor(context));
        return client.build();
    }

    /**
     * Dagger provide method for Gson object
     *
     * @return
     */
    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    /**
     * Dagger provide method for Rxjava adapter factory
     *
     * @return
     */
    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    /**
     * Dagger provide method for Retrofit object
     *
     * @param okHttpClient
     * @param gson
     * @param rxJava2CallAdapterFactory
     * @return
     */
    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();

        return retrofit;
    }

    /**
     * Dagger provide method for http cache
     *
     * @param application
     * @return
     */
    @Singleton
    @Provides
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    /**
     * Dagger provide method for api interface
     *
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    /**
     * Dagger provide method for logging interceptor
     *
     * @return
     */
    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingIntercepter() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
