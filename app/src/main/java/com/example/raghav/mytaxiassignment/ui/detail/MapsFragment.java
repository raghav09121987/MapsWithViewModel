package com.example.raghav.mytaxiassignment.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raghav.mytaxiassignment.R;
import com.example.raghav.mytaxiassignment.data.model.PoiValues;
import com.example.raghav.mytaxiassignment.ui.MainActivityViewModel;
import com.example.raghav.mytaxiassignment.ui.MainActivityViewModelFactory;
import com.example.raghav.mytaxiassignment.utilities.Constants;
import com.example.raghav.mytaxiassignment.utilities.InjectorUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.example.raghav.mytaxiassignment.utilities.Constants.LOG_TAG;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    private float lat, lon;
    private List<PoiValues> markerList = new ArrayList<>();
    private MainActivityViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, parent, false);
        MainActivityViewModelFactory detailFactory = InjectorUtils.provideMainActivityViewModelFactory(
                getActivity().getApplicationContext());
        mViewModel = ViewModelProviders.of(getActivity(), detailFactory).get(MainActivityViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(mViewModel.getSelectedPoiValue()!=null) {
            lat = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLatitude());
            lon = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLongitude());
        }
        markerList = mViewModel.getTaxiList().getValue();
        return view;
    }

    /**
     * Finds the bounds of currently visible ares in the map.
     */
    private void findMapBounds(){
        LatLng farLeft = mMap.getProjection().getVisibleRegion().farLeft;
        LatLng farRight = mMap.getProjection().getVisibleRegion().farRight;
        LatLng nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng nearRight = mMap.getProjection().getVisibleRegion().nearRight;
        addMarkers(farLeft, farRight, nearLeft, nearRight);
    }

    /**
     * Adds the markers in the visible area.
     * @param farLeft the far left corner of the camera.
     * @param farRight the far right corner of the map.
     * @param nearLeft the bottom left corner of the map.
     * @param nearRight the bottom right corner of the map.
     */
    private void addMarkers(LatLng farLeft,LatLng farRight,LatLng nearLeft,LatLng nearRight){
        int count = 0;
        if(markerList!=null) {
            for (int i = 0; i < markerList.size(); i++) {
                LatLng mLocation = new LatLng(Float.valueOf(markerList.get(i).getCoordinates().getLatitude()),
                        Float.valueOf(markerList.get(i).getCoordinates().getLongitude()));
                if (mLocation.latitude < farLeft.latitude &&
                        mLocation.longitude > farLeft.longitude &&
                        mLocation.latitude < farRight.latitude &&
                        mLocation.longitude < farRight.longitude &&
                        mLocation.latitude > nearLeft.latitude &&
                        mLocation.longitude > nearLeft.longitude &&
                        mLocation.latitude > nearRight.latitude &&
                        mLocation.longitude < nearRight.longitude) {
                    System.out.println(LOG_TAG + " Marker count: " + (++count));
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)).
                            position(mLocation).title
                            (markerList.get(i).getId().toString() + ", " + markerList.get(i).getFleetType()));
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(Constants.LOG_TAG, "onMapReady");
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);
        mMap.setOnCameraMoveStartedListener(this);
        if(markerList!=null) {
            for (int i = 0; i < markerList.size(); i++) {
                LatLng mLocation = new LatLng(Float.valueOf(markerList.get(i).getCoordinates().getLatitude()),
                        Float.valueOf(markerList.get(i).getCoordinates().getLongitude()));
                if ((lat == mLocation.latitude && (lon == mLocation.longitude))) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, Constants.ZOOM_LEVEL));
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)).
                            position(mLocation).title
                            (markerList.get(i).getId().toString() + ", " + markerList.get(i).getFleetType()))
                            .showInfoWindow();
                }
            }
        }
    }

    @Override
    public void onMapLoaded() {
        Log.d(LOG_TAG, "onMapLoaded");
    }

    /**
     * Animates to new position according to selected item in the list.
     */
    public void animateToPosition(){
        if(mViewModel.getSelectedPoiValue()!=null) {
            lat = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLatitude());
            lon = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLongitude());
            markerList = mViewModel.getTaxiList().getValue();
        }
            if(markerList!=null) {
                for (int i = 0; i < markerList.size(); i++) {
                    LatLng mLocation = new LatLng(Float.valueOf(markerList.get(i).getCoordinates().getLatitude()),
                            Float.valueOf(markerList.get(i).getCoordinates().getLongitude()));
                    if ((lat == mLocation.latitude && (lon == mLocation.longitude))) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, Constants.ZOOM_LEVEL));
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)).
                                position(mLocation).title
                                (markerList.get(i).getId().toString() + ", " + markerList.get(i).getFleetType()))
                                .showInfoWindow();
                    }
                }
            }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
            mMap.setOnMapLoadedCallback(() -> {
                Log.d(LOG_TAG, "onCameraMoveStarted - onMapLoaded");
                if(mViewModel.getSelectedPoiValue()!=null) {
                    lat = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLatitude());
                    lon = Float.valueOf(mViewModel.getSelectedPoiValue().getCoordinates().getLongitude());
                    markerList = mViewModel.getTaxiList().getValue();
                    findMapBounds();
                }
            });
    }
}
