package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.adapters.RiskAdapter;
import com.example.cropcheck.models.Claim;
import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.Risk;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.ClaimService;
import com.example.cropcheck.services.PolicyService;
import com.example.cropcheck.services.RiskService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedPoliciesActivity extends AppCompatActivity {


    Integer policy_id;
    Integer user_id;
    Integer cover_id;
    Integer status;
    String expiry_date;
    String title;
    String terms;
    String premium;
    String duration;

    TextView premium_amount;
    TextView policy_duration;
    TextView policy_title;
    TextView expiry;
    Integer site_id;

    Button claimButton;

    RiskAdapter myAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_policies);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                cover_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null", Toast.LENGTH_SHORT).show();
            } else {
                cover_id = extras.getInt("id");
                policy_id = extras.getInt("policy_id");
                user_id = extras.getInt("user_id");
                status = extras.getInt("status");
                expiry_date = extras.getString("expiry_date");
            }
        } else {
            policy_id = (Integer) savedInstanceState.getSerializable("id");
        }

        expiry = findViewById(R.id.policy_expiry);
        expiry.setText(expiry_date);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new RiskAdapter(getApplicationContext());

        recyclerView.setAdapter(myAdapter);

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user_id = response.body().getId();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadRisks(policy_id);
                            loadPolicy(policy_id);
                        }
                    });

                } else
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        claimButton = findViewById(R.id.claimButton);

        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Claim> call = CoreUtils.getAuthRetrofitClient(getToken()).create(ClaimService.class).makeClaim(cover_id);
                call.enqueue(new Callback<Claim>() {

                    @Override
                    public void onResponse(Call<Claim> call, Response<Claim> response) {
                        if (response.isSuccessful()) {
                            user_id = response.body().getId();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else
                            Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Claim> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void loadRisks(Integer policy_id) {
        Call<List<Risk>> risks = CoreUtils.getAuthRetrofitClient(getToken()).create(RiskService.class).getPolicyRisks(policy_id);
        risks.enqueue(new Callback<List<Risk>>() {
            @Override
            public void onResponse(Call<List<Risk>> call, final Response<List<Risk>> response) {
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
            public void onFailure(Call<List<Risk>> call, Throwable t) {

            }
        });
    }

    private void loadPolicy(Integer policy_id) {
        Call<Policy> policy = CoreUtils.getAuthRetrofitClient(getToken()).create(PolicyService.class).getPolicy(policy_id);
        policy.enqueue(new Callback<Policy>() {
            @Override
            public void onResponse(Call<Policy> call, Response<Policy> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            premium_amount = findViewById(R.id.premium_amount);
                            policy_duration = findViewById(R.id.policy_duration);
                            policy_title = findViewById(R.id.policy_title);

                            premium = "Ksh. " + premium;
                            duration = duration + " months";

                            premium_amount.setText(premium);
                            policy_duration.setText(duration);
                            policy_title.setText(title);

                        }
                    });
                } else
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Policy> call, Throwable t) {

            }
        });
    }

    private void dataReceived(List<Risk> risks) {
        myAdapter.updateData(risks);
    }

    public String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
