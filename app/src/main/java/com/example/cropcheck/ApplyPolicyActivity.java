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
import com.example.cropcheck.models.PolicyApplication;
import com.example.cropcheck.models.Risk;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.PolicyApplicationService;
import com.example.cropcheck.services.RiskService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyPolicyActivity extends AppCompatActivity {

    Integer policy_id;
    Integer user_id;
    String title;
    String terms;
    String premium;
    String duration;

    TextView premium_amount;
    TextView policy_duration;
    TextView policy_title;

    Button applyPolicyButton;

    RiskAdapter myAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_policy);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                policy_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null", Toast.LENGTH_SHORT).show();
            } else {
                policy_id = extras.getInt("id");
                premium = extras.getString("premium");
                title = extras.getString("title");
                terms = extras.getString("terms");
                duration = extras.getString("duration");
            }
        } else {
            policy_id = (Integer) savedInstanceState.getSerializable("id");
        }

        premium_amount = findViewById(R.id.premium_amount);
        policy_duration = findViewById(R.id.policy_duration);
        policy_title = findViewById(R.id.policy_title);

        premium = "Ksh. " + premium;
        duration = duration + " months";

        premium_amount.setText(premium);
        policy_duration.setText(duration);
        policy_title.setText(title);

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
                        }
                    });

                } else
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        applyPolicyButton = findViewById(R.id.applyButton);

        applyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PolicyApplication> call = CoreUtils.getAuthRetrofitClient(getToken()).create(PolicyApplicationService.class).addPolicyApplication(user_id, policy_id);
                call.enqueue(new Callback<PolicyApplication>() {

                    @Override
                    public void onResponse(Call<PolicyApplication> call, Response<PolicyApplication> response) {
                        if (response.isSuccessful()) {
//                            startActivity(new Intent(AddSiteActivity.this, AllSitesActivity.class));
                            Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<PolicyApplication> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "failed!!", Toast.LENGTH_SHORT).show();
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

    private void dataReceived(List<Risk> risks) {
        myAdapter.updateData(risks);
    }

    public String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
