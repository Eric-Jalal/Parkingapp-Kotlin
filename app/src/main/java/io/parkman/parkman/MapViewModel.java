package io.parkman.parkman;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import entities.Location;
import helper.StringToArray;
import usecase.JsonParser;

class MapViewModel {

    private static double latitude;
    private static double longitude;

    static void setLatLng(Context context){
        JsonParser jsonParser = new JsonParser();
        ArrayList<Double> locLatLng = reFormatLocationLatLng(jsonParser.loadJSONFromAsset(context).getCurrentLocationLatLng());
        latitude = locLatLng.get(0);
        longitude = locLatLng.get(1);
    }

    private static ArrayList<Double> reFormatLocationLatLng(String string) {
        ArrayList<Double> locLatLng = new ArrayList<>();
        List<String> listLatLng = StringToArray.convertStringToArray(string);
        // while loop
        for (String aListLatLng : listLatLng) {
            locLatLng.add(Double.parseDouble(aListLatLng));
        }
        return locLatLng;
    }

    static double getLatitude() {
        return latitude;
    }

    static double getLongitude() {
        return longitude;
    }
}
