package com.example.cropcheck.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Authorization {


    private int id;

    @SerializedName("token_type")
    public String token_type;

    @SerializedName("expires_in")
    public String expires_in;

    @SerializedName("access_token")
    public String access_token;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
