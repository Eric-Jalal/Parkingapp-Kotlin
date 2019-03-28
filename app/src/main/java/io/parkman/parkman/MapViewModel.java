package io.parkman.parkman;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import entities.Bounds;
import entities.Location;
import entities.Zones;
import helper.StringToArray;
import usecase.JsonParser;

class MapViewModel {

    private Context context;
    private double latitude;
    private double longitude;
    private JsonParser jsonParser = new JsonParser();

    public MapViewModel(Context context) {
        this.context = context;
    }

    private Location loadLocationGson(Context context){
       return jsonParser.loadJSONFromAsset(context);
    }

    void setLatLng(){
        ArrayList<Double> locLatLng = extractLocationLatLng(loadLocationGson(context).getCurrentLocationLatLng());
        latitude = locLatLng.get(0);
        longitude = locLatLng.get(1);
    }

    public Bounds getBounds(){
        return loadLocationGson(context).getLocationData().getBounds();
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    public ArrayList<LatLng> getZonesPolygon() {
        List<Zones> zonesList = loadLocationGson(context).getLocationData().getZones();
        ArrayList<LatLng> locLatLng = new ArrayList<LatLng>();
        for (Zones zones : zonesList){
            List<String> listLatLngCommaSeparated = StringToArray.convertStringToArrayCommaSeparated(zones.getPolygon());
            for (int i = 0; i < listLatLngCommaSeparated.size(); i++) {
                List<String> polygonLatLng;
                polygonLatLng = StringToArray.convertStringToArraySpaceSeparated(listLatLngCommaSeparated.get(i));
                LatLng point = new LatLng(Double.parseDouble(polygonLatLng.get(0)), Double.parseDouble(polygonLatLng.get(1)));
                locLatLng.add(point);
            }
        }
        return locLatLng;
    }

    private ArrayList<Double> extractLocationLatLng(String string) {
        ArrayList<Double> locLatLng = new ArrayList<>();
        List<String> listLatLng = StringToArray.convertStringToArrayCommaSeparated(string);
        // while loop
        for (String aListLatLng : listLatLng) {
            locLatLng.add(Double.parseDouble(aListLatLng));
        }
        return locLatLng;
    }

}
