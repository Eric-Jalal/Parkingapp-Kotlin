package io.parkman.parkman;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.HashMap;
import java.util.List;

import entities.Vehicles;
import helper.StringToArray;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapViewModel mapViewModel;
    private ImageView pinImageView;
    private HashMap<String, Zones> zoneMap = new HashMap<>();
    private TextView textViewName,textViewMaxDuration,textViewEmail;
    private String markerTitle;
    private Button parkButton;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapViewModel = new MapViewModel(getApplicationContext());
        mapViewModel.setLatLng();
        findViews();
    }

    private void findViews() {
        pinImageView = findViewById(R.id.imageView);
        textViewName = findViewById(R.id.textBox_name);
        textViewEmail = findViewById(R.id.textBox_email);
        textViewMaxDuration = findViewById(R.id.textBox_maxDuration);
        parkButton = findViewById(R.id.button_park);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
        setMapBounds();
        drawPolygons();
        setZoneMarkers();
        setMapPinVisibilityStates();
        parkButtonOnClickListener();
        markerOnClickListener();
        mMap.setMinZoomPreference(15f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    private void setMapPinVisibilityStates() {
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
    }

    private void parkButtonOnClickListener() {
        parkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markerTitle != null){
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.you_parked_here) + markerTitle,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.first_select_a_marker),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void markerOnClickListener() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerTitle = marker.getTitle();
                if (zoneMap.get(marker.getTitle()) != null){
                    textViewName.setText(zoneMap.get(marker.getTitle()).getProviderName());
                    textViewEmail.setText(zoneMap.get(marker.getTitle()).getContactEmail());
                    textViewMaxDuration.setText(String.format("%s Min",
                            String.valueOf(zoneMap.get(marker.getTitle()).getMaxDuration())));
                }
                return false;
            }
        });
    }

    private void setZoneMarkers() {
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
                    .snippet(mapViewModel.getZoneData().get(i).getServicePrice() + getString(R.string.Euro))
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
    }

    private void drawPolygons() {
        // Polyline
        // TODO: This section must be modified, the bug is all the vertices are
        // TODO: connected to each other, so then .fillColor(Color.BLUE) is not working
        // TODO: Many different solutions has been tested, need to ask
        // TODO: I used Markers instead of changing inside of polygongit s
        for (int i = 0; i < mapViewModel.getZonesPolygon().size(); i++) {
            mMap.addPolygon(new PolygonOptions()
                    .addAll(mapViewModel.getZonesPolygon().get(i))
                    .strokeColor(Color.BLUE)
                    .fillColor(Color.BLUE));
        }
    }

    private void getCurrentLocation() {
        currentLocation = new LatLng(mapViewModel.getLatitude(), mapViewModel.getLongitude());
    }

    private void setMapBounds() {
        // Set bounds
        Vehicles vehiclesEntity = mapViewModel.getBounds();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng northEast = new LatLng(vehiclesEntity.getNorth(), vehiclesEntity.getEast());
        LatLng southWest = new LatLng(vehiclesEntity.getSouth(), vehiclesEntity.getWest());
        builder.include(northEast);
        builder.include(southWest);
        LatLngBounds bounds = builder.build();
        mMap.setLatLngBoundsForCameraTarget(bounds);
    }

}
