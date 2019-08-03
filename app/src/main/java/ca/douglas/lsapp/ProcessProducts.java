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
import java.util.Timer;
import java.util.TimerTask;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class ProcessProducts extends AppCompatActivity {

    private DBConnectivityProducts receiver = new DBConnectivityProducts(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_products);

        Intent i = new Intent(ProcessProducts.this, DownloadService.class);
        i.putExtra("table","");
        i.putExtra("where_key","");
        i.putExtra("where_value","001LiquorStore@gmail.com");
        i.putExtra("column_name","");
        i.putExtra("new_value","");
        i.putExtra("id","");
        i.putExtra("method","GET_PRODAV");
        i.putExtra("setAction","ProdAvailable");
        startService(i);

    }

    public class DBConnectivityProducts extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityProducts(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {

                String text = intent.getStringExtra("output_data");

                String columns[] = {"ProductID","Name","Description","Category","PictureURL","Price","Highlight","StoreID","available"};
                Log.d("DB - onReceive",text);
                String []p = new String[42];

                try {
                    Log.d("dataNEW",text);
                    JSONArray ar = new JSONArray(text);
                    JSONObject jobj;

                    for (int x=0; x < ar.length(); x++) {
                        jobj = ar.getJSONObject(x);
                        // getting the columns
                        Product prod = new Product(Integer.parseInt(jobj.getString(columns[0])), jobj.getString(columns[1]),
                                                      jobj.getString(columns[2]), jobj.getString(columns[3]),
                                                      jobj.getString(columns[4]), Double.parseDouble(jobj.getString(columns[5])),
                                                      Boolean.parseBoolean(jobj.getString(columns[6])));
                        Commom.products.add(prod);

                        StoreProduct storeprod = new StoreProduct(Integer.parseInt(jobj.getString(columns[7])),
                                Integer.parseInt(jobj.getString(columns[0])),
                                Boolean.parseBoolean(jobj.getString(columns[8])));
                        Commom.storeProducts.add(storeprod);

                    }

                    final ArrayList<String> temp = new ArrayList<>();

                    for (int ind = 0; ind < Commom.products.size(); ind++) {
                        temp.add(Commom.products.get(ind).getName());
                    }

                    ProductMasterFragment m = (ProductMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
                    // passing the data to the MasterFragment

                    Log.d("onCreate","ProcessProducts");

                    m.setTheData(temp);



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
        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
        waitingDialog.show();
        waitingDialog.setMessage("Please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivityProducts.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");
        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
        waitingDialog.dismiss();


    }
}
