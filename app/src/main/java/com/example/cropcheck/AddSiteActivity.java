package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cropcheck.models.User;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
         user.enqueue(new Callback<User>() {
             @Override
             public void onResponse(Call<User> call, Response<User> response) {

             }

             @Override
             public void onFailure(Call<User> call, Throwable t) {

             }
         });
    }
    public String getToken(){
        return  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
