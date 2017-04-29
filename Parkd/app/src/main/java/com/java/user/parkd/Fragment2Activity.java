package com.java.user.parkd;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Fragment2Activity extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "AutocompleteActivity";
    private SlidingUpPanelLayout mLayout;
    EditText attributeText;
    private String name;
    GoogleMap mMap;
    View view;
    String url;
    double m_price;
    String m_spaceId;
    String m_address;
    String m_postcode;
    // String location;
    String m_imageAddress;
    String m_numOfSpaces;
    String m_type;
    String m_description;
    String m_location;
    JSONArray user = null;
    private TextView mArrivalText;
    private TextView mDepartureText;
    private Date mMagicDate;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd hh:mm aa");

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            String strDate = mFormatter.format(date);
            System.out.print("break");
            mArrivalText.setText(strDate);
            mMagicDate = date;
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private SlideDateTimeListener listener2 = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            String strDate = mFormatter.format(date);
            System.out.print("break");
            mDepartureText.setText(strDate);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

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

        attributeText = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
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
                mMap.clear();

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                getMarkerParams();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*
         *  Initialising sliding up panel widget
         */
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

        mLayout.setFadeOnClickListener(view12 -> {
            //TODO do nothing
        });

        // this should be get directions button, empty for now
        Button f = (Button) view.findViewById(R.id.follow);
        f.setMovementMethod(LinkMovementMethod.getInstance());
        f.setOnClickListener(view13 -> {
            // TODO
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
        MarkerOptions mMarker = new MarkerOptions();
        // Customise the styling of the base map using a JSON object defined
        // in a raw resource file.
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json));

        // and move the map's camera to the same location. 54.597263, -5.930134
        LatLng belfast = new LatLng(54.597263, -5.930134);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(belfast, 15));

        // lat/longs around belfast for testing
        //        LatLng belfast1 = new LatLng(54.595174, -5.928575);
        //        LatLng belfast2 = new LatLng(54.596399, -5.934533);
        //        LatLng belfast3 = new LatLng(54.598316, -5.933200);
        //        LatLng belfast4 = new LatLng(54.599306, -5.924222);

        mMap.setOnMarkerClickListener(marker -> {
            String markerId;

            if (marker.getTag() != null) {
                markerId = marker.getTag().toString();
                Fragment2Activity.this.getSpaceDetails(markerId);
            } else {
                //TODO
            }
            return true;
        });

        mMap.setOnMapClickListener(latLng -> mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN));
    }

    public void getMarkerParams() {
        Response.Listener<String> responseListener = response -> {

            try {
                //Receives response from the php
                JSONArray jsonResponse = new JSONArray(response);

                if (jsonResponse.length() > 0) {
                    ArrayList<JSONObject> listdata = new ArrayList<>();

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject object = jsonResponse.getJSONObject(i);
                        listdata.add(object);
                    }

                    for (int i = 0; i < listdata.size(); i++) {
                        Double lat = Double.parseDouble(listdata.get(i).getString("lat"));
                        Double lng = Double.parseDouble(listdata.get(i).getString("lng"));
                        Double price = Double.parseDouble(listdata.get(i).getString("price"));
                        String id = listdata.get(i).getString("id");
                        generateMarkers(lat, lng, price, id);
                    }
                } else {
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
        };

        // Sends request to the php
        Fragment2ActivityRequest request = new Fragment2ActivityRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void generateMarkers(Double lat, Double lng, Double price, String id) {
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

        DecimalFormat df = new DecimalFormat("#.00");

        if (price < 2.00) {
            icon = iconFactory1.makeIcon("£" + String.format("%.2f", price));
        } else if (price > 2.00 && price < 4.00) {
            icon = iconFactory2.makeIcon("£" + String.format("%.2f", price));
        } else {
            icon = iconFactory3.makeIcon("£" + String.format("%.2f", price));
        }

        LatLng location = new LatLng(lat, lng);

        MarkerOptions belfast_marker = new MarkerOptions();
        belfast_marker.position(location);
        belfast_marker.icon(BitmapDescriptorFactory.fromBitmap(icon));
        belfast_marker.title(m_type);
        mMap.addMarker(belfast_marker).setTag(id);
    }

    public void getSpaceDetails(String id) {
        m_spaceId = id;
        url = "http://pjohnston37.students.cs.qub.ac.uk/Android/getSpaceDetails.php?id=" + id;
        new JSONParse().execute();
    }

    public class JSONParse extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            return jParser.getJSONFromUrl(url);
        }

        @Override
        protected void onPreExecute() {
            // SHOW THE SPINNER WHILE LOADING FEEDS
        }

        @Override
        protected void onPostExecute(JSONArray json) {

            try {
                // Getting JSON Array
                user = json;
                JSONObject c = user.getJSONObject(0);

                // Storing  JSON item in a Variable
                m_price = c.getDouble("price");
                m_type = c.getString("type");
                m_description = c.getString("desc");
                m_imageAddress = c.getString("image");
                m_address = c.getString("address");
                m_numOfSpaces = c.getString("num");
                m_postcode = c.getString("postcode");
                m_location = c.getString("location");

                //Set JSON Data in TextView
                TextView barText = (TextView) view.findViewById(R.id.name);
                ImageView imageHeader = (ImageView) view.findViewById(R.id.image_header);
                TextView imageText = (TextView) view.findViewById(R.id.text_header);
                TextView description = (TextView) view.findViewById(R.id.space_description);
                mArrivalText = (TextView) view.findViewById(R.id.arrival_date);
                mDepartureText = (TextView) view.findViewById(R.id.depart_date);


                barText.setText("@ " + m_address + ", " + m_postcode);
                Glide.with(getActivity()).load(m_imageAddress).into(imageHeader);
                imageText.setText(m_type);
                description.setText(m_description);

                mArrivalText.setOnClickListener(v -> new SlideDateTimePicker.Builder(getActivity()
                        .getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setMinDate(new Date())
                        .setMaxDate(returnMaxDate())
                        .build()
                        .show());

                mDepartureText.setOnClickListener(v -> new SlideDateTimePicker.Builder(getActivity()
                        .getSupportFragmentManager())
                        .setListener(listener2)
                        .setInitialDate(mMagicDate)
                        .setMinDate(mMagicDate)
                        .setMaxDate(returnMaxDate())
                        .build()
                        .show());

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //This method will parse the RAW data downloaded from the server
    }

    // this methods ensures user cannot perform bookings more than 4 months further in the future
    private Date returnMaxDate() {
        Date currentDate;
        // get current date
        Calendar cal = Calendar.getInstance();
        // add 3 months to current date
        cal.add(Calendar.MONTH, 3);

        return currentDate = cal.getTime();
    }
}
