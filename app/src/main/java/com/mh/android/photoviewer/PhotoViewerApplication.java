package com.mh.android.photoviewer;

import android.app.Application;

import com.mh.android.photoviewer.di.AppComponent;
import com.mh.android.photoviewer.di.AppModule;
import com.mh.android.photoviewer.di.DaggerAppComponent;


/**
 * Custom application class
 * Created by @author Mubarak Hussain.
 */
public class PhotoViewerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    /**
     * Initialize the dagger
     */
    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))

                .build();
    }

    private AppComponent appComponent;

    /**
     * Getter for  App component
     * @return
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
