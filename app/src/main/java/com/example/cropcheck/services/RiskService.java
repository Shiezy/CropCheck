package com.example.cropcheck.services;

import com.example.cropcheck.models.Risk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiskService {

    @GET("get_policy_risks/{policy_id}")
    Call<List<Risk>> getPolicyRisks(@Path("policy_id") int policy_id);

}
