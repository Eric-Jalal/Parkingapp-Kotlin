package io.parkman.parkman;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import entities.Bounds;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapViewModel mapViewModel = new MapViewModel(getApplicationContext());
        mapViewModel.setLatLng();
        Bounds boundsEntity = mapViewModel.getBounds();

        mMap = googleMap;
        LatLng currentLocation = new LatLng(mapViewModel.getLatitude(), mapViewModel.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Finland"));
        mMap.setMinZoomPreference(13f);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng northEast = new LatLng(, boundsEntity.getNorth()boundsEntity.getEast());
        LatLng southWest = new LatLng(boundsEntity.getWest(),boundsEntity.getSouth());
        builder.include(northEast);
        builder.include(southWest);
        LatLngBounds bounds = builder.build();

        // begin new code:
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        Log.d("Amir", String.valueOf(boundsEntity.getEast()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,  width, height, padding));
    }
}
