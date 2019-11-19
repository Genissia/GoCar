package com.example.gocar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gocar.activity.LoginActivity;
import com.example.gocar.activity.MapsActivity;
import com.example.gocar.activity.RegisterActivity;
import com.google.android.gms.maps.GoogleMap;
import com.example.gocar.helper.CarsAdapter;
import com.example.gocar.activity.Details;
import com.example.gocar.helper.SessionManager;
import com.example.gocar.helper.SQLiteHandler;
import com.example.gocar.helper.cars;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends MapsActivity implements CarsAdapter.OnCarListener{

    private TextView txtName;
    private TextView txtEmail;
    GoogleMap googleMap;
    private static final int REQUEST_CODE = 101;
    private Button btnLogout;
    private SQLiteHandler db;
    private SessionManager session;
    double deltaLat;
    double deltaLon;
    public static int Carid;
    public static String userid;
    public static String Modelname;
    View.OnClickListener listener;
    double lon;
    //a list to store all the products
    List<cars> carList;
    //the recyclerview
    RecyclerView recyclerView;
    CarsAdapter adapter;
    public double dist;
    //double lat;
    public static String URL_CAR = "http://192.168.1.6/android_login_api/cars.php";
    public static double lat;
    public static double longi;
    public Intent intent;
    public cars car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName =  findViewById(R.id.name);
        txtEmail =  findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLogout);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        //fetchLastLocation();
        carList = new ArrayList<>();
        adapter = new CarsAdapter(MainActivity.this, carList,this);

        loadProducts();
        //initializing the carlist

        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());



        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Logout button click event
        btnLogout.setOnClickListener(
                new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                double radius = 6371;   // approximate Earth radius, *in meters*
                                //deltaLat = product.getDouble("Latitude") - latfrom;

                                deltaLat = Math.toRadians(product.getDouble("Latitude") - currentLocation.getLatitude());
                                deltaLon = Math.toRadians(product.getDouble("Longitude") - currentLocation.getLongitude());
                                double angle = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                                        + Math.cos(Math.toRadians(currentLocation.getLatitude())) * Math.cos(Math.toRadians(product.getDouble("Latitude")))
                                        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
                                double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
                                dist = radius * c;
                                carList.add(new cars(
                                        product.getInt("uniqueid"),
                                        product.getString("Model_Name"),
                                        product.getInt("ProductionYear"),
                                        product.getDouble("Latitude"),
                                        product.getDouble("Longitude"),
                                        product.getInt("FuelLevel"),
                                        product.getString("image"),
                                        dist
                                ));

                                Collections.sort(carList);

//                                recyclerView.setOnClickListener(
//                                        new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//
//                                                int  position = v.getId();
//                                                oncarclick(position);
//
//                                            }
//                                        });
                                       recyclerView.setAdapter(adapter);

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    public void oncarclick(int position){

        String latitude = String.valueOf(carList.get(position).getLatitude());
        String longitude = String.valueOf(carList.get(position).getLongitude());
        Carid = carList.get(position).getuniqueid();
        Modelname = carList.get(position).getModelName();
        userid = LoginActivity.uid;
       // int caridsend = Integer.parseInt(carid);
        lat = Double.parseDouble(latitude);
        longi = Double.parseDouble(longitude);
        Intent intent = new Intent(MainActivity.this, Details.class);
        startActivity(intent);
      //  Intent j = new Intent(MainActivity.this, MapsActivity.class);
       // j.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      //  intent.putExtra("lat", latidub);
       // intent.putExtra("long", longdub);
        //startActivity(j);

      //  intent.putExtra("carid", caridsend);


    }


    private void logoutUser() {
        session.setLogin(userid,false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
