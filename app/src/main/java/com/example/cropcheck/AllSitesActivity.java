package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.models.Site;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.SiteService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSitesActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    Integer user_id;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sites);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(getApplicationContext());

        recyclerView.setAdapter(myAdapter);

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user_id = response.body().getId();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadSites();
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
}
