package com.codefororlando.streettrees;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codefororlando.streettrees.api.models.Tree;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap map;
    private LocationManager locationManager;

    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_TREETYPE = "type";

    //TEMP DATA
    private Tree[] treeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);

        //TODO: Use actual tree data.
        Tree treeA = new Tree(new SimpleDateFormat().parse("2015-11-13T23:37:00.000",new ParsePosition(0)),28.562917, -81.350608,"Nuttall Oak");
        Tree treeB = new Tree(new SimpleDateFormat().parse("2015-11-13T21:46:00.000",new ParsePosition(0)),28.499321, -81.479695,"Tuliptree");

        treeData = new Tree[]{treeA,treeB};

        addMarkersToMap(treeData);

    }

    private void addMarkersToMap(Tree[] trees){
        // Add a marker in Sydney and move the camera
        for(Tree tree: trees){
            map.addMarker(new MarkerOptions().position(tree.getLocation()).title(tree.getTreeName()));
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(28.5,-81)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        String treeTame = marker.getTitle();
        LatLng location = marker.getPosition();
        detailIntent.putExtra(EXTRA_LOCATION,location);
        detailIntent.putExtra(EXTRA_TREETYPE,treeTame);
        startActivity(detailIntent);
    }
}
