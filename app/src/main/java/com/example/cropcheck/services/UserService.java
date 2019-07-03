package com.example.cropcheck.services;

import com.example.cropcheck.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("user")
    Call<User> user();
}
