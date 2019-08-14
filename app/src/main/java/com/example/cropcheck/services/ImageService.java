package com.example.cropcheck.services;

import com.example.cropcheck.models.Image;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageService {
    @Multipart
    @POST("add_farm_image")
    Call<Image> uploadImage(@Part MultipartBody.Part file, @Part("season_id") RequestBody season_id, @Part("farm_id") RequestBody farm_id);
}
