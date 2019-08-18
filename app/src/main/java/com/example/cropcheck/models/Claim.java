package com.example.cropcheck.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Claim {

    private int id;

    @SerializedName("cover_id")
    public Integer cover_id;

    @SerializedName("claim_amount")
    public String claim_amount;

    @SerializedName("status")
    public Integer status;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

}
