package com.example.raghav.mytaxiassignment.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.raghav.mytaxiassignment.R;
import com.example.raghav.mytaxiassignment.ui.detail.MapsFragment;
import com.example.raghav.mytaxiassignment.ui.list.TaxiListFragment;

/**
 * This class loads both the map and list fragments.
 */
public class MainActivity extends AppCompatActivity {
    private final String LIST_TAG = "LIST_TAG";
    private final String MAPS_TAG = "MAPS_TAG";

    private TaxiListFragment mTaxiListFragment;
    private MapsFragment mMapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaxiListFragment = (TaxiListFragment) getSupportFragmentManager().findFragmentByTag(LIST_TAG);

        if (mTaxiListFragment == null && savedInstanceState == null) {
            mTaxiListFragment = new TaxiListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.list_container, mTaxiListFragment, LIST_TAG).commit();
        }
        if(mMapsFragment == null && savedInstanceState == null) {
            mMapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.map_container, mMapsFragment, MAPS_TAG).commit();
        }
    }

    /**
     * This method moves the map to new position.
     */
    public void showOnMap() {
        mMapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentByTag(MAPS_TAG);

        if(mMapsFragment == null) {
            mMapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.map_container, mMapsFragment, MAPS_TAG).commit();
        }else{
            mMapsFragment.animateToPosition();
        }
    }
}
