package com.example.cropcheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ApplyPolicyActivity extends AppCompatActivity {

    Integer policy_id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_policy);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                policy_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                policy_id = extras.getInt("id");
                Toast.makeText(getApplicationContext(), ""+policy_id , Toast.LENGTH_SHORT).show();
            }
        } else {
            policy_id = (Integer) savedInstanceState.getSerializable("id");
        }
    }
}
