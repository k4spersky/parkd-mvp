package com.java.user.parkd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Fragment2Activity extends Fragment implements OnMapReadyCallback {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment2, container, false);
        final android.widget.SearchView searchView = (android.widget.SearchView) view.findViewById(R.id.search_frag2);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // and move the map's camera to the same location. 54.606549, -5.931456
        LatLng belfast = new LatLng(54.606549, -5.931456);
        googleMap.addMarker(new MarkerOptions().position(belfast)
                .title("Belfast City Centre"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(belfast));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(belfast, 12));
    }
}
