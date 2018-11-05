package com.mh.android.photoviewer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.mh.android.photoviewer.adapter.AlbumListAdapter;
import com.mh.android.photoviewer.model.Album;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by @author Mubarak Hussain.
 */

@RunWith(AndroidJUnit4.class)
public class AlbumListAdapterTest {

    List<Album> albumList;

    @Before
    public void setup() {
        albumList = new ArrayList<>();
        Album album = new Album();
        album.setTitle("abc");
        album.setThumbnailUrl("https://via.placeholder.com/150/315aa6");

        Album album1 = new Album();
        album1.setTitle("abc_test");
        album1.setThumbnailUrl("https://via.placeholder.com/150/13454b");

        albumList.add(album);
        albumList.add(album1);
    }

    @Test
    public void testAdapterInit() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();


        AlbumListAdapter albumListAdapter = new AlbumListAdapter(albumList, appContext, null);

        assertNotNull("Adapter should not be null", albumListAdapter);

        try {
            albumListAdapter = new AlbumListAdapter(null, appContext, null);
            assertNull("Adapter object should be null", albumListAdapter);
        } catch (IllegalArgumentException e) {
            assertEquals("Item list or context should not be null", e.getMessage());
        }

        try {
            albumListAdapter = new AlbumListAdapter(null, null, null);
            assertNull("Adapter object should be null", albumListAdapter);
        } catch (IllegalArgumentException e) {
            assertEquals("Item list or context should not be null", e.getMessage());
        }

        try {
            albumListAdapter = new AlbumListAdapter(albumList, null, null);
            assertNull("Adapter object should be null", albumListAdapter);
        } catch (IllegalArgumentException e) {
            assertEquals("Item list or context should not be null", e.getMessage());
        }
    }

    @Test
    public void test_getItemCount() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ItemClickListener itemClickListener = new ItemClickListener();

        AlbumListAdapter albumListAdapter = new AlbumListAdapter(albumList, appContext, itemClickListener);

        //initial state
        int initialExpected = 2;
        int initialActual = albumListAdapter.getItemCount();

        assertEquals(initialExpected, initialActual);

    }

    @Test
    public void test_onBindViewHolder() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ItemClickListener itemClickListener = new ItemClickListener();

        AlbumListAdapter albumListAdapter = new AlbumListAdapter(albumList, appContext, itemClickListener);

        RecyclerView recyclerView = new RecyclerView(appContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(appContext));
        recyclerView.setAdapter(albumListAdapter);

        assertNotNull(recyclerView.getAdapter());
        assertEquals(2, recyclerView.getAdapter().getItemCount());

    }


    @Test
    public void test_onCreateViewHolder() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ItemClickListener itemClickListener = new ItemClickListener();

        AlbumListAdapter albumListAdapter = new AlbumListAdapter(albumList, appContext, itemClickListener);
        LinearLayout parent = new LinearLayout(appContext);

        //child view holder
        RecyclerView.ViewHolder viewHolder = albumListAdapter.onCreateViewHolder(parent, 0);

        assertNotNull("View holder should not be null", viewHolder);

        assertNotNull("Item view should not be null", viewHolder.itemView);
        assertNotNull("Image view should not be null", viewHolder.itemView.findViewById(R.id.imgAlbum));
        assertNotNull("Text view should not be null", viewHolder.itemView.findViewById(R.id.txtTitle));

    }

    class ItemClickListener implements AlbumListAdapter.ListItemCLickListener {

        public Album album;

        @Override
        public void onItemClick(Album album) {
            this.album = album;
        }
    }

}
