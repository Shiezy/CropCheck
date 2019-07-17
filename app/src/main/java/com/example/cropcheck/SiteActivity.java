package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.adapters.PolicyAdapter;
import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.Season;
import com.example.cropcheck.models.Site;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.SeasonService;
import com.example.cropcheck.services.SiteService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteActivity extends AppCompatActivity {

    Integer site_id;
    Integer user_id;
    PolicyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        final TextView season_dets = (TextView) findViewById(R.id.season_dets);
        final TextView season_start_date = (TextView) findViewById(R.id.season_start_date);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                site_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                site_id = extras.getInt("id");
//                Toast.makeText(getApplicationContext(), ""+site_id , Toast.LENGTH_SHORT).show();
            }
        } else {
            site_id = (Integer) savedInstanceState.getSerializable("id");
        }

        recyclerView = findViewById(R.id.site_policy_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new PolicyAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);


        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user_id = response.body().getId();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadSeasonDets();
                            loadPolicies();

                        }
                    });

                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }
    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }

    private void loadPolicies() {
        Call<List<Policy>> policies = CoreUtils.getAuthRetrofitClient(getToken()).create(SiteService.class).getSitePolicies(site_id);
        policies.enqueue(new Callback<List<Policy>>() {
            @Override
            public void onResponse(Call<List<Policy>> call, final Response<List<Policy>> response) {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();

                            dataReceived(response.body());
                        }
                    });
                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Policy>> call, Throwable t) {

            }
        });
    }
    private void loadSeasonDets(){
        final TextView season_dets = (TextView) findViewById(R.id.season_dets);
        final TextView season_start_date = (TextView) findViewById(R.id.season_start_date);
        Toast.makeText(getApplicationContext(), ""+site_id , Toast.LENGTH_SHORT).show();


        Call<Season> season = CoreUtils.getAuthRetrofitClient(getToken()).create(SeasonService.class).isOnSeason(site_id);
        season.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, final Response<Season> response) {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int status = response.body().getStatus();
                            String startDate = response.body().getStart_date();

                            if(status == 1){
                                season_dets.setText("Has been in season since");
                                season_start_date.setText(startDate);
                            } else {
                                season_dets.setText("Not in Season");
                                season_start_date.setVisibility(View.GONE);
                            }



                        }
                    });



                }
                else  {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            season_dets.setText("monica");

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Season> call, final Throwable t) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.d("TEMP_TAG", t.getMessage());

                        season_dets.setText("magda");

                    }
                });
            }
        });
    }
    private void dataReceived(List<Policy> policies) {
        adapter.updateData(policies);}

}
