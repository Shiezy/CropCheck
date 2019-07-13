package com.example.cropcheck;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cropcheck.models.Site;
import com.example.cropcheck.models.User;
import com.example.cropcheck.services.SiteService;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
         user.enqueue(new Callback<User>() {
             @Override
             public void onResponse(Call<User> call, Response<User> response) {

             }

             @Override
             public void onFailure(Call<User> call, Throwable t) {

             }
         });

        final EditText site_name = findViewById(R.id.site_name);
        final EditText size = findViewById(R.id.size);
        final EditText village = findViewById(R.id.village);
        final EditText division = findViewById(R.id.division);
        final EditText county = findViewById(R.id.county);
        Button btn_add_site = (Button) findViewById(R.id.btn_add_site);

        btn_add_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Site> call = CoreUtils.getAuthRetrofitClient(getToken()).create(SiteService.class).addSite(site_name.getText().toString(),
                        size.getText().toString(), county.getText().toString(), division.getText().toString(), village.getText().toString(),9);
                Toast.makeText(getApplicationContext(),site_name.getText().toString(), Toast.LENGTH_SHORT).show();
                call.enqueue(new Callback<Site>() {

                    @Override
                    public void onResponse(Call<Site> call, Response<Site> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AddSiteActivity.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddSiteActivity.this, AllSitesActivity.class));

                        }else{
                            Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Site> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"failed!!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddSiteActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public String getToken(){
        return  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
