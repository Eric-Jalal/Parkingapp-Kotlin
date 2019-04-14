package io.parkman.parkman;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import Data.Data;
import Data.Endpoint;
import entities.LocationData;
import entities.Vehicles;
import helper.StringToArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MapViewModel {

    private Context context;
    private double latitude;
    private double longitude;
    private List<Vehicles> body;

    MapViewModel(final Context context) {
        Endpoint endpoint = Data.getRetrofitInstance().create(Endpoint.class);
        Call<List<Vehicles>> call = endpoint.getVehiclesList();
        call.enqueue(new Callback<List<Vehicles>>() {
            @Override
            public void onResponse(Call<List<Vehicles>> call, Response<List<Vehicles>> response) {
                Toast.makeText(context.getApplicationContext(), "Got the data :D", Toast.LENGTH_SHORT).show();
                body = response.body();
            }

            @Override
            public void onFailure(Call<List<Vehicles>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

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
