package ca.douglas.lsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.douglas.lsapp.DB.DBUtil;
import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.Shared.Commom;
@SuppressWarnings("unchecked")
public class ShowProducts extends AppCompatActivity {

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

        ArrayList<Product> productsAvailable = DBUtil.getProductList();
        Commom.productListFromSelectedStore =productsAvailable;

        ListView listView = findViewById(R.id.listViewProducts);
        productAdapter = new ProductAdapter(this);
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
