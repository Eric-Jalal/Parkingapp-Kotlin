package Data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Data {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://my-json-server.typicode.com/FlashScooters/Challenge/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
