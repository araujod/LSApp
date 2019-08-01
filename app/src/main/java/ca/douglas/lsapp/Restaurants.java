package ca.douglas.lsapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Restaurant;

public class Restaurants extends AppCompatActivity {

    private final String TAG = "RESTAURANTS";
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Intent i = getIntent();
        double latitude = 0, longitude = 0;
        if (i != null) {
            latitude = i.getDoubleExtra("latitude", 0);
            longitude = i.getDoubleExtra("longitude", 0);
        }
        Log.d(TAG, "Lat/Long : " + latitude + " " + longitude);

        userLocation = new Location("user");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        Log.d(TAG, "Starting creation...");
        ListView listView = findViewById(R.id.listViewRestaurant);
        Log.d(TAG, "Creating Adapter...");
        final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this);
        listView.setAdapter(restaurantAdapter);

        Log.d(TAG, "Initializing restaurants...");

        ArrayList<Restaurant> restaurants = DBUtil.getRestaurantList();

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

    }
}
