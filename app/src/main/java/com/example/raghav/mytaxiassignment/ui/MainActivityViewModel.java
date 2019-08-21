package com.example.raghav.mytaxiassignment.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.raghav.mytaxiassignment.data.MyTaxiRepository;
import com.example.raghav.mytaxiassignment.data.model.PoiValues;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private LiveData<List<PoiValues>> mTaxiList;
    private final MyTaxiRepository mRepository;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PoiValues mSelectedPoiValue;

    public PoiValues getSelectedPoiValue() {
        return mSelectedPoiValue;
    }

    public void setSelectedPoiValue(PoiValues poiValues) {
        mSelectedPoiValue = poiValues;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(boolean value) {
        this.loading.setValue(value);
    }

    public MainActivityViewModel(MyTaxiRepository repository) {
        this.mRepository = repository;
        mTaxiList = new MutableLiveData<>();

    }

    public LiveData<List<PoiValues>> getTaxiList(){
        return mTaxiList;
    }

    public void fetchTaxiData(){
        mTaxiList = mRepository.getTaxiData();
    }
}
