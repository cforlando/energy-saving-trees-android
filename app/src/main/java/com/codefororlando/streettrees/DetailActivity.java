package com.codefororlando.streettrees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.fragments.DetailFragment;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;

public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailListener {

    final String TAG = "DETAILACTIVITY";

    private TreeDescription treeDescription;
    private LatLng location;
    private String treeType;

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

        Intent loadingIntent = getIntent();
        location = loadingIntent.getParcelableExtra(MainActivity.EXTRA_LOCATION);
        treeType = loadingIntent.getStringExtra(MainActivity.EXTRA_TREETYPE);
    }

    private void loadTreeData(LatLng _location, String _treeType){

    }

    @Override
    public TreeDescription getTreeData() {
        try {
            TreeDescriptionProvider treeDescriptionProvider = TreeDescriptionProvider.getInstance(getApplicationContext());
            treeDescription = treeDescriptionProvider.getTreeDescription(treeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeDescription;
    }
}
