package com.java.user.parkd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.java.user.parkd.R.drawable.email;


public class Fragment2Activity extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DemoActivity";
    private SlidingUpPanelLayout mLayout;
    private FrameLayout frameLayout;
    private ArrayList<String> datalist;
    EditText attributeText;
    private String name = "";
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

         attributeText = (EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
        attributeText.setHintTextColor(getResources().getColor(R.color.cadet_grey));
        autocompleteFragment.getView().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_search));
        attributeText.setHint("find your space!");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                 name = (String) place.getName();
                LatLng latLng = place.getLatLng();

//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title(name);
//                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pin));
//                mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                getLocs();
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

        //set screen height
        mLayout.setAnchorPoint(0.5f);

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

        // and move the map's camera to the same location. 54.597263, -5.930134
        LatLng belfast = new LatLng(54.597263, -5.930134);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(belfast);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pin));
//        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(belfast, 15));

        /*//icon generator for low prices
        IconGenerator iconFactory1 = new IconGenerator(getContext());
        iconFactory1.setColor(getResources().getColor(R.color.eucalyptus));
        iconFactory1.setTextAppearance(R.style.bubbleGeneratorText);

        //icon generator for medium prices
        IconGenerator iconFactory2 = new IconGenerator(getContext());
        iconFactory2.setColor(getResources().getColor(R.color.pastel_orange));
        iconFactory2.setTextAppearance(R.style.bubbleGeneratorText);

        //icon generator for high prices
        IconGenerator iconFactory3 = new IconGenerator(getContext());
        iconFactory3.setColor(getResources().getColor(R.color.pastel_red));
        iconFactory3.setTextAppearance(R.style.bubbleGeneratorText);

        // make icons
        Bitmap iconLow = iconFactory1.makeIcon("£1.85");
        Bitmap iconMed = iconFactory2.makeIcon("£3.50");
        Bitmap iconHigh = iconFactory3.makeIcon("£5.75");

        // lat/longs around belfast for testing
        LatLng belfast1 = new LatLng(54.595174, -5.928575);
        LatLng belfast2 = new LatLng(54.596399, -5.934533);
        LatLng belfast3 = new LatLng(54.598316, -5.933200);
        LatLng belfast4 = new LatLng(54.599306, -5.924222);*/

        //make markers for testing
       /* MarkerOptions belfast_marker = new MarkerOptions();
            belfast_marker.position(belfast1);
            belfast_marker.icon(BitmapDescriptorFactory.fromBitmap(iconLow));
            mMap.addMarker(belfast_marker);

        MarkerOptions belfast_marker2 = new MarkerOptions();
            belfast_marker2.position(belfast2);
            belfast_marker2.icon(BitmapDescriptorFactory.fromBitmap(iconLow));
            mMap.addMarker(belfast_marker2);

        MarkerOptions belfast_marker3 = new MarkerOptions();
            belfast_marker3.position(belfast3);
            belfast_marker3.icon(BitmapDescriptorFactory.fromBitmap(iconMed));
            mMap.addMarker(belfast_marker3);

        MarkerOptions belfast_marker4 = new MarkerOptions();
            belfast_marker4.position(belfast4);
            belfast_marker4.icon(BitmapDescriptorFactory.fromBitmap(iconHigh));
            mMap.addMarker(belfast_marker4);*/

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

    public void getLocs()
    {
        Toast.makeText(getActivity(), "Here", Toast.LENGTH_SHORT).show();
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response) {

                try {
                    Toast.makeText(getActivity(), "HEre", Toast.LENGTH_SHORT).show();
                    //Receives response from the php
                    JSONArray jsonResponse = new JSONArray(response);

                    if (jsonResponse.length() >0)
                    {
                        ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject object = jsonResponse.getJSONObject(i);
                            listdata.add(object);
                        }


                        for(int i = 0; i<listdata.size(); i++ ){

                            Double lat = Double.parseDouble(listdata.get(i).getString("lat"));
                            Double lng = Double.parseDouble(listdata.get(i).getString("lng"));
                            Double price = Double.parseDouble(listdata.get(i).getString("price"));
                            Toast.makeText(getActivity(), lat + " " + lng + " " + price, Toast.LENGTH_SHORT).show();
                            generateMarkers(lat, lng, price);
                        }

                    }else{
                        //Alerts the user of failure and asks for them retry
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No Spaces found")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // Sends request to the php
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        Fragment2ActivityRequest request = new Fragment2ActivityRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void generateMarkers(Double lat, Double lng, Double price)
    {
        Bitmap icon;

        IconGenerator iconFactory1 = new IconGenerator(getContext());
        iconFactory1.setColor(getResources().getColor(R.color.eucalyptus));
        iconFactory1.setTextAppearance(R.style.bubbleGeneratorText);

        //icon generator for medium prices
        IconGenerator iconFactory2 = new IconGenerator(getContext());
        iconFactory2.setColor(getResources().getColor(R.color.pastel_orange));
        iconFactory2.setTextAppearance(R.style.bubbleGeneratorText);

        //icon generator for high prices
        IconGenerator iconFactory3 = new IconGenerator(getContext());
        iconFactory3.setColor(getResources().getColor(R.color.pastel_red));
        iconFactory3.setTextAppearance(R.style.bubbleGeneratorText);

        if (price < 2.00)
        {
             icon = iconFactory1.makeIcon("£" + price.toString());
        }
        else if(price > 2.00 && price < 4.00)
        {
            icon = iconFactory2.makeIcon("£" + price.toString());
        }
        else
        {
            icon = iconFactory3.makeIcon("£" + price.toString());
        }

        LatLng loc = new LatLng(lat, lng);

        MarkerOptions belfast_marker = new MarkerOptions();
        belfast_marker.position(loc);
        belfast_marker.icon(BitmapDescriptorFactory.fromBitmap(icon));
        mMap.addMarker(belfast_marker);

    }
}
