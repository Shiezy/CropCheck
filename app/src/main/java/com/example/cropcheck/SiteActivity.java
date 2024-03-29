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

import com.example.cropcheck.models.Season;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.SeasonService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteActivity extends AppCompatActivity {

    Integer site_id;
    String village;
    String county;
    String division;
    String site_name;
    Integer user_id;
    Integer season_id;

    Button btnPolicy;
    Button coversButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        final Button btnSetSeason = findViewById(R.id.btnSeason);
        final Button diagnosis = findViewById(R.id.diagnosis);
        final TextView siteDetailsCounty = findViewById(R.id.site_dets_county);
        final TextView siteDetailsDivision = findViewById(R.id.site_dets_division);
        final TextView siteDetailsVillage = findViewById(R.id.site_dets_village);
        final TextView siteDetailsName = findViewById(R.id.site_dets_name);
        final Button siteImages = findViewById(R.id.images);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                site_id = null;
                village = null;
                county = null;
                division = null;
                site_name = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                site_id = extras.getInt("id");
                village = extras.getString("village");
                division = extras.getString("division");
                county = extras.getString("county");
                site_name = extras.getString("site_name");
//                Toast.makeText(getApplicationContext(), ""+site_id , Toast.LENGTH_SHORT).show();
            }
        } else {
            site_id = (Integer) savedInstanceState.getSerializable("id");
            site_name = (String) savedInstanceState.getSerializable("site_name");
        }


        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user_id = response.body().getId();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            siteDetailsCounty.setText(county);
                            siteDetailsDivision.setText(division);
                            siteDetailsVillage.setText(village);
                            siteDetailsName.setText(site_name);
                            loadSeasonDets();

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

        btnPolicy = findViewById(R.id.btnPolicy);
        btnPolicy.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SiteActivity.this, PoliciesActivity.class);
                myIntent.putExtra("site_id",site_id);
                startActivity(myIntent);
            }
        }));

        diagnosis.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SiteActivity.this, DiagnoseActivity.class);
                myIntent.putExtra("season_id",season_id);
                startActivity(myIntent);
            }
        }));



        coversButton = findViewById(R.id.coversButton);
        coversButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SiteActivity.this, MyPoliciesActivity.class);
                myIntent.putExtra("site_id",site_id);
                startActivity(myIntent);
            }
        }));

    }
    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }

    private void loadSeasonDets(){
        final TextView season_dets = (TextView) findViewById(R.id.season_dets);
        final TextView season_start_date = (TextView) findViewById(R.id.season_start_date);
        final TextView season_end_date = (TextView) findViewById(R.id.season_end_date);
        final Button btnSetSeason = (Button) findViewById(R.id.btnSeason);

        Call<Integer> isOnSeason = CoreUtils.getAuthRetrofitClient(getToken()).create(SeasonService.class).isOnSeason(site_id);
           isOnSeason.enqueue(new Callback<Integer>() {
               @Override
               public void onResponse(Call<Integer> call,final Response<Integer> response) {

                   if (response.isSuccessful()) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               int checker = response.body();
                               if(checker == 0) {
                                   season_dets.setText("Not in Season");
                                   btnSetSeason.setText("Start Season");
                                   season_start_date.setVisibility(View.GONE);
                               } else if (checker == 1){

                                   Call<Season> season = CoreUtils.getAuthRetrofitClient(getToken()).create(SeasonService.class).getSeason(site_id);
                                   season.enqueue(new Callback<Season>() {
                                       @Override
                                       public void onResponse(Call<Season> call, final Response<Season> response) {
                                           if(response.isSuccessful()){
                                               runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       int status = response.body().getStatus();
                                                       String startDate = response.body().getStart_date();
                                                       String endDate = response.body().getEnd_date();
                                                       season_id = response.body().getId();
                                                       season_dets.setText("Has been in season since");
                                                       season_start_date.setText(startDate);
                                                       season_end_date.setText(endDate);
                                                       btnSetSeason.setText("End Season");

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
                           }
                       });
                   }


               }

               @Override
               public void onFailure(Call<Integer> call, Throwable t) {

               }
           });



    }
//    private void dataReceived(List<Policy> policies) {
//        adapter.updateData(policies);}

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
