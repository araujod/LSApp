package ca.douglas.lsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
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

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
@SuppressWarnings("unchecked")
public class ShowProducts extends AppCompatActivity {

    private ShowProducts.DBConnectivityShowProducts receiver = new ShowProducts.DBConnectivityShowProducts(this);

    private static final String TAG = "PRODUCTS";
    private static final int DETAIL_ACTIVITY_REQUEST = 1;
    private static final int CHECKOUT_ACTIVITY_REQUEST = 2;
    private Order order;
    private Restaurant restaurant;
    private ArrayList<OrderDetail> orderDetails;
    private ProductAdapter productAdapter;
    private ConstraintLayout clViewOrder;
    private TextView txtProductsNum;
    private TextView txtTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);
        Intent i = getIntent();
        restaurant = (Restaurant)i.getSerializableExtra("restaurant");
        order = (Order) i.getSerializableExtra("order");

        orderDetails = new ArrayList<>();

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Set title of action bar
        setTitle(restaurant.getName());



        //GET ALL THE PRODUCTS AVAILABLE FOR A GIVEN STORE
        Intent i2 = new Intent(ShowProducts.this, DownloadService.class);
        i2.putExtra("table","");
        i2.putExtra("where_key","");
        i2.putExtra("where_value",restaurant.getEmail());
        i2.putExtra("where_value2","");
        i2.putExtra("column_name","");
        i2.putExtra("new_value","");
        i2.putExtra("id","");
        i2.putExtra("method","GET_PRODAVUSER");
        i2.putExtra("setAction","ShowProdAvailable");
        startService(i2);



    }







    public class DBConnectivityShowProducts extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityShowProducts(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {


                String text = intent.getStringExtra("output_data");

                String columns[] = {"ProductID","Name","Description","Category","PictureURL","Price","Highlight","StoreID","available"};
                Log.d("DB - onReceive",text);

                //Array to store available products
                ArrayList<Product> productsAvailable = new ArrayList<>();

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

                        Boolean available = false;
                        if(jobj.getInt(columns[8])==1){
                            available = true;
                        }

                        Boolean highlight = false;
                        if(jobj.getInt(columns[6])==1){
                            highlight = true;
                        }


                        // getting the columns
                        if (jobj.getInt(columns[6]) == 1)
                            tempBool = Boolean.TRUE;
                        else
                            tempBool = Boolean.FALSE;



                        String imageUrl = jobj.getString(columns[4]);
                        imageUrl=  imageUrl.toLowerCase();
                        String[] imageUrl2 = imageUrl.split(".png");


                        Product prod = new Product(Integer.parseInt(jobj.getString(columns[0])), jobj.getString(columns[1]),
                                jobj.getString(columns[2]), jobj.getString(columns[3]),
                                imageUrl2[0], Float.parseFloat(jobj.getString(columns[5])),
                                tempBool);
//                        Commom.products.add(prod);
//



                        productsAvailable.add(prod);


                    }



                    Commom.productListFromSelectedStore =productsAvailable;

                    ListView listView = findViewById(R.id.listViewProducts);
                    productAdapter = new ProductAdapter(context);
                    listView.setAdapter(productAdapter);
                    productAdapter.setProducts(productsAvailable);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product product = (Product) parent.getAdapter().getItem(position);
                            Intent i = new Intent(ShowProducts.this, ProductDetail.class);
                            i.putExtra("product", product);
                            startActivityForResult(i, DETAIL_ACTIVITY_REQUEST);
                        }
                    });

                    clViewOrder = findViewById(R.id.clViewOrder);
                    txtProductsNum = findViewById(R.id.txtProductsNum);
                    txtTotalPrice = findViewById(R.id.txtTotalPrice);

                    updateOrder();
                    clViewOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(ShowProducts.this, OrderDetailView.class);
                            i.putExtra("order", order);
                            i.putExtra("orderDetails", orderDetails);
                            i.putExtra("restaurant", restaurant);
                            startActivityForResult(i, CHECKOUT_ACTIVITY_REQUEST);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(ShowProducts.DBConnectivityShowProducts.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");



    }
















    private void updateOrder() {
        int productsNum = 0;
        float totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            productsNum += orderDetail.getQuantity();
            totalPrice += orderDetail.getSubTotal();
        }

        if (productsNum == 0)
            Commom.hideComponentInConstraintLayout(clViewOrder);
        else {
            order.setTotal(totalPrice);
            Commom.showComponentInConstraintLayout(clViewOrder, (int) getResources().getDimension(R.dimen.bottom_banner_height));
            txtProductsNum.setText(Integer.toString(productsNum));
            txtTotalPrice.setText(Commom.getCurrencyFormatted(order.getTotal()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                OrderDetail orderDetail = (OrderDetail) data.getSerializableExtra("orderDetail");
                orderDetails.add(orderDetail);
                updateOrder();
            }
        } else if (requestCode == CHECKOUT_ACTIVITY_REQUEST) {
            orderDetails = (ArrayList<OrderDetail>) data.getSerializableExtra("orderDetails");
            updateOrder();
        }
    }

    @Override
    public void onBackPressed() {
        if(orderDetails.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("You will lose your current order.\nAre your sure?");

            // add the buttons
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ShowProducts.this.finish();
                }
            });
            builder.setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        } else
            finish();
    }
}
