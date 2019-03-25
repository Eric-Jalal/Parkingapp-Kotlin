package io.parkman.parkman;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import entities.Bounds;
import entities.Location;
import helper.StringToArray;
import usecase.JsonParser;

class MapViewModel {

    private Context context;
    private double latitude;
    private double longitude;
    private JsonParser jsonParser = new JsonParser();
    private Bounds bounds;

    public MapViewModel(Context context) {
        this.context = context;
    }

    private Location loadLocationGson(Context context){
       return jsonParser.loadJSONFromAsset(context);
    }

    void setLatLng(){
        ArrayList<Double> locLatLng = reFormatLocationLatLng(loadLocationGson(context).getCurrentLocationLatLng());
        latitude = locLatLng.get(0);
        longitude = locLatLng.get(1);
    }

    private ArrayList<Double> reFormatLocationLatLng(String string) {
        ArrayList<Double> locLatLng = new ArrayList<>();
        List<String> listLatLng = StringToArray.convertStringToArray(string);
        // while loop
        for (String aListLatLng : listLatLng) {
            locLatLng.add(Double.parseDouble(aListLatLng));
        }
        return locLatLng;
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
}
