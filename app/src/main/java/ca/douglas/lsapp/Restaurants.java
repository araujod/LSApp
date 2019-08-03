package ca.douglas.lsapp;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class Restaurants extends AppCompatActivity {

    private final String TAG = "RESTAURANTS";
    private Location userLocation;
    private double latitude = 0, longitude = 0;
    private Restaurants.DBConnectivityRestaurants receiver = new Restaurants.DBConnectivityRestaurants(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Intent i = getIntent();

        if (i != null) {
            latitude = i.getDoubleExtra("latitude", 0);
            longitude = i.getDoubleExtra("longitude", 0);
        }
        Log.d(TAG, "Lat/Long : " + latitude + " " + longitude);

        Intent push = new Intent(Restaurants.this, DownloadService.class);
        //i.putExtra("where_value",spin.getSelectedItem().toString());
        push.putExtra("table","Store");
        push.putExtra("where_key","");
        push.putExtra("where_value","");
        push.putExtra("column_name","");
        push.putExtra("new_value","");
        push.putExtra("id","");
        push.putExtra("method","GET_ALL");
        push.putExtra("setAction","Restaurants");

        startService(push);


    }



    public class DBConnectivityRestaurants extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityRestaurants(Context context) {

            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {

                String text = intent.getStringExtra("output_data");


                String columns[] = {"StoreID","Email","Phone","Name","Address","LogoURL","Lng","Lat"};

                Log.d("DB - onReceive",text);


                userLocation = new Location("user");
                userLocation.setLatitude(latitude);
                userLocation.setLongitude(longitude);



                Log.d(TAG, "Initializing restaurants...");

                ArrayList<Restaurant> restaurants =  new ArrayList<>();


                try {
                    Log.d("dataNEW",text);

                    JSONArray ar = new JSONArray(text);
                    JSONObject jobj;

                    for (int x=0; x < ar.length(); x++) {
                        jobj = ar.getJSONObject(x);
                        // getting the columns

                        Restaurant rest = new Restaurant(
                                Integer.parseInt(jobj.getString(columns[0])),
                                jobj.getString(columns[1]),
                                jobj.getString(columns[2]),
                                jobj.getString(columns[3]),
                                jobj.getString(columns[4]),
                                jobj.getString(columns[5]),
                                Double.parseDouble(jobj.getString(columns[6])),
                                Double.parseDouble(jobj.getString(columns[7]))
                        );

                        restaurants.add(rest);
                    }


                    Log.d(TAG, "Starting creation...");
                    ListView listView = findViewById(R.id.listViewRestaurant);
                    Log.d(TAG, "Creating Adapter...");
                    final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(Restaurants.this);
                    listView.setAdapter(restaurantAdapter);

                    for (Restaurant restaurant : restaurants)
                        restaurant.setDistanceFromUser(userLocation);
                    Collections.sort(restaurants);//IComparable implemented by distance
                    restaurantAdapter.setRestaurants(restaurants);




                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Restaurant restaurant = (Restaurant) parent.getAdapter().getItem(position);

                            Intent i = new Intent(Restaurants.this, ShowProducts.class);
                            i.putExtra("restaurant", restaurant);

                            startActivity(i);
                        }
                    });







                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }








    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
        final SpotsDialog waitingDialog = new SpotsDialog(Restaurants.this);
        waitingDialog.show();
        waitingDialog.setMessage("Please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(Restaurants.DBConnectivityRestaurants.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");
        final SpotsDialog waitingDialog = new SpotsDialog(Restaurants.this);
        waitingDialog.dismiss();

    }

}
