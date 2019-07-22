package com.example.cropcheck.services;

import com.example.cropcheck.models.PolicyApplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PolicyApplicationService {

    @POST("policy_application")
    @FormUrlEncoded
    Call<PolicyApplication> addPolicyApplication(@Field("user_id") Integer user_id, @Field("policy_id") Integer policy_id);

}
