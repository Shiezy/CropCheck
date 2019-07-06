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
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView username, natid;
    RecyclerView recyclerView;
    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<RetroPhoto>> call = service.getAllPhotos();
//        call.enqueue(new Callback<List<RetroPhoto>>() {
//            @Override
//            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });

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


        username = findViewById(R.id.username);
        natid = findViewById(R.id.natid);

        //import fonts
        Typeface Mlight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MR.ttf");

        //customize fonts
        username.setTypeface(MMedium);
        natid.setTypeface(Mlight);

        recyclerView = findViewById(R.id.my_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
////        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
        String[] myDataset= new String[7];
        myDataset[0] = "Cat";
        myDataset[1] = "Dog";
        myDataset[2] = "Elephant";
        myDataset[3] = "Losing";
        myDataset[4] = "My";
        myDataset[5] = "Fucking";
        myDataset[6] = "Mind";
        recyclerView.setAdapter(new MyAdapter(myDataset));

//        Call<List<Site>> sites = CoreUtils.getAuthRetrofitClient(getToken()).create(SiteService.class).getAllSites();
//        sites.enqueue(new Callback<List<Site>>() {
//            @Override
//            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Site>> call, Throwable t) {
//
//            }
//        });

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    int id =response.body().getId();
                    String userName = response.body().getName();
                    username.setText(userName);
                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void generateDataList(List<RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
//        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CustomAdapter(this,photoList));
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(this).getString("ACCESS_TOKEN", null);
    }
}
