package com.example.sola;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("/3/movie/top_rated")
    Call<ModelData> getReponse(@Query("api_key") String api_key);
}
