package com.mh.android.photoviewer.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mh.android.photoviewer.PhotoViewerApplication;
import com.mh.android.photoviewer.R;
import com.mh.android.photoviewer.adapter.AlbumListAdapter;
import com.mh.android.photoviewer.model.Album;
import com.mh.android.photoviewer.model.Resource;
import com.mh.android.photoviewer.utils.Utils;
import com.mh.android.photoviewer.viewmodel.AlbumsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Home activity to show list of album
 * Created by @author Mubarak Hussain.
 */
public class HomeActivity extends BaseActivity implements AlbumListAdapter.ListItemCLickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AlbumsViewModel albumsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLayout(R.layout.activity_home);

        configureDagger();

        setHomeButtonEnabled(true);

        initializeView();

        albumsViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumsViewModel.class);

        if (Utils.isNetworkAvailable(this)) {
            hideErrorText();
            loadData(albumsViewModel);
        } else {
            showErrorText(R.string.no_data_available);
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, getString(R.string.no_iternet), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to initialize view
     */
    private void initializeView() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new AlbumListAdapter(new ArrayList<>(), this, this));
    }

    /**
     * Method to init dagger
     */
    private void configureDagger() {
        ((PhotoViewerApplication) getApplication()).getAppComponent().inject(this);
    }


    /**
     * Method to load data from server
     *
     * @param albumsViewModel
     */
    private void loadData(AlbumsViewModel albumsViewModel) {
        albumsViewModel.getListAlbums().observe(this, new Observer<Resource<List<Album>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Album>> listResource) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (listResource != null) {
                    Log.d(TAG, listResource.data + "");
                    if (Resource.Status.SUCCESS == listResource.status) {
                        hideErrorText();
                        AlbumListAdapter adapter = new AlbumListAdapter(listResource.data, HomeActivity.this, HomeActivity.this);
                        mRecyclerView.setAdapter(adapter);
                    } else if (Resource.Status.NO_INTERNET == listResource.status) {
                        showErrorText(R.string.no_data_available);
                        Toast.makeText(HomeActivity.this, getString(R.string.no_iternet), Toast.LENGTH_SHORT).show();
                    } else {
                        showErrorText(R.string.no_data_available);
                        Toast.makeText(HomeActivity.this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(Album album) {
        if (Utils.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, FullScreenActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Utils.ALBUM_TITLE, album.getTitle());
            bundle.putString(Utils.ALBUM_URL, album.getUrl());
            intent.putExtras(bundle);
            startActivity(intent);
        } else
            Toast.makeText(this, getString(R.string.no_iternet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        if (Utils.isNetworkAvailable(this)) {
            hideErrorText();
            loadData(albumsViewModel);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showErrorText(R.string.no_data_available);
            Toast.makeText(this, getString(R.string.no_iternet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        if (albumsViewModel != null)
            albumsViewModel.stop();
        super.onStop();
    }
}
