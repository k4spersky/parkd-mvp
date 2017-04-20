package com.java.user.parkd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Arrays;
import java.util.List;


public class Fragment2Activity extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DemoActivity";
    private SlidingUpPanelLayout mLayout;
    private FrameLayout frameLayout;
    GoogleMap mMap;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment2, container, false);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        EditText attributeText = (EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
        attributeText.setHintTextColor(getResources().getColor(R.color.cadet_grey));
        autocompleteFragment.getView().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_search));
        attributeText.setHint("find your space!");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());

                String name = (String) place.getName();
                LatLng latLng = place.getLatLng();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(name);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pin));
                mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*
         *  SLIDING UP PANEL
         */
        ListView lv = (ListView) view.findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> your_array_list = Arrays.asList(
                "This",
                "Is",
                "An",
                "Example",
                "ListView"
        );

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);

        mLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        //change layout state to hidden for marker on click, like below
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                //TODO do nothing
            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO do nothing
            }
        });

        TextView t = (TextView) view.findViewById(R.id.name);
        t.setText("Car Park name");

        Button f = (Button) view.findViewById(R.id.follow);
        f.setMovementMethod(LinkMovementMethod.getInstance());
        f.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO
            }
        });

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

        mMap = googleMap;
        // Customise the styling of the base map using a JSON object defined
        // in a raw resource file.
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json));

        // and move the map's camera to the same location. 54.606549, -5.931456
        LatLng belfast = new LatLng(54.606549, -5.931456);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(belfast));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(belfast, 15));

        //icon generator
        IconGenerator iconFactory = new IconGenerator(getContext());
        iconFactory.setColor(getResources().getColor(R.color.eucalyptus));
        iconFactory.setTextAppearance(R.style.bubbleGeneratorText);

        // make icon
        Bitmap icon = iconFactory.makeIcon("£3.50");

        //make marker
        MarkerOptions belfast_marker = new MarkerOptions();
            belfast_marker.position(belfast);
            //belfast_marker.title("fee: £3.50").visible(true);
            belfast_marker.icon(BitmapDescriptorFactory.fromBitmap(icon));
            mMap.addMarker(belfast_marker);

       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
           @Override
           public boolean onMarkerClick(Marker marker) {
               mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
               return true;
           }
       });

       mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
           @Override
           public void onMapClick(LatLng latLng) {
               mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
           }
       });
    }
}
