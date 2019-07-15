package com.example.cropcheck.services;

import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.Site;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SiteService {

    @POST("add_farm")
    @FormUrlEncoded
    Call<Site> addSite(@Field("site_name") String site_name, @Field("size") String size,
                       @Field("county") String county, @Field("division") String division,
                       @Field("village") String village,  @Field("user_id") int user_id);


    @GET("get_all_farms/{user_id}")
    Call<List<Site>> getAllSites(@Path("user_id") int user_id);

    @GET("get_farm_policies/{site_id}")
    Call<List<Policy>> getSitePolicies(@Path("site_id") int site_id);
}
