package com.example.cropcheck.services;

import android.provider.ContactsContract;

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

    @POST("register")
    @FormUrlEncoded
    Call<Authorization> register(@Field("name") String name, @Field("email") String email, @Field("national_id") String national_id,
                                 @Field("phone") String phone, @Field("password") String password,
                                 @Field("password_confirmation") String password_confirmation);
}
