package com.example.cropcheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.models.User;
import com.example.cropcheck.services.UserService;
import com.example.cropcheck.utils.CoreUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView username, natid;
    ProgressDialog progressDoalog;

    Integer user_id;
    String user_name;

    Button sites_button;
    Button add_site_button;


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

        sites_button = (Button)findViewById(R.id.sites_button);

        sites_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllSitesActivity.class));
            }
        });

        add_site_button = findViewById(R.id.add_site_button);

        add_site_button.setOnClickListener(new View.OnClickListener() {
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
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }
}
