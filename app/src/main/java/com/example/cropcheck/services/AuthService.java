package com.example.cropcheck.services;

import com.example.cropcheck.models.Authorization;
import com.example.cropcheck.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {

    @POST("login")
    @FormUrlEncoded
    Call<Authorization> login(@Field("phone") String phone, @Field("password") String password);
}
