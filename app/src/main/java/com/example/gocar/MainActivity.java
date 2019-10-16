package com.example.gocar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gocar.activity.LoginActivity;
import com.example.gocar.activity.MapsActivity;
import com.example.gocar.helper.CarsAdapter;
import com.example.gocar.helper.SessionManager;
import com.example.gocar.helper.SQLiteHandler;
import com.example.gocar.helper.cars;
import com.google.android.gms.maps.GoogleMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends MapsActivity implements CarsAdapter.OnCarListener{

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    GoogleMap googleMap;
    private SQLiteHandler db;
    private SessionManager session;
    private static final int REQUEST_CODE = 101;
    double deltaLat;
    double deltaLon;
    //a list to store all the products
    List<cars> carList;
    //the recyclerview
    RecyclerView recyclerView;
    CarsAdapter adapter;

    public double dist;
    View.OnClickListener listener;
    //double lat;
    double lon;
    public static String URL_CAR = "http://192.168.1.8/android_login_api/cars.php";
    public Intent intent;
    public cars car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName =  findViewById(R.id.name);
        txtEmail =  findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLogout);
        //btnmaps =  findViewById(R.id.map);
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

        // SqLite database handler
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
       // String id = String.valueOf(carList.get(position).getModelName());
        String name = carList.get(position).getModelName();
        String year = String.valueOf(carList.get(position).getProductionYear());
        String latitude = String.valueOf(carList.get(position).getLatitude());
        String longitude = String.valueOf(carList.get(position).getLongitude());
        Double latit = Double.parseDouble(latitude);
        Double longit = Double.parseDouble(longitude);
        String image = carList.get(position).getImage();
        String fuel = String.valueOf(carList.get(position).getFuelLevel());
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("year", year);
        intent.putExtra("latitude", latit);
        intent.putExtra("longitude", longit);
        intent.putExtra("image", image);
        intent.putExtra("fuel", fuel);
        startActivity(intent);
        //finish();
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
