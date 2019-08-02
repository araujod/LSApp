package ca.douglas.lsapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import ca.douglas.lsapp.DownloadService.DBConnectivity;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class ProcessProducts extends AppCompatActivity {

    private DBConnectivity receiver = new DBConnectivity(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_products);

        // assume the data is retrieve from somewhere
        ArrayList<String> data = populateData();

        ProductMasterFragment m = (ProductMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        // passing the data to the MasterFragment
        Log.d("onCreate","ProcessProducts");
        m.setTheData(data);
    }

    private ArrayList<String> populateData() {
        ArrayList<String> temp = new ArrayList<>();

        Intent i = new Intent(ProcessProducts.this, DownloadService.class);
        //i.putExtra("where_value",spin.getSelectedItem().toString());
        i.putExtra("table","Product");
        startService(i);

        String []x = new String[42];
        for (int ind = 0; ind < Commom.Products.length; ind++)
        x[ind] = Commom.Products[ind][2];

        String s[] = {"Barnebas","Lynnet","Isabeau","Carolus","Odelinda"};
        for(String names: s)
            temp.add(names);

        return temp;
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);
    }
}
