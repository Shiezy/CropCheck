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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public int getCrop_id() {
        return crop_id;
    }

    public void setCrop_id(int crop_id) {
        this.crop_id = crop_id;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
