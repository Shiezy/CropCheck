package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Diagnostic {
    @SerializedName("id")
    private
    int id;
    @SerializedName("season_id")
    private Integer season_id;
    @SerializedName("no_pictures")
    private Integer no_pictures;
    @SerializedName("no_infected")
    private Integer no_infected;
    @SerializedName("recommendation")
    private String recommendation;
    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNo_infected() {
        return no_infected;
    }

    public Integer getNo_pictures() {
        return no_pictures;
    }

    public Integer getSeason_id() {
        return season_id;
    }

    public String getDescription() {
        return description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNo_infected(Integer no_infected) {
        this.no_infected = no_infected;
    }

    public void setNo_pictures(Integer no_pictures) {
        this.no_pictures = no_pictures;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setSeason_id(Integer season_id) {
        this.season_id = season_id;
    }
}
