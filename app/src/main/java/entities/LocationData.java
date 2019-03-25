package entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationData {

    @SerializedName("bounds")
    private Bounds bounds;
    @SerializedName("zones")
    private List<Zones> zones;

    public Bounds getBounds() {
        return bounds;
    }

    public List<Zones> getZones() {
        return zones;
    }

}
