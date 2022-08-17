package com.example.controlwifi;


import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/speed")
    public Single<Speed> getSpeed();

    @GET("/left")
    public Single<Status> left();

    @GET("/right")
    public Single<Status> right();

    @GET("/control")
    public Single<Status> control(@Query(value="percent", encoded=true) String percent);
}
