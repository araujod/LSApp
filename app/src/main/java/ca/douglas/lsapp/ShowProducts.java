package ca.douglas.lsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DB.StoreProduct;

public class ShowProducts extends AppCompatActivity {

    Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);
        Intent i = getIntent();
        restaurant = (Restaurant)i.getSerializableExtra("restaurant");
        TextView textView = findViewById(R.id.tvStoreID);
        textView.setText(restaurant.getId()+"");

        ArrayList<StoreProduct> productsAvailable = DBUtil.getStoreProductList();

    }
}
