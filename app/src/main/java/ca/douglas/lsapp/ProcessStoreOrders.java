package ca.douglas.lsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class ProcessStoreOrders extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_store_orders);

        final ArrayList<Integer> temp = new ArrayList<>();

        for (int ind = 0; ind < Commom.orderStatus.size(); ind++) {
            temp.add(Commom.orderStatus.get(ind).getOrderID());
            Log.d("ORDER ID", String.valueOf(Commom.orderStatus.get(ind).getOrderID()));
        }

        StoreOrdersMasterFragment m = (StoreOrdersMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        // passing the data to the MasterFragment

        Log.d("onCreate", "ProcessStoreOrders");

        m.setTheData(temp);


    }

}