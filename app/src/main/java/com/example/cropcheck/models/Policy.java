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
    private List<String>risks;
    @SerializedName("premium")
    private float premium;
    @SerializedName("terms")
    private String terms;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public List<String> getRisks() {
        return risks;
    }

    public void setRisks(List<String> risks) {
        this.risks = risks;
    }

    public float getPremium() {
        return premium;
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
}
