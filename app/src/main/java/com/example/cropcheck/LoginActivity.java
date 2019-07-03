package com.example.cropcheck;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String registerString = "Don't have account? Sign Up.";

        SpannableString myString = new SpannableString(registerString);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        };

        //For Click
        myString.setSpan(clickableSpan,registerString.indexOf("S"),registerString.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //For UnderLine
//        myString.setSpan(new UnderlineSpan(),startIndex,lastIndex,0);

        //For Bold
        myString.setSpan(new StyleSpan(Typeface.BOLD),registerString.indexOf("?"),registerString.indexOf("."),0);

        //Finally you can set to textView.

        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(myString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        Button btn = (Button)findViewById(R.id.loginbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AddSiteActivity.class));
            }
        });
    }
}
