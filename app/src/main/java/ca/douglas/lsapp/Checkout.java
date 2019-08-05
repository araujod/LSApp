package ca.douglas.lsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DB.User;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class Checkout extends AppCompatActivity {

    private Checkout.DBConnectivityCheckout receiver = new Checkout.DBConnectivityCheckout(this);


    private Order order;
    private ArrayList<OrderDetail> orderDetails;
    private Restaurant restaurant;
    private boolean isOrderHistory;
    private TextView txtDate, txtCheckoutAddress, txtType, txtCheckoutTime, txtDelivery, txtDeliveryFee, txtSubtotalPrice, txtTotalPrice;
    private ConstraintLayout clBottom;
    private TextView txtBottomButton, txtProductsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        orderDetails = (ArrayList<OrderDetail>) intent.getSerializableExtra("orderDetails");
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        isOrderHistory = intent.getBooleanExtra("isOrderHistory", false);

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Set title of action bar
        setTitle(restaurant.getName());

        txtDate = findViewById(R.id.txtDate);
        txtType = findViewById(R.id.txtType);
        txtCheckoutAddress = findViewById(R.id.txtCheckoutAddress);
        txtCheckoutTime = findViewById(R.id.txtCheckoutTime);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtDeliveryFee = findViewById(R.id.txtDeliveryFee);
        txtSubtotalPrice = findViewById(R.id.txtSubtotalPrice);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        clBottom = findViewById(R.id.clBottom);
        txtBottomButton = findViewById(R.id.txtBottomButton);
        txtProductsCount = findViewById(R.id.txtProductsCount);

        if (isOrderHistory) {
            txtDate.setText(order.getDateTime());
            txtBottomButton.setText("View products");
            txtProductsCount.setText(Integer.toString(order.getTotal_Items()));
        } else {
            Commom.hideComponentInConstraintLayout(txtDate);
            txtProductsCount.setText(Integer.toString(orderDetails.size()));
            order.setTotal_Items(orderDetails.size());
        }

        txtType.setText("Delivery");
        txtCheckoutAddress.setText(order.getDeliveryAddress());
        txtDeliveryFee.setText(Commom.getCurrencyFormatted(Commom.DELIVERY_FEE));
        txtTotalPrice.setText(Commom.getCurrencyFormatted(order.getTotal() + Commom.DELIVERY_FEE));

        txtSubtotalPrice.setText(Commom.getCurrencyFormatted(order.getTotal()));

        clBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOrderHistory) {
                    Intent i = new Intent(Checkout.this, OrderDetailView.class);
                    i.putExtra("order", order);
                    i.putExtra("restaurant", restaurant);
                    i.putExtra("isOrderHistory", true);
                    startActivity(i);
                } else {
                    // Save order, order details and recommendation if there is a discount
                    order.setDateTime("");


                    final SpotsDialog waitingDialog = new SpotsDialog(Checkout.this);
                    waitingDialog.show();
                    waitingDialog.setMessage("Please wait...");


                    String OrderDetail1="";
                    for(int x=0 ; x<orderDetails.size();x++){

                        OrderDetail1+=orderDetails.get(x).getValuesAsString();

                    }




                    //GET ALL THE PRODUCTS AVAILABLE FOR A GIVEN STORE
                    Intent i2 = new Intent(Checkout.this, DownloadService.class);
                    i2.putExtra("table","Orders");
                    i2.putExtra("where_key","");
                    i2.putExtra("where_value", order.getValuesAsStringOrder());
                    i2.putExtra("where_value2",OrderDetail1);
                    i2.putExtra("column_name","");
                    i2.putExtra("new_value","");
                    i2.putExtra("id","");
                    i2.putExtra("method","POST_ORDER");
                    i2.putExtra("setAction","Checkout");
                    startService(i2);











                    //TODO: INSERT ORODER
                    //TODO: INSERT ORDER DETAILS


                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    String orderCollection = "OrderStatus";

                    //TODO: INSERT ORDER
                    //TODO: INSERT ORDER DETAILS

                    //FIREBASE
                    Map<String, Object> user = new HashMap<>();
                    //ORDER ID SHOULD COME FROM MY SQL DATABASE
                    order.setOrderID(0);
                    user.put("OrderID", order.getOrderID());
                    user.put("OrderStatus", "OP");//STATUS OP Order Posted OA Order Accepted OR Order Rejected OD Order  Delivery
                    user.put("StoreID", order.getStoreID());
                    user.put("UserID", order.getUserID());

                    Log.d("Checkout", "Order added FIREBASE");
                    // Add a new document with User ID authenticated
                    db.collection(orderCollection).document(order.getOrderID()+"")
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    Log.d("Checkout", "Success");
                                    Toast.makeText(Checkout.this.getApplicationContext(), R.string.orderComplete, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Checkout.this, ClientHome.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    waitingDialog.dismiss();
                                    Log.d("Checkout", "Alert failure");
                                    Toast.makeText(Checkout.this, "Error registering order!", Toast.LENGTH_LONG).show();
                                    Log.w("Checkout", "Error registering order", e);
                                }
                            });

                }
            }
        });
    }











    public class DBConnectivityCheckout extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityCheckout(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {


                String text = intent.getStringExtra("output_data");

                String columns[] = {"ProductID", "Name", "Description", "Category", "PictureURL", "Price", "Highlight", "StoreID", "available"};
                Log.d("DB - onReceive", text);


            }
        }
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
        IntentFilter filter = new IntentFilter(Checkout.DBConnectivityCheckout.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");



    }











}
