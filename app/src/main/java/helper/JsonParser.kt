package helper

import android.content.Context
import android.content.res.AssetManager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type

import entities.LocationData

// I wrote this class before i figured out there is an API for fetching that json file
class JsonParser {
    fun loadJSONFromAsset(context: Context): LocationData {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open("data.json")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val location = object : TypeToken<LocationData>() {

        }.type
        return gson.fromJson(reader, location)
    }
}
