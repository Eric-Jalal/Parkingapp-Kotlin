package io.parkman.parkman;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Arrays;
import java.util.List;

import entities.Bounds;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapViewModel mapViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapViewModel = new MapViewModel(getApplicationContext());
    }

    /**
     * Creates a List of LatLngs that form a rectangle with the given dimensions.
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapViewModel.setLatLng();
        Bounds boundsEntity = mapViewModel.getBounds();

        mMap = googleMap;
        LatLng currentLocation = new LatLng(mapViewModel.getLatitude(), mapViewModel.getLongitude());

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(mapViewModel.getZonesPolygon());
        polygonOptions.clickable(true);
        polygonOptions.strokeColor(ContextCompat.getColor(this,android.R.color.holo_orange_dark));
        polygonOptions.fillColor(ContextCompat.getColor(this,android.R.color.holo_orange_light));
        googleMap.addPolygon(polygonOptions);

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
