package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    private int id;
    @SerializedName("filename")
    private String filename;

    @SerializedName("farm_id")
    private int site_id;

    @SerializedName("season_id")
    private int season_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }
}
