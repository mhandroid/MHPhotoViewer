package com.mh.android.photoviewer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.android.photoviewer.R;
import com.mh.android.photoviewer.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to provide list of item to recycler view
 * Created by @author Mubarak Hussain.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {
    private static final String TAG = AlbumListAdapter.class.getSimpleName();
    private List<Album> mList;
    private Context mContext;
    private ListItemCLickListener mAlbumItemCLickListener;

    public AlbumListAdapter(List<Album> list, Context context, ListItemCLickListener albumItemCLickListener) {
        if (list == null || context == null) {
            throw new IllegalArgumentException("Item list or context should not be null");
        }
        mList = list;
        mContext = context;
        this.mAlbumItemCLickListener = albumItemCLickListener;
    }


    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item_layout, viewGroup, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {
        final Album album = mList.get(i);
        if (album != null) {
            albumViewHolder.txtTitle.setText(album.getTitle());

            Picasso.with(mContext).load(album.getThumbnailUrl()).placeholder(R.drawable.ic_launcher_background).fit().into(albumViewHolder.imgAlbum);

            albumViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAlbumItemCLickListener != null)
                        mAlbumItemCLickListener.onItemClick(album);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     public class AlbumViewHolder extends RecyclerView.ViewHolder {
        final TextView txtTitle;
        final ImageView imgAlbum;
        final View view;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }

    public interface ListItemCLickListener {
        void onItemClick(Album album);
    }
}
