package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("id")
    private
    int id;
    @SerializedName("site_name")
    private String site_name;
    @SerializedName("size")
    private String size;
    @SerializedName("county")
    private String county;
    @SerializedName("division")
    private String division;
    @SerializedName("village")
    private String village;

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSize() {
        return size;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
