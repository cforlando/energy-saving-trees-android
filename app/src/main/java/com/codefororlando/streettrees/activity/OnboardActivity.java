package com.codefororlando.streettrees.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codefororlando.streettrees.R;

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
