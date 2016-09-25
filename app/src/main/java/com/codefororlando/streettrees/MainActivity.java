package com.codefororlando.streettrees;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.providers.SavedTreesProvider;
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

import java.io.IOException;
import java.text.ParseException;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraChangeListener {

    private static final String TAG = "MAINACTIVITY";
    private GoogleMap map;
    private LocationManager locationManager;

    public static final int REQUEST_ACCESS_FINE_LOCATION = 10001;
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_TREETYPE = "type";

    FloatingActionButton fab;
    String locationProvider;

    public static final int FINE_LOCATION_REQUEST_CODE = 103;   //random number
    private int DEFAULT_ZOOM_LEVEL = 10;

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
        map.setOnCameraChangeListener(this);

        locationProvider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
            //TODO: REQUEST PERMISSION - CONCERN RELEASE VERSION TARGET 16
            //requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"},REQUEST_ACCESS_FINE_LOCATION);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        centerMapToUser();
        loadAndSetGeoData();
    }

    private void addMarkersToMap(Tree[] trees) {
        // Add a marker in Sydney and move the camera
        for (Tree tree : trees) {
            map.addMarker(new MarkerOptions().position(tree.getLocation()).title(tree.getTreeName()));
        }
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
        detailIntent.putExtra(EXTRA_LOCATION, location);
        detailIntent.putExtra(EXTRA_TREETYPE, treeTame);
        startActivity(detailIntent);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        try {
            SavedTreesProvider provider = SavedTreesProvider.getInstance(getApplicationContext());
            VisibleRegion vr = map.getProjection().getVisibleRegion();
            addMarkersToMap(provider.getVisibleTrees(vr, 20));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM_LEVEL));
    }

}
