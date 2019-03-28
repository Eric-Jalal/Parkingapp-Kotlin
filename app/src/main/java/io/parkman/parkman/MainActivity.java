package io.parkman.parkman;

import android.graphics.Color;
import android.location.LocationListener;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import entities.Bounds;
import entities.Zones;
import helper.StringToArray;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapViewModel mapViewModel;
    private ImageView pinImageView;
    private HashMap<String, Zones> zoneMap = new HashMap<>();
    private TextView textViewName,textViewMaxDuration,textViewEmail;
    private Button parkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapViewModel = new MapViewModel(getApplicationContext());
        pinImageView = findViewById(R.id.imageView);
        textViewName = findViewById(R.id.textBox_name);
        textViewEmail = findViewById(R.id.textBox_email);
        textViewMaxDuration = findViewById(R.id.textBox_maxDuration);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapViewModel.setLatLng();
        Bounds boundsEntity = mapViewModel.getBounds();

        mMap = googleMap;
        LatLng currentLocation = new LatLng(mapViewModel.getLatitude(), mapViewModel.getLongitude());

        // Polyline
        mMap.addPolygon(new PolygonOptions()
                .addAll(mapViewModel.getZonesPolygon())
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        // Set bounds
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng northEast = new LatLng(boundsEntity.getNorth(), boundsEntity.getEast());
        LatLng southWest = new LatLng(boundsEntity.getSouth(),boundsEntity.getWest());
        builder.include(northEast);
        builder.include(southWest);
        LatLngBounds bounds = builder.build();
        mMap.setLatLngBoundsForCameraTarget(bounds);
        mMap.setMinZoomPreference(15f);

        // Marker
        for (int i = 0; i < mapViewModel.getZoneData().size(); i++){
            List<String> polygonLatLng;
            polygonLatLng = StringToArray.convertStringToArraySpaceSeparated(mapViewModel.getZoneData().get(i).getPoint());
            zoneMap.put(mapViewModel.getZoneData().get(i).getName().trim(), mapViewModel.getZoneData().get(i));
            LatLng point = new LatLng(Double.parseDouble(polygonLatLng.get(0)), Double.parseDouble(polygonLatLng.get(1)));
            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(mapViewModel.getZoneData().get(i).getName())
                    .draggable(true)
                    .snippet(mapViewModel.getZoneData().get(i).getServicePrice() + " Euro")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (zoneMap.get(marker.getTitle()) != null){
                    textViewName.setText(zoneMap.get(marker.getTitle()).getProviderName());
                    textViewEmail.setText(zoneMap.get(marker.getTitle()).getContactEmail());
                    textViewMaxDuration.setText(String.format("%s Min", String.valueOf(zoneMap.get(marker.getTitle()).getMaxDuration())));
                }
                return false;
            }
        });


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                pinImageView.setVisibility(View.INVISIBLE);
            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                pinImageView.setVisibility(View.VISIBLE);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

}
