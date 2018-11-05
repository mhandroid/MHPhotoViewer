package com.mh.android.photoviewer.di;

import android.app.Application;

import com.mh.android.photoviewer.UserRepository;
import com.mh.android.photoviewer.api.ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger class to provide app dependency
 * Created by @author Mubarak Hussain.
 */
@Module
public class AppModule {
    private Application mApplication;


    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    UserRepository provideUserRepository(ApiInterface apiInterface) {
        return new UserRepository(apiInterface);
    }
}
