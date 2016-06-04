package com.codefororlando.streettrees;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codefororlando.streettrees.api.models.Tree;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraChangeListener {

    private static final String TAG = "MAINACTIVITY";
    private GoogleMap map;
    private LocationManager locationManager;

    public static final int REQUEST_ACCESS_FINE_LOCATION = 10001;
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
        Tree treeA = new Tree(new SimpleDateFormat().parse("2015-11-13T23:37:00.000", new ParsePosition(0)), 28.562917, -81.350608, "Nuttall Oak");
        Tree treeB = new Tree(new SimpleDateFormat().parse("2015-11-13T21:46:00.000", new ParsePosition(0)), 28.499321, -81.479695, "Tuliptree");

        treeData = new Tree[]{treeA, treeB};

        addMarkersToMap(treeData);

    }

    private void addMarkersToMap(Tree[] trees) {
        // Add a marker in Sydney and move the camera
        for (Tree tree : trees) {
            map.addMarker(new MarkerOptions().position(tree.getLocation()).title(tree.getTreeName()));
        }

        String locationProvider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TODO: REQUEST PERMISSION - CONCERN RELEASE VERSION TARGET 16
            //requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"},REQUEST_ACCESS_FINE_LOCATION);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d(TAG,"Setting Map Location");
        Location myLocation = locationManager.getLastKnownLocation(locationProvider);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

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
        VisibleRegion visibleRegion = map.getProjection().getVisibleRegion();
    }
}
