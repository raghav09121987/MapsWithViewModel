package com.example.raghav.mytaxiassignment.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.raghav.mytaxiassignment.data.MyTaxiRepository;

/**
 * This class provides repository instance to ViewModel class.
 */
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MyTaxiRepository mRepository;

    public MainActivityViewModelFactory(MyTaxiRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository);
    }
}
