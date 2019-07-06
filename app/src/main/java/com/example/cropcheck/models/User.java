package com.example.cropcheck.models;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private
    int id;
    @SerializedName("name")
    private
    String name;
    @SerializedName("email")
    private
    String email;
    @SerializedName("phone")
    private
    String phone;
    @SerializedName("national_id")
    private
    String national_id;
    @SerializedName("password")
    private
    String password;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNational_id() {
        return national_id;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
