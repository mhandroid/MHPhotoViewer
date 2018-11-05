package com.mh.android.photoviewer.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mh.android.photoviewer.viewmodel.AlbumsViewModel;
import com.mh.android.photoviewer.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * View model class to provide android view model class instance
 * Created by @author Mubarak Hussain.
 */
@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(AlbumsViewModel.class)
    abstract ViewModel bindAlbumsViewModel(AlbumsViewModel albumViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
