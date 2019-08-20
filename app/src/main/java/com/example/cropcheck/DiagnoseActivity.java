package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.cropcheck.adapters.DiagnosticsAdapter;
import com.example.cropcheck.models.Diagnostic;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.DiagnosticService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnoseActivity extends AppCompatActivity {

    Integer season_id;
    DiagnosticsAdapter myAdapter;
    Integer user_id;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new DiagnosticsAdapter(getApplicationContext());

        recyclerView.setAdapter(myAdapter);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                season_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                season_id = extras.getInt("site_id");
                Toast.makeText(getApplicationContext(), ""+season_id , Toast.LENGTH_SHORT).show();
            }
        } else {
            season_id = (Integer) savedInstanceState.getSerializable("site_id");
        }

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    loadDiagnostics();
                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void loadDiagnostics(){
        Call<List<Diagnostic>> diagnostic = CoreUtils.getAuthRetrofitClient(getToken()).create(DiagnosticService.class).getSeasonDiagnostics(season_id);
        diagnostic.enqueue(new Callback<List<Diagnostic>>() {
            @Override
            public void onResponse(Call<List<Diagnostic>> call, Response<List<Diagnostic>> response) {
                dataReceived(response.body());
            }

            @Override
            public void onFailure(Call<List<Diagnostic>> call, Throwable t) {

            }
        });
    }

    public String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }

    private void dataReceived(List<Diagnostic> diagnostics) {
        myAdapter.updateData(diagnostics);
    }
}
