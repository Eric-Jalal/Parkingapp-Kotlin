package Data;

import java.util.List;

import entities.Vehicles;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Endpoint {
    @GET("/vehicles/")
    Call<List<Vehicles>> vehiclesList();
}
