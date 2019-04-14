package entities

import com.google.gson.annotations.SerializedName

class Vehicles {

    @SerializedName("id")
    val id: Int = 0 // this depends on the api, could be something else than integer
    @SerializedName("name")
    val name: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("latitude")
    val latitude: Double = 0.toDouble()
    @SerializedName("longitude")
    val longitude: Double = 0.toDouble()
    @SerializedName("batteryLevel")
    val batteryLevel: Int = 0
    @SerializedName("timestamp")
    val timestamp: String? = null
    @SerializedName("price")
    val price: Int = 0
    @SerializedName("priceTime")
    val priceTime: Int = 0
    @SerializedName("currency")
    val currency: String? = null
}
