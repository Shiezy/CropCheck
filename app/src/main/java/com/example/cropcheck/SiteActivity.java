package com.example.cropcheck;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    String village;
    String county;
    String site_name;
    Integer user_id;
    PolicyAdapter adapter;
    Integer season_id;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        final Button btnSetSeason = (Button) findViewById(R.id.btnSeason);
        final TextView siteDetails = (TextView) findViewById(R.id.site_dets);
        final Button siteImages = (Button) findViewById(R.id.images);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                site_id = null;
                village = null;
                county = null;
                site_name = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                site_id = extras.getInt("id");
                village = extras.getString("village");
                county = extras.getString("county");
                site_name = extras.getString("site_name");
//                Toast.makeText(getApplicationContext(), ""+site_id , Toast.LENGTH_SHORT).show();
            }
        } else {
            site_id = (Integer) savedInstanceState.getSerializable("id");
            site_name = (String) savedInstanceState.getSerializable("site_name");
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
                            siteDetails.setText(site_name + " " + village +" "+county);
                            loadSeasonDets();
                            loadPolicies();

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnSetSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String buttonText = b.getText().toString();
                if(buttonText == "End Season"){
                   endSeason();
                } else {
                    startSeason();
                }
            }
        });

        siteImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteActivity.this, ImageActivity.class);
                Bundle b = new Bundle();
                b.putInt("farm_id", site_id); //Your id
                b.putInt("season_id", season_id); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
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
                            dataReceived(response.body());
                        }
                    });
                }
//                else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Policy>> call, Throwable t) {

            }
        });
    }
    private void loadSeasonDets(){
        final TextView season_dets = (TextView) findViewById(R.id.season_dets);
        final TextView season_start_date = (TextView) findViewById(R.id.season_start_date);
        final Button btnSetSeason = (Button) findViewById(R.id.btnSeason);


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
                            season_id = response.body().getId();
//                            season_id = 2;

                            if(status == 1){
                                season_dets.setText("Has been in season since");
                                season_start_date.setText(startDate);
                                btnSetSeason.setText("End Season");

                            } else {
                                season_dets.setText("Not in Season");
                                btnSetSeason.setText("Start Season");
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

    private void endSeason(){
        Call<Season> seasonCall = CoreUtils.getAuthRetrofitClient(getToken()).create(SeasonService.class).endSeason(season_id);
        seasonCall.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"successfully ended",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void startSeason(){
        Toast.makeText(getApplicationContext(),"let's try to start",Toast.LENGTH_SHORT).show();
        Call<Season> seasonCall = CoreUtils.getAuthRetrofitClient(getToken()).create(SeasonService.class).startSeason(site_id);
        seasonCall.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"success start",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Failed to start",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
