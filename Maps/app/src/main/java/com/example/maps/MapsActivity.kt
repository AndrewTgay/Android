package com.example.maps

import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.maps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//SHA1: F2:D0:09:DB:D4:AC:EF:9B:CB:CF:09:63:E0:13:E7:B0:EE:D2:3E:66
    //api_key : AIzaSyAeQu34hyJgezFJAQkbHr21GgX7v-RmUU8
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val TAG = MapsActivity::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val zoomLev = 18f
        val overlaySize = 100f

        val homeLatLng = LatLng(30.149389, 31.323278)
        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.android))
            .position(homeLatLng,overlaySize)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng,zoomLev))
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.addGroundOverlay(androidOverlay)

        setMapLongClick(map)
        setPoiClick(map)
        enableMyLocation()
       // setMapStyle(map)
    }


    private fun setMapStyle(map:GoogleMap){
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if(!success){
                Log.e(TAG,"Style parsing Fail")
            }
        }catch (e:Resources.NotFoundException){
            Log.e(TAG,"Can't find style. Error $e")
        }
    }

    private fun setPoiClick(map:GoogleMap){
        map.setOnPoiClickListener{poi->
            val poiMarker = map.addMarker(MarkerOptions()
                .position(poi.latLng)
                .title(poi.name))

        }
    }

    private fun setMapLongClick(map:GoogleMap){
        map.setOnMapLongClickListener { latLng->
            val snippet = String.format(Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
            latLng.latitude,
            latLng.longitude)
            map.addMarker(
                MarkerOptions().position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    private fun isPermissionGranted():Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation(){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //ask for Permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
                enableMyLocation()
                return
            }
            map.setMyLocationEnabled(true)
       // }else{
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       // return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.map_options,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                true }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                true }
            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true }
            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true }
            else -> super.onOptionsItemSelected(item)
            }
        }
    }