package dvelopd.retrofittutorial.api;

import dvelopd.retrofittutorial.models.MovieResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/")
    Call<MovieResults> getMovies(@Query("s") String search,
                                 @Query("apikey") String key);

}
