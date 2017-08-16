// Copyright © 2017 Code for Orlando.
// 
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.codefororlando.streettrees.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Criteria
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.res.ResourcesCompat
import com.codefororlando.streettrees.R
import com.codefororlando.streettrees.api.models.Tree
import com.codefororlando.streettrees.api.providers.ITreeProvider
import com.codefororlando.streettrees.requesttree.RequestTreeActivity
import com.codefororlando.streettrees.treemap.MapPresenter
import com.codefororlando.streettrees.view.CustomInfoWindowAdapter
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer

class MainActivity : KodeinAppCompatActivity(),
        MapPresenter.MapView,
        OnMapReadyCallback,
        OnMarkerClickListener,
        OnInfoWindowClickListener,
        OnCameraMoveListener {

    private val treeProvider: ITreeProvider by instance()
    private val presenter: MapPresenter = MapPresenter()
    private val cachedTrees: MutableList<Tree> = mutableListOf()

    private lateinit var locationManager: LocationManager
    private lateinit var locationProvider: String
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_tree_map)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, RequestTreeActivity::class.java)
            startActivity(intent)
        }
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(treeProvider, this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
        cachedTrees.clear()
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setInfoWindowAdapter(CustomInfoWindowAdapter(layoutInflater))
        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)
        map.setOnCameraMoveListener(this)

        if (checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {

            requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    FINE_LOCATION_REQUEST_CODE)
        } else {
            locationProvider = locationManager.getBestProvider(Criteria(), true)
            centerMapToUser()
            loadAndSetGeoData()
            presenter.fetchTrees()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        val treeName = marker.title
        val location = marker.position
        detailIntent.putExtra(EXTRA_LOCATION, location)
        detailIntent.putExtra(EXTRA_TREETYPE, treeName)
        startActivity(detailIntent)
    }

    override fun onCameraMove() {
        updateMapWithTrees(cachedTrees)
    }

    override fun updateMapWithTrees(trees: List<Tree>?) {
        if (trees == null) {
            return
        }

        val vr = map.projection.visibleRegion
        val visibleTrees = presenter.getVisibleTrees(vr, trees, DEFAULT_MARKER_LIMIT)
        for (tree in visibleTrees) {
            map.addMarker(MarkerOptions().position(tree.location).title(tree.treeName))
        }

        cachedTrees.clear()
        cachedTrees.addAll(trees)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {

        when (requestCode) {
            FINE_LOCATION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PERMISSION_GRANTED
                        && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    onMapReady(map)
                    centerMapToUser()
                } else {
                    // permission denied, boo! Disable the
                }
            }
        }
    }

    private fun loadAndSetGeoData() {
        try {
            val layer = GeoJsonLayer(map, R.raw.orlando_city_limits, applicationContext)
            val green = ResourcesCompat.getColor(resources, R.color.orlandoGreen, theme)
            val transparentGreen = ResourcesCompat.getColor(resources, R.color.orlandoGreen, theme)
            layer.defaultPolygonStyle.strokeColor = green
            layer.defaultPolygonStyle.fillColor = transparentGreen
            layer.addLayerToMap()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun centerMapToUser() {
        if (checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            return
        }
        val lastKnownLocation = locationManager.getLastKnownLocation(locationProvider)
        if (lastKnownLocation != null) {
            val currentLocation = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM_LEVEL.toFloat()))
        }
    }

    companion object {
        const val EXTRA_LOCATION = "location"
        const val EXTRA_TREETYPE = "type"

        private const val FINE_LOCATION_REQUEST_CODE = 103   //random number
        private const val DEFAULT_ZOOM_LEVEL = 10
        private const val DEFAULT_MARKER_LIMIT = 20
    }

}
