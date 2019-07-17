package com.example.cropcheck.services;

import com.example.cropcheck.models.Season;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeasonService {



    @GET("is_on_season/{site_id}")
    Call<Season> isOnSeason(@Path("site_id") int site_id);
}
