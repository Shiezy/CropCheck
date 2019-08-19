package com.example.cropcheck.services;

import com.example.cropcheck.models.Image;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ImageService {
    @Multipart
    @POST("add_farm_image")
    Call<Image> uploadImage(@Part MultipartBody.Part file, @Part("season_id") RequestBody season_id, @Part("farm_id") RequestBody farm_id);


    @GET("farm_images/{farm_id}/{season_id}")
    Call<List<Image>> getImage(@Path("farm_id") int farm_id, @Path("season_id") int season_id);
}
