package com.example.me.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


//주변 헬스장을 리사이클러뷰로 보여줌
public class Helthlocation extends AppCompatActivity {

    ImageButton tomapbutton;
    ImageButton toserchbutton;
    ImageButton helthlocationbackbutton;
    PlacesClient placesClient;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    TextView serchtest;

    double mylatitude;
    double mylongitude;
    String resultText = "값이없음";
    int count=1;
    ArrayList<String[]> resultlist;
    ProgressBar progressBar4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helthlocation);

        toserchbutton =(ImageButton)findViewById(R.id.toserchbutton);
        tomapbutton = (ImageButton)findViewById(R.id.tomapbutton);
        helthlocationbackbutton=(ImageButton)findViewById(R.id.helthlocationbackbutton);
       progressBar4 =findViewById(R.id.progressBar4);

        toserchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toserchintent = new Intent(Helthlocation.this, locationserch.class);
                startActivity(toserchintent);
            }
        });



        helthlocationbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle aBundle = appInfo.metaData;
        String aValue = aBundle.getString("com.google.android.geo.API_KEY");


        Places.initialize(getApplicationContext(), aValue);
        placesClient = Places.createClient(this);


        if (ActivityCompat.checkSelfPermission(Helthlocation.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Helthlocation.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Helthlocation.this);
        com.google.android.gms.tasks.Task task2 = fusedLocationProviderClient.getLastLocation();
        task2.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    //Write your implemenation here

                    mylatitude =location.getLatitude();
                    mylongitude=location.getLongitude();

                }
                try {
                    resultText = new Task(mylatitude,mylongitude).execute().get();

                    Helthlocation.this.onResume();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });








    }

    @Override
    protected void onResume() {
        super.onResume();

        if(count==2) {



            resultlist =new ArrayList<>();
            try{

                JSONObject jsonObject = new JSONObject(resultText);


                String parseresult = jsonObject.getString("results");

                JSONArray jsonArray = new JSONArray(parseresult);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject subJsonObject = jsonArray.getJSONObject(i);

                    String name = subJsonObject.getString("name");

                    String address = subJsonObject.getString("vicinity");

                    String placeid = subJsonObject.getString("place_id");

                    String geometry = subJsonObject.getString("geometry");
                    JSONObject subJsonObject2 = new JSONObject(geometry);
                    String location = subJsonObject2.getString("location");
                    JSONObject subJsonObject3 = new JSONObject(location);
                    String latitude = subJsonObject3.getString("lat");
                    String lngitide = subJsonObject3.getString("lng");

                    String photoreference;
                    if(!subJsonObject.isNull("photos"))
                    {
                        String photo =subJsonObject.getString("photos");
                        JSONArray jsonArray2 = new JSONArray(photo);
                        JSONObject subJsonObject4 = jsonArray2.getJSONObject(0);
                        photoreference = subJsonObject4.getString("photo_reference");

                    }
                    else
                    {
                        photoreference="~";
                    }


                    String[] arraysum = new String[6];
                    arraysum[0] = name;
                    arraysum[1] = address;
                    arraysum[2] = latitude;
                    arraysum[3] = lngitide;
                    arraysum[4] = placeid;
                    arraysum[5] = photoreference;

                    resultlist.add(arraysum);




                }



            }catch (JSONException e){

            }
            progressBar4.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.helthlocationrecycle);
            RecyclerViewAdapter_Helthlocation adapter = new RecyclerViewAdapter_Helthlocation(this, resultlist, R.layout.row_healthlocation);
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());


        //    Log.d("placetest", String.valueOf(resultlist.get(0)[0]+" ### "+resultlist.get(0)[1]+" ### "+resultlist.get(0)[2]+" ### "+resultlist.get(0)[3]+" ### "+resultlist.get(0)[4]+" ### "+resultlist.get(0)[5]));

            tomapbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tomapintent = new Intent(Helthlocation.this, Map.class);
                    tomapintent.putExtra("resultlist",resultlist);
                    startActivity(tomapintent);
                }
            });
        }

        count++;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class Task extends AsyncTask<String, Integer, String> {

        String clientKey = "AIzaSyCM_wygaB3RdtdAcdFpDHOTIo_Y5stCI-Q";;
        private String str, receiveMsg;
        double mylatitude;
        double mylongitude;

        Task(double mylatitude,double mylongitude){
            this.mylatitude=mylatitude;
            this.mylongitude=mylongitude;
        }


        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+mylatitude+","+mylongitude+"&radius=1500&type=gym&language=ko&key="+clientKey);

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
