package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Policy {
    @SerializedName("id")
    private
    int id;
    @SerializedName("title")
    private String title;
    @SerializedName("risks")
    private List<Risk>risks;
    @SerializedName("premium")
    private float premium;
    @SerializedName("terms")
    private String terms;
    @SerializedName("duration")
    private String duration;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public List<Risk> getRisks() {
        return risks;
    }

    public void setRisks(List<Risk> risks) {
        this.risks = risks;
    }

    public String getPremium() {
        return String.valueOf(Math.round(premium*100)/100D);
    }

    public void setPremium(float premium) {
        this.premium = premium;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
