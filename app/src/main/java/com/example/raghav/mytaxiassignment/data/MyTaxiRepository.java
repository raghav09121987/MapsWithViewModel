package com.example.raghav.mytaxiassignment.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.raghav.mytaxiassignment.utilities.InjectorUtils;
import com.example.raghav.mytaxiassignment.data.model.PoiValues;
import com.example.raghav.mytaxiassignment.data.network.TaxiNetworkDataSource;

import java.util.List;

/**
 * This class is the single source for all of our app's data
 */
public class MyTaxiRepository {
    private static final Object LOCK = new Object();
    private static MyTaxiRepository sInstance;
    Context mContext;
    private TaxiNetworkDataSource mTaxiNetworkDataSource;

    public MyTaxiRepository(TaxiNetworkDataSource taxiNetworkDataSource, Context context) {
        mTaxiNetworkDataSource = taxiNetworkDataSource;
        this.mContext = context;
    }

    public synchronized static MyTaxiRepository getInstance(TaxiNetworkDataSource weatherNetworkDataSource, Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MyTaxiRepository (weatherNetworkDataSource, context);
            }
        }
        return sInstance;
    }

    /**
     * calls fetchData method in TaxiNetworkDataSource
     * @return LiveData of type List<PoiValues>
     */
    public LiveData<List<PoiValues>> getTaxiData(){
        mTaxiNetworkDataSource = InjectorUtils.provideNetworkDataSource();
        return mTaxiNetworkDataSource.fetchData();
    }
}
