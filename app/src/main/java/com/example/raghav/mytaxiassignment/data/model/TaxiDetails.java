package com.example.raghav.mytaxiassignment.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TaxiDetails implements Serializable {

    @SerializedName("poiList")
    private List<PoiValues> poiList;

    public List<PoiValues> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<PoiValues> poiList) {
        this.poiList = poiList;
    }
}
