package io.parkman.parkman

import android.content.Context
import android.widget.Toast

import java.util.ArrayList

import Data.Data
import Data.Endpoint
import entities.Vehicles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MapViewModel(context: Context) {

    private var body: List<Vehicles>? = null

    val vehicles: ArrayList<Vehicles>
        get() {
            val listVehicles = ArrayList<Vehicles>()
            for (i in 0..body!!.size) {
                listVehicles.add(body!![i])
            }
            return listVehicles
        }

    init {
        val endpoint = Data.retrofitInstance!!.create(Endpoint::class.java)
        val call = endpoint.vehiclesList
        call.enqueue(object : Callback<List<Vehicles>> {
            override fun onResponse(call: Call<List<Vehicles>>, response: Response<List<Vehicles>>) {
                Toast.makeText(context.applicationContext, "Got the data :D", Toast.LENGTH_SHORT).show()
                body = response.body()
            }

            override fun onFailure(call: Call<List<Vehicles>>, t: Throwable) {
                Toast.makeText(context.applicationContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
