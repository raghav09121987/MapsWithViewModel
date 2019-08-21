package com.example.raghav.mytaxiassignment.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PoiValues implements Serializable {

    private Integer id;
    private String fleetType;
    @SerializedName("coordinate")
    private Coordinate coordinates;

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFleetType() {
        return fleetType;
    }

    public void setFleetType(String fleetType) {
        this.fleetType = fleetType;
    }
}
