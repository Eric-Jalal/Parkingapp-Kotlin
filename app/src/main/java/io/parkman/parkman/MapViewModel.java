package io.parkman.parkman;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import Data.Data;
import Data.Endpoint;
import entities.LocationData;
import entities.Vehicles;
import helper.StringToArray;
import helper.JsonParser;

class MapViewModel {

    private Context context;
    private double latitude;
    private double longitude;
    private JsonParser jsonParser = new JsonParser();

    MapViewModel(Context context) {
        Endpoint service = Data.getRetrofitInstance().create(Endpoint.class);

        this.context = context;
    }

    private LocationData loadLocationGson(Context context){
       return jsonParser.loadJSONFromAsset(context);
    }

    void setLatLng(){
        ArrayList<Double> locLatLng = extractLocationLatLng(loadLocationGson(context).getCurrentLocationLatLng());
        latitude = locLatLng.get(0);
        longitude = locLatLng.get(1);
    }

    Vehicles getBounds(){
        return loadLocationGson(context).getLocationData().getVehicles();
    }

    List<Zones> getZoneData() {
        return loadLocationGson(context).getLocationData().getZones();
    }

    ArrayList<ArrayList<LatLng>> getZonesPolygon() {
        List<Zones> zonesList = loadLocationGson(context).getLocationData().getZones();
        ArrayList<ArrayList<LatLng>> locLatLng = new ArrayList<>();
        ArrayList<LatLng> pointSequences = new ArrayList<>();
        for (Zones zones : zonesList){
            List<String> listLatLngCommaSeparated = StringToArray.INSTANCE.convertStringToArrayCommaSeparated(zones.getPolygon());
            for (int i = 0; i < listLatLngCommaSeparated.size(); i++) {
                List<String> polygonLatLng = StringToArray.INSTANCE.convertStringToArraySpaceSeparated(listLatLngCommaSeparated.get(i));
                pointSequences.add(new LatLng(Double.parseDouble(polygonLatLng.get(0)), Double.parseDouble(polygonLatLng.get(1))));
            }
            locLatLng.add(pointSequences);
        }
        return locLatLng;
    }

    private ArrayList<Double> extractLocationLatLng(String string) {
        ArrayList<Double> locLatLng = new ArrayList<>();
        List<String> listLatLng = StringToArray.INSTANCE.convertStringToArrayCommaSeparated(string);
        // while loop
        for (String aListLatLng : listLatLng) {
            locLatLng.add(Double.parseDouble(aListLatLng));
        }
        return locLatLng;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

}
