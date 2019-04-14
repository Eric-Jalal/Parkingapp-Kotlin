package io.parkman.parkman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var mapViewModel: MapViewModel? = null
    private var pinImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mapViewModel = MapViewModel(applicationContext)
        findViews()
    }

    private fun findViews() {
        pinImageView = findViewById(R.id.imageView)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setZoneMarkers()
        setMapPinVisibilityStates()
        mMap!!.setMinZoomPreference(15f)
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    private fun setMapPinVisibilityStates() {
        mMap!!.setOnCameraMoveListener { pinImageView!!.visibility = View.INVISIBLE }
        mMap!!.setOnCameraIdleListener { pinImageView!!.visibility = View.VISIBLE }
    }

    private fun setZoneMarkers() {
        // Marker
        for (i in 0 until mapViewModel!!.vehicles.size) {
            // TODO: this part should be iterated and generated the accurate data in ViewModel not here!
            mMap!!.addMarker(MarkerOptions()
                    .position(LatLng(mapViewModel!!.vehicles[i].latitude,
                            mapViewModel!!.vehicles[i].longitude))
                    .title(mapViewModel!!.vehicles[i].name)
                    .draggable(true)
                    .snippet(mapViewModel!!.vehicles[i].price.toString() + mapViewModel!!.vehicles[i].currency!!)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        }
    }

}
