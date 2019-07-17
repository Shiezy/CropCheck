package com.example.cropcheck;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cropcheck.models.Policy;

import java.io.Serializable;

public class ApplyPolicyActivity extends AppCompatActivity {

    Integer policy_id;
    String title;
    String terms;
    String premium;
    Class<? extends Serializable> risk;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_policy);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Serializable serializableExtra = getIntent().getSerializableExtra("risk");

            if(serializableExtra == null){
                risk = null;
                Toast.makeText(getApplicationContext(), "Risk Object Is Null" , Toast.LENGTH_SHORT).show();
            } else {
                risk = serializableExtra.getClass();
                Toast.makeText(getApplicationContext(), ""+risk , Toast.LENGTH_SHORT).show();
            }

            if(extras == null) {
                policy_id = null;
                Toast.makeText(getApplicationContext(), "SITE ID is null" , Toast.LENGTH_SHORT).show();
            } else {
                policy_id = extras.getInt("id");
                premium = extras.getString("premium");
                title = extras.getString("title");
                terms = extras.getString("terms");
            }
        } else {
            policy_id = (Integer) savedInstanceState.getSerializable("id");
        }
    }
}
