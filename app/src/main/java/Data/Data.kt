package Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Data {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://my-json-server.typicode.com/FlashScooters/Challenge/"

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
}
