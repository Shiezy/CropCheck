package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Season {
    @SerializedName("id")
    private int id;
    @SerializedName("site_id")
    private int site_id;
    @SerializedName("crop_id")
    private int crop_id;
    @SerializedName("start_date")
    private Date start_date;
    @SerializedName("end_date")
    private Date end_date;
    @SerializedName("status")
    private Boolean status;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
