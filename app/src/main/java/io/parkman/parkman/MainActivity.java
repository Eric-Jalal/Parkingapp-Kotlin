package io.parkman.parkman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import entities.Bounds;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mMap.setMinZoomPreference(13f);

        // Set bounds
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng northEast = new LatLng(boundsEntity.getNorth(), boundsEntity.getEast());
        LatLng southWest = new LatLng(boundsEntity.getSouth(),boundsEntity.getWest());
        builder.include(northEast);
        builder.include(southWest);
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
    }
}
