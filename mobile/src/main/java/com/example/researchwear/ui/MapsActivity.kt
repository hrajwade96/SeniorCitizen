package com.example.researchwear.ui

import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.researchwear.R
import com.example.researchwear.interfaces.INetworkAPI
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var mapView: MapView? = null
    private lateinit var locationCallback: LocationCallback
    private var latlon = LatLng(19.1231827, 72.8339267)
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var lat = 19.1231827
    var long = 72.8339267
    val zoomLevel = 17f
    var flag = false
    protected val REQUEST_CHECK_SETTINGS = 0x1
    val homeLatLng = LatLng(lat, long)
    private var locationManager: LocationManager? = null
    private lateinit var marker: Marker
    var latLngArray = arrayOf(LatLng(0.0, 0.0), LatLng(0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    override fun onMapReady(googleMap: GoogleMap) {
        //  Toast.makeText(this,"inonmapready",Toast.LENGTH_LONG).show()
        mMap = googleMap

        val homeLatLng = LatLng(lat, long)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        mMap.addMarker(MarkerOptions().position(homeLatLng))
        addMarkers(mMap)
        setMapLongClick(mMap)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                var currentLatLng = LatLng(0.0, 0.0)

                for (location in locationResult.locations) {
                    // Update UI with location data
                    // ...
                    Log.i("Latitude", "" + location.latitude)
                    Log.i("Longitude", "" + location.longitude)
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    //  flag=true


                    //Toast.makeText(applicationContext,"location is : ${location.latitude}${location.longitude}",Toast.LENGTH_LONG).show()

                }
                calMinDistance(currentLatLng)

//                if(marker!=null){
//                    marker.remove()
//                }

                //   mMap.clear()


                if (flag == false) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel))
                } else {
                    marker.remove()
                }
                addLocationMarker(currentLatLng, mMap)
                flag = true


            }

        }
        startLocationUpdates()
    }

    fun addLocationMarker(currentLatLong: LatLng, mMap: GoogleMap) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        val titleStr = "Current location"


        markerOptions.title(titleStr)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.guy))
        marker = mMap.addMarker(markerOptions)


//        mMap.addMarker(MarkerOptions().position(currentLatLong))
//        MarkerOptions()
//
//            .position(currentLatLong)
//            .title(getString(R.string.title))
//            .draggable(true)
//            .icon(BitmapDescriptorFactory.fromResource(R.drawable.guy))
    }

    fun addMarkers(map: GoogleMap) {
        latlon = LatLng(lat, long)
        val retrofit = Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://research-app12.herokuapp.com/").build()

        val postsApi = retrofit.create(INetworkAPI::class.java)

        var response = postsApi.getAllPosts()

        response
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ response ->

                when (response.code()) {
                    200 -> {

                        var resData = response.body()
                        var lat = 0.0
                        var lon = 0.0
                        var x = 1
                        for (x in 1..4) {
                            lat = resData!!.allStores[x].lat
                            lon = resData!!.allStores[x].lon
                            latlon = LatLng(lat, lon)
                            //Toast.makeText(this,latlon.toString(), Toast.LENGTH_LONG).show()
                            val snippet = String.format(
                                Locale.getDefault(),
                                "Lat: %1$.5f, Long: %2$.5f",
                                latlon.latitude,
                                latlon.longitude
                            )
//            mMap.addMarker(MarkerOptions().position(latlon))
//            MarkerOptions()
//                .position(latlon)
//                .title(getString(R.string.title))
//                .snippet(snippet)
//                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.medicallogo))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.guy))


                            val markerOptions = MarkerOptions().position(latlon)
                            val titleStr = "Current location"


                            markerOptions.title(titleStr)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.medicallogo))
                            marker = mMap.addMarker(markerOptions)

                        }


                    }
                    else -> {
                        Toast.makeText(this, "HTTP ERROR : ${response.code()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                response.body()
            }, { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            })


    }

    fun calMinDistance(currentLatLong: LatLng) {
        var r = 6371e3 // metres
        var latlon1 = LatLng(19.1231827, 72.8339267)
        var φ1 = Math.toRadians(currentLatLong.latitude)
        var φ2 = Math.toRadians(latlon1.latitude)
        var Δφ = Math.toRadians(latlon1.latitude - currentLatLong.latitude)
        var Δλ = Math.toRadians(latlon1.longitude - currentLatLong.longitude)

        var a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ / 2) * Math.sin(Δλ / 2)
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        var d = r * c
        Toast.makeText(
            this, "The Distance between Bhavans and your location is :${d / 1000} km",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener {
            map.setOnMapLongClickListener { latLng ->
                val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
                )
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.title))
                        .snippet(snippet)
                )

            }
        }

    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
        }
    }
}
