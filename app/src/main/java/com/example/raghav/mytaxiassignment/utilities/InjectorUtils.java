package com.example.raghav.mytaxiassignment.utilities;

import android.content.Context;

import com.example.raghav.mytaxiassignment.ui.MainActivityViewModelFactory;
import com.example.raghav.mytaxiassignment.data.network.RetroInstance;
import com.example.raghav.mytaxiassignment.data.network.RetrofitInterface;
import com.example.raghav.mytaxiassignment.data.MyTaxiRepository;
import com.example.raghav.mytaxiassignment.data.network.TaxiNetworkDataSource;

/**
 * Provides static methods to inject the various classes needed for MyTaxi
 */
public class InjectorUtils {

    private static MyTaxiRepository provideRepository(Context context) {
        TaxiNetworkDataSource networkDataSource =
                TaxiNetworkDataSource.getInstance();
        return MyTaxiRepository.getInstance(networkDataSource,context);
    }

    public static TaxiNetworkDataSource provideNetworkDataSource() {
        return TaxiNetworkDataSource.getInstance();
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        MyTaxiRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository);
    }

    public static RetrofitInterface getRetroInstance() {
        return RetroInstance.getClient().create(RetrofitInterface.class);
    }
}