package com.example.cropcheck.services;

import com.example.cropcheck.models.Season;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SeasonService {



    @GET("is_on_season/{site_id}")
    Call<Integer> isOnSeason(@Path("site_id") int site_id);

    @GET("is_on_season/{site_id}")
    Call<Season> getSeason(@Path("site_id") int site_id);

    @POST("end_season/{season_id}")
    Call<Season> endSeason(@Path("season_id") int season_id);

    @POST("new_season/{site_id}")
    Call<Season> startSeason(@Path("site_id") int site_id);
}
