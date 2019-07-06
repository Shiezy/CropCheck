package com.example.cropcheck.models;

import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("site_name")
    String site_name;
    @SerializedName("size")
    String size;
    @SerializedName("county")
    String county;
    @SerializedName("division")
    String division;
    @SerializedName("village")
    String village;
}
