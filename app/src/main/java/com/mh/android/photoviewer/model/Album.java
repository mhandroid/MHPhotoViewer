package com.mh.android.photoviewer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Pojo class for each album item
 * Created by @author Mubarak Hussain.
 */
public class Album implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("albumId")
    private String albumId;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", title = " + title + ", albumId = " + albumId + ", thumbnailUrl = " + thumbnailUrl + ", url = " + url + "]";
    }
}
