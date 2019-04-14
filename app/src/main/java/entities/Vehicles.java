package entities;

import com.google.gson.annotations.SerializedName;

public class Vehicles {

    @SerializedName("id")
    private int id; // this depends on the api, could be something else than integer
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("batteryLevel")
    private int batteryLevel;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("price")
    private int price;
    @SerializedName("priceTime")
    private int priceTime;
    @SerializedName("currency")
    private String currency;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getPrice() {
        return price;
    }

    public int getPriceTime() {
        return priceTime;
    }

    public String getCurrency() {
        return currency;
    }
}
