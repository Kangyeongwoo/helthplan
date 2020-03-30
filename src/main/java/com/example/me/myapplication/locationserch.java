package com.example.me.myapplication;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class locationserch extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    GoogleMap mMap;
    double mylatitude;
    double mylongitude;
    LatLng mylocation;

    String serchname;
    String serchid;
    LatLng serchlatlng;
    String serchadress;
    String serchnumber;
    String resultText;
    double serchlat;
    double serchlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationserch);

        ImageButton locationserchbackbutton = findViewById(R.id.locationserchbackbutton);
        locationserchbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("placetest", "Place: " + place.getId());

                serchname = place.getName();
                serchid = place.getId();
                try {
                    resultText=new Task2(serchid).execute().get();

                    try{
                        JSONObject jsonObject = new JSONObject(resultText);

                        String parseresult = jsonObject.getString("result");

                        JSONObject subJsonObject2 = new JSONObject(parseresult);

                        serchnumber = subJsonObject2.getString("formatted_phone_number");

                        serchadress = subJsonObject2.getString("formatted_address");

                        String geometry = subJsonObject2.getString("geometry");

                        JSONObject subJsonObject3 = new JSONObject(geometry);

                        String location = subJsonObject3.getString("location");

                        JSONObject subJsonObject4 = new JSONObject(location);

                        serchlat = Double.parseDouble(subJsonObject4.getString("lat"));
                        serchlng = Double.parseDouble(subJsonObject4.getString("lng"));

                        serchlatlng=new LatLng(serchlat,serchlng);

                        onMapReady(mMap);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("placetest", "An error occurred: " + status);
            }
        });

        if (ActivityCompat.checkSelfPermission(locationserch.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationserch.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;

        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(locationserch.this);
        Task task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener((OnSuccessListener<Location>) location -> {
            if (location != null) {
                //Write your implemenation here

                mylatitude = location.getLatitude();
                mylongitude = location.getLongitude();

                mylocation = new LatLng(mylatitude, mylongitude);
                Log.d("latlngtest", String.valueOf(mylocation));
                //   liklyhoodlist.add(mylocation);

            }
            onMapReady(mMap);
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Marker> markerlist = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
            // Show rationale and request permission.
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        } catch (NullPointerException e) {

        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));



        if (serchlatlng != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(serchlatlng);
            markerOptions.title(serchname);
            markerOptions.snippet("주소 : " + serchadress + "\n전화번호 : " + serchnumber);
            markerlist.add(mMap.addMarker(markerOptions));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(serchlatlng));
            markerlist.get(0).showInfoWindow();
        } else {

        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(locationserch.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(locationserch.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(locationserch.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    public class Task2 extends AsyncTask<String, Integer, String> {

        String clientKey = "AIzaSyCM_wygaB3RdtdAcdFpDHOTIo_Y5stCI-Q";
        ;
        private String str, receiveMsg;
        String placeid;


        Task2(String placeid) {
            this.placeid = placeid;
        }


        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&fields=formatted_address,formatted_phone_number,geometry&language=ko&key=" + clientKey);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", String.valueOf(url));

                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return receiveMsg;

        }
    }
}

