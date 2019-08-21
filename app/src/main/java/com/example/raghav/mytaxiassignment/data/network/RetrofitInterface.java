package com.example.raghav.mytaxiassignment.data.network;

import com.example.raghav.mytaxiassignment.data.model.TaxiDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * This class contains the endpoints.
 */
public interface RetrofitInterface {

    /**
     * This method performs HTTP request to get the list of all Taxis
     * @param options latitude and longitude as map object to be used as query parameters.
     * @return Call<TaxiDetails>
     */
    @GET("/")
    public Call<TaxiDetails> getAllTaxis(@QueryMap Map<String, String> options);

}
