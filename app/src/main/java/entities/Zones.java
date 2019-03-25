package entities;

import com.google.gson.annotations.SerializedName;

public class Zones {

    @SerializedName("id")
    private int id;
    @SerializedName("polygon")
    private String polygon;
    @SerializedName("name")
    private String name;
    @SerializedName("payment_is_allowed")
    private int paymentIsAllowed;
    @SerializedName("max_duration")
    private float maxDuration;
    @SerializedName("service_price")
    private float servicePrice;
    @SerializedName("depth")
    private int depth;
    @SerializedName("draw")
    private int draw;
    @SerializedName("sticker_required")
    private int stickerRequired;
    @SerializedName("currency")
    private String currency;
    @SerializedName("contact_email")
    private String contactEmail;
    @SerializedName("point")
    private String point;
    @SerializedName("country")
    private String country;
    @SerializedName("provider_id")
    private int providerId;
    @SerializedName("provider_name")
    private String providerName;

    public int getId() {
        return id;
    }

    public String getPolygon() {
        return polygon;
    }

    public String getName() {
        return name;
    }

    public int getPaymentIsAllowed() {
        return paymentIsAllowed;
    }

    public float getMaxDuration() {
        return maxDuration;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public int getDepth() {
        return depth;
    }

    public int getDraw() {
        return draw;
    }

    public int getStickerRequired() {
        return stickerRequired;
    }

    public String getCurrency() {
        return currency;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getPoint() {
        return point;
    }

    public String getCountry() {
        return country;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }
}
