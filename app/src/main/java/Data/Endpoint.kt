package Data

import entities.Vehicles
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @get:GET("/vehicles/")
    val vehiclesList: Call<List<Vehicles>>
}
