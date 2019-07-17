package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Season {
    @SerializedName("id")
    private int id;
    @SerializedName("farm_id")
    private int site_id;
    @SerializedName("crop_id")
    private int crop_id;
    @SerializedName("start_time")
    private String start_date;
    @SerializedName("end_time")
    private String end_date;
    @SerializedName("status")
    private int status;

    public String getStart_date(){
        return start_date;
    }
    public void setStart_date(String start_date){
        this.start_date = start_date;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
