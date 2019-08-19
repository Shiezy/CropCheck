package com.example.cropcheck.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Cover {

    @SerializedName("id")
    private
    int id;

    @SerializedName("policy_id")
    public Integer policy_id;

    @SerializedName("user_id")
    public Integer user_id;

    @SerializedName("status")
    public Integer status;

    @SerializedName("expiry_date")
    public String expiry_date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(Integer policy_id) {
        this.policy_id = policy_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
