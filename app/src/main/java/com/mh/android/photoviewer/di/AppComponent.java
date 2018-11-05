package com.mh.android.photoviewer.di;

import com.mh.android.photoviewer.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by @author Mubarak Hussain.
 */
@Singleton
@Component(modules = {ViewModelModule.class, NetModule.class, AppModule.class})
public interface AppComponent {

    void inject(HomeActivity activity);

}
