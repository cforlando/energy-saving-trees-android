package com.codefororlando.streettrees.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.providers.TreeProvider;
import com.codefororlando.streettrees.presenter.MapPresenter;
import com.codefororlando.streettrees.view.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraChangeListener {

    private static final String TAG = "MAINACTIVITY";
    private GoogleMap map;
    private LocationManager locationManager;

    public static final int REQUEST_ACCESS_FINE_LOCATION = 10001;
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_TREETYPE = "type";

    public static final int DEFAULT_MARKER_LIMIT = 20;

    FloatingActionButton fab;

    List<Tree> cachedTrees;

    TreeProvider treeProvider;
    MapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_map);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RequestTreeActivity.class);
                startActivity(intent);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        treeProvider = new TreeProvider(this);
        presenter = new MapPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(treeProvider, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setOnCameraChangeListener(this);

        String locationProvider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"},REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        Log.d(TAG,"Setting Map Location");
        Location myLocation = locationManager.getLastKnownLocation(locationProvider);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));

        presenter.fetchTrees();
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        updateMapWithTrees(cachedTrees);
    }

    @Override
    public void updateMapWithTrees(List<Tree> trees) {
        if(trees == null) {
            return;
        }

        VisibleRegion vr = map.getProjection().getVisibleRegion();
        List<Tree> visibleTrees = presenter.getVisibleTrees(vr, trees, DEFAULT_MARKER_LIMIT);
        for (Tree tree : visibleTrees) {
            map.addMarker(new MarkerOptions().position(tree.getLocation()).title(tree.getTreeName()));
        }
        cachedTrees = trees;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            onMapReady(map);
        }
    }
}
