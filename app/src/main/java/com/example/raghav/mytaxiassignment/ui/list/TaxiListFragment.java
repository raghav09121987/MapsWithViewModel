package com.example.raghav.mytaxiassignment.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raghav.mytaxiassignment.R;
import com.example.raghav.mytaxiassignment.data.model.PoiValues;
import com.example.raghav.mytaxiassignment.databinding.FragmentTaxiListBinding;
import com.example.raghav.mytaxiassignment.ui.MainActivity;
import com.example.raghav.mytaxiassignment.ui.MainActivityViewModel;
import com.example.raghav.mytaxiassignment.ui.MainActivityViewModelFactory;
import com.example.raghav.mytaxiassignment.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class TaxiListFragment extends Fragment implements TaxiListAdapter.TaxiAdapterOnItemClickHandler {
    private RecyclerView mRecyclerView;
    private TaxiListAdapter mAdapter;
    private MainActivityViewModel mViewModel;
    List<PoiValues> mTaxiList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        FragmentTaxiListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_taxi_list, parent, false);
        MainActivityViewModelFactory detailFactory = InjectorUtils.provideMainActivityViewModelFactory(
                getActivity().getApplicationContext());
        mViewModel = ViewModelProviders.of(getActivity(), detailFactory).get(MainActivityViewModel.class);

        mRecyclerView = binding.recyclerView;
        // Create an adapter and supply the data to be displayed.
        mAdapter = new TaxiListAdapter(mTaxiList, TaxiListFragment.this);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        if(savedInstanceState == null) {
            mViewModel.fetchTaxiData();
            mViewModel.setLoading(true);
        }

        mViewModel.getTaxiList().observe(getActivity(), mTaxiList -> {
            //Update the UI
            if (mTaxiList != null && mTaxiList.size()!=0) {
                mViewModel.setLoading(false);
                mAdapter.refreshData(mTaxiList);
            }
        });

        return binding.getRoot();
    }

    /**
     * This method is called when a list item is clicked
     * @param f PoiValues
     */
    @Override
    public void onItemClick(PoiValues f) {
        mViewModel.setSelectedPoiValue(f);
        ((MainActivity)getActivity()).showOnMap();
    }
}
