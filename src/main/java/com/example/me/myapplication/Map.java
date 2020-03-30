package com.example.me.myapplication;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

//지도가 보일 액티비티
public class Map extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private GoogleApiClient googleApiClient;

    Button testbutton;
    double mylatitude;
    double mylongitude;
    int count=1;
    GoogleMap mMap;
    View mapView;
    LatLng mylocation;
    ArrayList<LatLng> liklyhoodlist ;
    PlacesClient placesClient;
    ArrayList<String> namelist ;
    ArrayList<String> addresslist ;
    String focus = "off";
    String focusname = "me";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d("testmap", "oncreate");

        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle aBundle = appInfo.metaData;
        String aValue = aBundle.getString("com.google.android.geo.API_KEY");

        Places.initialize(getApplicationContext(), aValue);

        liklyhoodlist=new ArrayList<>();
        namelist=new ArrayList<>();
        addresslist=new ArrayList<>();
// Create a new Places client instance.
        placesClient = Places.createClient(this);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();



        ActivityCompat.requestPermissions(Map.this,
                new String[]{ACCESS_FINE_LOCATION}, 1);

        if( getIntent().getStringExtra("focus")!=null) {
            focus = getIntent().getStringExtra("focus");
        }
        if(getIntent().getStringExtra("focusname")!=null) {
            focusname = getIntent().getStringExtra("focusname");
        }

        ArrayList<String[]> resultlist = (ArrayList<String[]>)getIntent().getSerializableExtra("resultlist");
        Log.d("latlngtest", String.valueOf(resultlist));
        for(int i=0;i<resultlist.size();i++){

            String[] gym = resultlist.get(i);
            double latitude = Double.parseDouble(gym[2]);
            double lngitude = Double.parseDouble(gym[3]);
            LatLng gymlocation = new LatLng(latitude,lngitude);
            liklyhoodlist.add(gymlocation);
            namelist.add(gym[0]);
            addresslist.add(gym[1]);

        }


//        Log.d("latlngtest", String.valueOf(liklyhoodlist.get(0)));


        ImageButton mapbackbutton = (ImageButton) findViewById(R.id.mapbackbutton);

        mapbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0] == ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // Show rationale and request permission.
                }
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    public void onStart() {
        super.onStart();
        // Initiating the connection
        googleApiClient.connect();
    }

    public void onResume() {
        super.onResume();

        Log.d("AndroidClarified","onresume");
        // Initiating the connection
        if (ActivityCompat.checkSelfPermission(Map.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Map.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;

        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Map.this);
        Task task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener((OnSuccessListener<Location>) location -> {
            if(location!=null) {
                //Write your implemenation here

                mylatitude=location.getLatitude();
                mylongitude=location.getLongitude();

                mylocation = new LatLng(mylatitude, mylongitude);
                Log.d("latlngtest", String.valueOf(mylocation));
             //   liklyhoodlist.add(mylocation);

            } onMapReady(mMap);
        });


        if(count==2) {
            Log.d("AndroidClarified", mylatitude + " " + mylongitude);

        }

        count++;

    }




    public void onStop() {
        super.onStop();
        // Disconnecting the connection
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
       ArrayList<Marker> markerlist =new ArrayList<>();
       int focusindex = -1;

            mMap = googlemap;
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
            // Show rationale and request permission.
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
            // 서울 여의도에 대한 위치 설정

        for(int i=0; i<liklyhoodlist.size();i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(liklyhoodlist.get(i));
            markerOptions.title(namelist.get(i));
            markerOptions.snippet(addresslist.get(i));

            if(focus.equals("on")){
                if(focusname.equals(namelist.get(i))){
                    focusindex=i;
                }
            }

           markerlist.add(mMap.addMarker(markerOptions));
        }


        if(focusindex!=-1){
            markerlist.get(focusindex).showInfoWindow();
        }



        //카메라를 여의도 위치로 옮긴다.
          try {
              mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
          }catch (NullPointerException e){

          }
          mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(Map.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(Map.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(Map.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });





        }



    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }







}

