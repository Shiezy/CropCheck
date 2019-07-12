package com.example.cropcheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.models.Site;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.SiteService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import adapter.CustomAdapter;
import model.RetroPhoto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView username, natid;
    RecyclerView recyclerView;
    ProgressDialog progressDoalog;

    Integer user_id;
    String user_name;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getToken() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        natid = findViewById(R.id.natid);

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user_id = response.body().getId();
                    user_name = response.body().getName();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            username.setText(user_name);
                            loadSites();
                        }
                    });

                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        Button btn = (Button)findViewById(R.id.diagnose);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Button siteBtn = (Button)findViewById(R.id.sites);

        siteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddSiteActivity.class));
            }
        });

        //import fonts
        Typeface Mlight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MR.ttf");

        //customize fonts
        username.setTypeface(MMedium);
        natid.setTypeface(Mlight);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(getApplicationContext());

        recyclerView.setAdapter(myAdapter);



    }

    private void loadSites() {
        Call<List<Site>> sites = CoreUtils.getAuthRetrofitClient(getToken()).create(SiteService.class).getAllSites(user_id);
        sites.enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, final Response<List<Site>> response) {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataReceived(response.body());
                        }
                    });
                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {

            }
        });
    }

    private void dataReceived(List<Site> sites) {
        myAdapter.updateData(sites);
    }

    private void generateDataList(List<RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
//        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CustomAdapter(this,photoList));
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
