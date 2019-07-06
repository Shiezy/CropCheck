package com.example.cropcheck;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cropcheck.models.Authorization;
import com.example.cropcheck.services.AuthService;
import com.example.cropcheck.utils.CoreUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn = findViewById(R.id.registerbtn);
        final EditText name = (EditText)findViewById(R.id.nameId);
        final EditText phone = (EditText)findViewById(R.id.phoneId);
        final EditText email = (EditText)findViewById(R.id.emailId);
        final EditText national_id = (EditText)findViewById(R.id.nationalId);
        final EditText password = (EditText)findViewById(R.id.passwordId);
        final EditText password_confirmation = (EditText)findViewById(R.id.password_confirmationId);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Authorization> call = CoreUtils.getRetrofitClient().create(AuthService.class).
                        register(name.getText().toString(),
                                email.getText().toString(),
                                national_id.getText().toString(), phone.getText().toString(),
                                password.getText().toString(), password_confirmation.getText().toString());
                call.enqueue(new Callback<Authorization>() {
                    @Override
                    public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ACCESS_TOKEN", response.body().access_token);
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        Log.e("whhh", "SUCCESS");
                    }

                    @Override
                    public void onFailure(Call<Authorization> call, Throwable t) {
                        Log.e("whhh", t.getMessage());
                    }
                });
            }
        });
    }
}
