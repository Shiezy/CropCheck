package com.example.cropcheck.models;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("national_id")
    String national_id;
    @SerializedName("password")
    String password;

}
