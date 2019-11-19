package com.example.gocar.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gocar.MainActivity;
import com.example.gocar.R;
import com.example.gocar.app.AppConfig;
import com.example.gocar.app.AppController;
import com.example.gocar.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {

    private static final String TAG = Details.class.getSimpleName();
    //private TextView carmodel;
    private Button gotomap;
    private EditText yourreview;
    TextView textviewy;
    TextView simpletext;
    private Button submit;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        yourreview =  findViewById(R.id.yourreview);
        gotomap =  findViewById(R.id.gotomap);
        submit =  findViewById(R.id.submit);
        textviewy = (TextView)findViewById(R.id.textView);
        loadreviews();
        simpletext = (TextView)findViewById(R.id.simpleTextView);
        simpletext.setText(MainActivity.Modelname);
        simpletext.setTextColor(Color.BLACK);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String userid = user.get(SessionManager.KEY_userid);
        final int carid = MainActivity.Carid;

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String reviewy = yourreview.getText().toString().trim();
                // Check for empty data in the form
                Log.i("THIS IS MY ID",userid);
                if(!reviewy.isEmpty()&&!(""+carid).isEmpty()&&!userid.isEmpty()){
                    addReview(userid,carid,reviewy);
                    //Reviews reviews = new Reviews(userid,carid,review);


                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the review!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
        Log.i("amhere",""+userid);
        gotomap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
//                final double lat = intent.getDoubleExtra("latitude",0);
//                final double lon = intent.getDoubleExtra("longitude",0);
                Intent intent1  = new Intent(Details.this, MapsActivity.class);
               intent.putExtra("latitude", MainActivity.lat);
               intent.putExtra("longitude", MainActivity.longi);
                startActivity(intent1);
            }
        });


    }
    private void loadreviews() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_REVIEWS,
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
                                String UserReview = product.getString("UserReview");
                                String userid= product.getString("userid");
                                int Carid = product.getInt("Carid");
                                textviewy.setText("REVIEWS");
                                textviewy.append(" "+Carid+UserReview);
                                textviewy.setTextColor(Color.BLACK);
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

    private void addReview(final String userid,final int carid,final String userreview) {
        // Tag used to cancel the request
        String tag_string_req = "req_review";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEWS, new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Review Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //int uniqueid = jObj.getInt("UniqueId");
                        JSONObject userreview = jObj.getJSONObject("userreview");
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "review Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to reviews url
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid);
                params.put("Carid", "" + carid);
                params.put("UserReview", userreview);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    }










