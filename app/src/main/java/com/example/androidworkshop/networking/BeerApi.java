package com.example.androidworkshop.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface BeerApi {

    @GET("beers")
    Call<List<Beer>> getBeers();

    @GET("beers")
    Call<List<Beer>> getBeerByName(
            @Query("name") String name
    ); // query param can be passed as null if we don't need them, retrofit will ignore there param if they do not have values

    @GET("beers")
    Call<List<Beer>> getBeerById(
            @Query("id") String id
    );


}
