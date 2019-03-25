package entities;

import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("north")
    private double north;
    @SerializedName("south")
    private double south;
    @SerializedName("west")
    private double west;
    @SerializedName("east")
    private double east;

    public double getNorth() {
        return north;
    }

    public double getSouth() {
        return south;
    }

    public double getWest() {
        return west;
    }

    public double getEast() {
        return east;
    }
}
