package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cropcheck.models.Diagnostic;
import com.example.cropcheck.services.DiagnosticService;
import com.example.cropcheck.utils.CoreUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnoseActivity extends AppCompatActivity {

    Integer season_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

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
    }

    Call<Diagnostic> diagnosticCall = CoreUtils.getAuthRetrofitClient(getToken()).create(DiagnosticService.class).getSeasonDiagnostics(season_id);
        diagnosticCall.enqueue(new Callback<Diagnostic>() {
        @Override
        public void onResponse(Call<Diagnostic> call, final Response<Diagnostic> response) {
            if (response.isSuccessful()) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dataReceived(response.body());
//                    }
//                });
            } else
                Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Diagnostic> call, Throwable t) {

        }
    });

    public String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
