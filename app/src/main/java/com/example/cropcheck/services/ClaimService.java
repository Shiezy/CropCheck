package com.example.cropcheck.services;

import com.example.cropcheck.models.Claim;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClaimService {

    @GET("get_all_claims")
    Call<List<Claim>> getAllCovers();

    @GET("get_claim/{claim_id}")
    Call<Claim> getClaim(@Path("claim_id") int claim_id);

    @POST("make_claim")
    @FormUrlEncoded
    Call<Claim> makeClaim(@Field("cover_id") Integer cover_id);
}
