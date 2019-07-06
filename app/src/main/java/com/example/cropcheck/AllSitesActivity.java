package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sites);

        textView4 = findViewById(R.id.textView4);

        final Call<User> user = CoreUtils.getAuthRetrofitClient(getToken()).create(UserService.class).user();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    int id =response.body().getId();
                    String userName = response.body().getName();
                    textView4.setText(userName);
                }else   Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(this).getString("ACCESS_TOKEN", null);
    }
}
