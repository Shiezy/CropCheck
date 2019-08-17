package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

public class ImageList {

    public ImageList(String filename) {
        this.filename = filename;
    }

    public ImageList(boolean is_last) {
        this.is_last = is_last;
    }

    @SerializedName("filename")
    public String filename;

    public boolean is_last;
}
