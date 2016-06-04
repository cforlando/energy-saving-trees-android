package com.codefororlando.streettrees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codefororlando.streettrees.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    final String TAG = "DETAILACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {
            DetailFragment frag = DetailFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer, frag, DetailFragment.TAG)
                    .commit();
        }
    }
}
