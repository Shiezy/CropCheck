package com.example.cropcheck.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Cover {

    private int id;

    @SerializedName("policy_id")
    public Integer policy_id;

    @SerializedName("user_id")
    public Integer user_id;

    @SerializedName("status")
    public Integer status;

    @SerializedName("expiry_date")
    public String expiry_date;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

}
