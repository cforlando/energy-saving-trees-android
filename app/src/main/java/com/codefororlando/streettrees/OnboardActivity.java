package com.codefororlando.streettrees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OnboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        //HACK, 
        Intent intent = new Intent(this, RequestTreeActivity.class);
        startActivity(intent);

    }
}
