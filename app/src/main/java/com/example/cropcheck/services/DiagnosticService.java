package com.example.cropcheck.services;

import com.example.cropcheck.models.Cover;
import com.example.cropcheck.models.Diagnostic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiagnosticService {

    @GET("get_all_diagnostic")
    Call<List<Diagnostic>> getAllDiagnostics();

    @GET("get_season_diagnostic")
    Call<List<Diagnostic>> getSeasonDiagnostics(@Path("season_id") int season_id);

    @GET("diagnose/farm_id/season_id")
    Call<Cover> getDiagnostics(@Path("farm_id") int farm_id, @Path("season_id") int season_id);

}
