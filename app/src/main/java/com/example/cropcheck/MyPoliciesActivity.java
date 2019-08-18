package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.cropcheck.adapters.PolicyAdapter;
import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.PolicyService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPoliciesActivity extends AppCompatActivity {

    PolicyAdapter myAdapter;
    Integer user_id;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_policies);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new PolicyAdapter(getApplicationContext());

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

    public String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }

    private void loadPolicies() {
        Call<List<Policy>> policies = CoreUtils.getAuthRetrofitClient(getToken()).create(PolicyService.class).getAllPolicies();
        policies.enqueue(new Callback<List<Policy>>() {
            @Override
            public void onResponse(Call<List<Policy>> call, final Response<List<Policy>> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataReceived(response.body());
                        }
                    });
                } else
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Policy>> call, Throwable t) {

            }
        });
    }

    private void dataReceived(List<Policy> policies) {
        myAdapter.updateData(policies);
    }
}
