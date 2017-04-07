package com.codefororlando.streettrees.activity;

import android.annotation.TargetApi;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.TreeApplication;
import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.providers.TreeProvider;
import com.codefororlando.streettrees.requesttree.RequestTreeActivity;
import com.codefororlando.streettrees.treemap.MapPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.util.List;
import java.io.IOException;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MapPresenter.MapView,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraChangeListener {

    private GoogleMap map;
    private LocationManager locationManager;

    private static final int FINE_LOCATION_REQUEST_CODE = 103;   //random number
    private static final int DEFAULT_ZOOM_LEVEL = 10;
    private static final int DEFAULT_MARKER_LIMIT = 20;
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_TREETYPE = "type";
    String locationProvider;

    FloatingActionButton fab;

    List<Tree> cachedTrees;

    @Inject
    TreeProvider treeProvider;

    MapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_map);

        ((TreeApplication) getApplication()).getTreeProviderComponent().inject(this);

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

        locationProvider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
            return;
        }
        centerMapToUser();
        loadAndSetGeoData();
        presenter.fetchTrees();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        String treeName = marker.getTitle();
        LatLng location = marker.getPosition();
        detailIntent.putExtra(EXTRA_LOCATION, location);
        detailIntent.putExtra(EXTRA_TREETYPE, treeName);
        startActivity(detailIntent);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        updateMapWithTrees(cachedTrees);
    }

    @Override
    public void updateMapWithTrees(List<Tree> trees) {
        if (trees == null) {
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(map);
                    centerMapToUser();
                } else {
                    // permission denied, boo! Disable the
                }
                return;
            }
        }
    }

    private void loadAndSetGeoData() {
        try {
            GeoJsonLayer layer = new GeoJsonLayer(map, R.raw.orlando_city_limits, getApplicationContext());
            int green = getResources().getColor(R.color.orlandoGreen);
            int transparentGreen = getResources().getColor(R.color.orlandoGreenTransparent);
            layer.getDefaultPolygonStyle().setStrokeColor(green);
            layer.getDefaultPolygonStyle().setFillColor(transparentGreen);
            layer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void centerMapToUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM_LEVEL));
        }
    }

}
