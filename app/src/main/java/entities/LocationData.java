package entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationData {

    @SerializedName("vehicles")
    private List<Vehicles> vehicles;

    public List<Vehicles> getVehicles() {
        return vehicles;
    }

}
