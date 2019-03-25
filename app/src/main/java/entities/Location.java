package entities;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("current_location")
    private String currentLocationLatLng;

    @SerializedName("location_data")
    private LocationData locationData;

    public String getCurrentLocationLatLng() {
        return currentLocationLatLng;
    }

    public LocationData getLocationData() {
        return locationData;
    }

}
