package com.example.cropcheck.services;

import com.example.cropcheck.models.Cover;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoverService {

    @GET("get_all_covers")
    Call<List<Cover>> getAllCovers();

    @GET("get_farm_covers/{site_id}")
    Call<List<Cover>> getSiteCovers(@Path("site_id") int site_id);

    @GET("get_policy/{policy_id}")
    Call<Cover> getCover(@Path("policy_id") int policy_id);

}
