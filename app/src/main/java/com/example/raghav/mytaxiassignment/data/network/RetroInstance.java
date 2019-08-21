package com.example.raghav.mytaxiassignment.data.network;

import com.example.raghav.mytaxiassignment.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    private static Retrofit retrofit = null;

    /**
     * This converts the response JSON into the POJO objects.
     * @return Retrofit instance.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
