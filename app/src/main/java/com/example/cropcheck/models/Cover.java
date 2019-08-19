package com.example.cropcheck.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Cover {

    @SerializedName("id")
    private
    int id;

    @SerializedName("policy_id")
    public Integer policy_id;

    @SerializedName("farm_id")
    public Integer farm_id;

    @SerializedName("user_id")
    public Integer user_id;

    @SerializedName("status")
    public Integer status;

    @SerializedName("expiry_date")
    public String expiry_date;

    @SerializedName("start_date")
    public String start_date;

    @SerializedName("policy")
    public Policy policy;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Integer getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(Integer farm_id) {
        this.farm_id = farm_id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
