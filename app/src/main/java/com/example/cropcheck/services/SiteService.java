package com.example.cropcheck.services;

import com.example.cropcheck.models.Site;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SiteService {

    @POST("add_site")
    @FormUrlEncoded
    Call<Site> addSite(@Field("site_name") String site_name, @Field("size") String size,
                       @Field("county") String county, @Field("division") String division,
                       @Field("village") String village);

    @GET("get_sites")
    Call<List<Site>> getAllSites();
}
