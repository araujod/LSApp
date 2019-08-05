package ca.douglas.lsapp;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderHistory;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;

public class History extends AppCompatActivity {

    private History.DBConnectivityHistory receiver = new History.DBConnectivityHistory(this);

    public static final int REQUEST_ORDER_CODE = 3000;
    private ArrayList<Order> orders;
    private HashMap<Integer, Restaurant> restaurantHashMap;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        Intent i = new Intent(History.this, DownloadService.class);
        i.putExtra("table","Product");
        i.putExtra("where_key","");
        i.putExtra("where_value","");
        i.putExtra("where_value2","");
        i.putExtra("column_name","");
        i.putExtra("new_value","");
        i.putExtra("id","");
        i.putExtra("method","GET_ALL");
        i.putExtra("setAction","History");
        startService(i);
    }

    public class DBConnectivityHistory extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityHistory(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {
//                final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//                waitingDialog.dismiss();

                String text = intent.getStringExtra("output_data");

                String columns[] = {"ProductID","Name","Description","Category","PictureURL","Price","Highlight","StoreID","available"};
                Log.d("DB - onReceive",text);

                try {
                    Log.d("dataNEW",text);
                    JSONArray ar = new JSONArray(text);
                    JSONObject jobj;
                    Commom.products.clear();
                    Commom.storeProducts.clear();

                    Boolean tempBool;
                    Commom.products.clear();
                    Commom.storeProducts.clear();

                    for (int x=0; x < ar.length(); x++) {
                        jobj = ar.getJSONObject(x);


                        Boolean highlight = false;
                        if(jobj.getInt(columns[6])==1){
                            highlight = true;
                        }


                        // getting the columns
                        if (jobj.getInt(columns[6]) == 1)
                            tempBool = Boolean.TRUE;
                        else
                            tempBool = Boolean.FALSE;
                        Product prod = new Product(Integer.parseInt(jobj.getString(columns[0])), jobj.getString(columns[1]),
                                jobj.getString(columns[2]), jobj.getString(columns[3]),
                                jobj.getString(columns[4]), Float.parseFloat(jobj.getString(columns[5])),
                                tempBool);
                        Commom.productListFromSelectedStore.add(prod);



                        Log.d("BOOLEAN",text);

                    }


/***********************************************************/
                    Intent i = new Intent(History.this, GetOrder.class);
                    startActivityForResult(i, REQUEST_ORDER_CODE);
                    /*//Adapter
                    orderAdapter = new OrderAdapter(context);
                    ListView listView = findViewById(R.id.lvOrders);
                    listView.setAdapter(orderAdapter);


                    restaurantHashMap = new HashMap<>();
                    getOrders();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Order order = (Order) parent.getAdapter().getItem(position);

                            Intent i = new Intent(History.this, Checkout.class);
                            i.putExtra("order", order);
                            i.putExtra("restaurant", restaurantHashMap.get(order.getStoreID()));
                            i.putExtra("isOrderHistory", true);
                            startActivity(i);
                        }
                    });

/*********************************************************/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_ORDER_CODE){
            IdpResponse resp = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                orderAdapter = new OrderAdapter(History.this);
                ListView listView = findViewById(R.id.lvOrders);
                listView.setAdapter(orderAdapter);

                restaurantHashMap = new HashMap<>();
                getOrders();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order order = (Order) parent.getAdapter().getItem(position);

                        Intent i = new Intent(History.this, Checkout.class);
                        i.putExtra("order", order);
                        i.putExtra("restaurant", restaurantHashMap.get(order.getStoreID()));
                        i.putExtra("isOrderHistory", true);
                        startActivity(i);
                    }
                });
            }
            else {
                Toast.makeText(this, "Something is wrong: \n"+resp.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
//        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//        waitingDialog.show();
//        waitingDialog.setMessage("Please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(History.DBConnectivityHistory.STATUS_DONE);
        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");
//        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//        waitingDialog.dismiss();


    }



    public ArrayList<Order> GetOrderListFromHistory()
    {
        ArrayList<Order> orders = new ArrayList<>();
        for (OrderHistory orderHistory:Commom.orderHistories
             ) {
            orders.add(orderHistory.getOrder());
        }

        return orders;
    }
    public Restaurant GetStoreFromHistory(int storeID)
    {
        Restaurant store=null;
        for (OrderHistory orderHistory:Commom.orderHistories
        ) {
            if(orderHistory.getStore().getId()==storeID){
                store = orderHistory.getStore();
                break;
            }
        }

        return store;
    }
    public void getOrders() {

        orders = GetOrderListFromHistory();
        for (Order order : orders) {
            if (!restaurantHashMap.containsKey(order.getStoreID())) {
                Restaurant restaurant =GetStoreFromHistory(order.getStoreID());
                restaurantHashMap.put(restaurant.getId(), restaurant);
            }

           // order.setTotal_Items(DBUtil.getCountOrderDetail(order.getOrderID()));
        }
        orderAdapter.setOrders(orders, restaurantHashMap);
    }
}
