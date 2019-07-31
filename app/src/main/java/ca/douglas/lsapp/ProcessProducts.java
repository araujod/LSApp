package ca.douglas.lsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ProcessProducts extends AppCompatActivity {

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
        String []s = {"Barnebas","Lynnet","Isabeau","Carolus","Odelinda"};
        for(String names: s)
            temp.add(names);
        return temp;
    }
}
