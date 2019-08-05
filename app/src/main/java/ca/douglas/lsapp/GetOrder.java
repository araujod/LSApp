package ca.douglas.lsapp;

import android.app.Activity;
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

import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.OrderHistory;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;

public class GetOrder extends AppCompatActivity {
    private GetOrder.DBConnectivityGetOrder receiver = new GetOrder.DBConnectivityGetOrder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order);

        Intent i = new Intent(GetOrder.this, DownloadService.class);
        i.putExtra("table","");
        i.putExtra("where_key", Commom.currentUser.getId());
        i.putExtra("where_value","");
        i.putExtra("where_value2","");
        i.putExtra("column_name","");
        i.putExtra("new_value","");
        i.putExtra("id","");
        i.putExtra("method","GET_ORDHISTORY");
        i.putExtra("setAction","GetOrder");
        startService(i);
    }
    public class DBConnectivityGetOrder extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityGetOrder(Context context) {
            this.context = context;
        }

        public int FindOrderHistoryByOrderID(int orderID)
        {
            for (int i=0; i<Commom.orderHistories.size();i++) {
                if(Commom.orderHistories.get(i).getOrder().getOrderID()==orderID)
                {
                    return i;
                }
            }
            return -1;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {
//                final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//                waitingDialog.dismiss();
                Commom.orderHistories.clear();

                String text = intent.getStringExtra("output_data");
                                  //"OrderID","StoreID","UserID","DateTime","Total","Total_Items","DeliveryAdd","ProductID","Quantity","SubTotal","Email","Phone","Name","Address","LogoURL","Lng","Lat"
                String columns[] = {"OrderID","StoreID","UserID","DateTime","Total","Total_Items","DeliveryAdd","ProductID","Quantity","SubTotal","Email","Phone","Name","Address","LogoURL","Lng","Lat"};
                Log.d("DB - onReceive",text);
                try {
                    Log.d("dataNEW",text);
                    JSONArray ar = new JSONArray(text);
                    JSONObject jobj;
                    Commom.orderHistories.clear();

                    OrderHistory orderHistory;
                    Order order;
                    Restaurant restaurant;
                    for (int x=0; x < ar.length(); x++) {
                        jobj = ar.getJSONObject(x);

                        int OrderID = Integer.parseInt(jobj.getString(columns[0]));
                        int StoreID = Integer.parseInt(jobj.getString(columns[1]));
                        String UserID =  jobj.getString(columns[2]);
                        String DateTime = jobj.getString(columns[3]);
                        float Total = Float.parseFloat(jobj.getString(columns[4]));
                        int Total_Items = Integer.parseInt(jobj.getString(columns[5]));
                        String deliveryAddress = jobj.getString(columns[6]);
                        int ProductID= Integer.parseInt(jobj.getString(columns[7]));
                        int Quantity= Integer.parseInt(jobj.getString(columns[8]));
                        float SubTotal= Float.parseFloat(jobj.getString(columns[9]));
                        String email=jobj.getString(columns[10]);
                        String phone=jobj.getString(columns[11]);
                        String name=jobj.getString(columns[12]);
                        String address=jobj.getString(columns[13]);
                        String LogoUrl=jobj.getString(columns[14]);
                        double longitude=0.0;
                        double latitude=0.0;

                        int orderHistoryIndex = FindOrderHistoryByOrderID(OrderID);

                        OrderDetail orderDetail = new OrderDetail(OrderID,ProductID,Quantity,SubTotal);
                        ArrayList<OrderDetail> orderDetails;

                        if(orderHistoryIndex==-1)
                        {
                            orderHistory = new OrderHistory();
                            // int OrderID, int StoreID, String UserID, String DateTime, float Total, int Total_Items, String deliveryAddress
                            order = new Order(OrderID, StoreID,UserID,DateTime,Total,Total_Items,deliveryAddress);
                            restaurant = new Restaurant(StoreID,email,phone,name,address,LogoUrl,longitude,latitude);
                            orderDetails = new ArrayList<>();
                        }
                        else
                        {
                            orderHistory = Commom.orderHistories.get(orderHistoryIndex);
                            order = orderHistory.getOrder();
                            restaurant = orderHistory.getStore();
                            orderDetails = orderHistory.getOrderDetails();
                        }

                        orderDetails.add(orderDetail);

                        orderHistory.setOrder(order);
                        orderHistory.setOrderDetails(orderDetails);
                        orderHistory.setStore(restaurant);
                        if(orderHistoryIndex==-1){
                            Commom.orderHistories.add(orderHistory);
                        }
                        else
                        {
                            Commom.orderHistories.set(orderHistoryIndex,orderHistory);
                        }

                    }


/***********************************************************/
                GetOrder.this.setResult(Activity.RESULT_OK, null);
                finish();
/***********************************************************/


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
//        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//        waitingDialog.show();
//        waitingDialog.setMessage("Please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(GetOrder.DBConnectivityGetOrder.STATUS_DONE);
        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");
//        final SpotsDialog waitingDialog = new SpotsDialog(ProcessProducts.this);
//        waitingDialog.dismiss();


    }

}
