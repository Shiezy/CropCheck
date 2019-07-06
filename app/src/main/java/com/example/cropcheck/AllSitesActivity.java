package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.cropcheck.models.Site;
import com.example.cropcheck.services.SiteService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSitesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sites);

//        EditText name = vnv;

        Call<List<Site>> sites = CoreUtils.getAuthRetrofitClient(getToken()).create(SiteService.class).getAllSites();
        sites.enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {

            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {

            }
        });
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(this).getString("ACCESS_TOKEN", null);
    }
}
