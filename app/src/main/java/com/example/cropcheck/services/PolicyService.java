package com.example.cropcheck.services;

import com.example.cropcheck.models.Policy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PolicyService {

//    @POST("add_farm")
//    @FormUrlEncoded
//    Call<Site> addSite(@Field("site_name") String site_name, @Field("size") String size,
//                       @Field("county") String county, @Field("division") String division,
//                       @Field("village") String village,  @Field("user_id") int user_id);


    @GET("get_all_policies")
    Call<List<Policy>> getAllPolicies();

    @GET("get_farm_policies/{site_id}")
    Call<List<Policy>> getSitePolicies(@Path("site_id") int site_id);

    @GET("get_policy/{policy_id}")
    Call<Policy> getPolicy(@Path("policy_id") int policy_id);

}
