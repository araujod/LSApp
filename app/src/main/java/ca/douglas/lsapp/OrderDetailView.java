package ca.douglas.lsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.Shared.Commom;

public class OrderDetailView extends AppCompatActivity {

    private Order order;
    private ArrayList<OrderDetail> orderDetails;
    private Restaurant restaurant;
    private OrderDetailAdapter orderDetailAdapter;
    private boolean isOrderHistory;
    private TextView txtSubtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_view);
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
        if (isOrderHistory)
            setTitle(getString(R.string.yourPreviousOrder));
        else
            setTitle(getString(R.string.yourOrder));

        TextView txtRestaurant = findViewById(R.id.txtRestaurant);
        ConstraintLayout clCheckout = findViewById(R.id.clCheckout);
        ListView listView = findViewById(R.id.lvOrder);
        //Footer
        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.order_view_list_footer_layout, null, false);
        listView.addFooterView(footerView, null, false);
        txtSubtotal = footerView.findViewById(R.id.txtSubtotal);
        orderDetailAdapter = new OrderDetailAdapter(this, isOrderHistory);

        //Adapter
        txtRestaurant.setText(restaurant.getName());
        listView.setAdapter(orderDetailAdapter);
        setTotalPrice();
        orderDetailAdapter.setOrderDetails(orderDetails);

        //TODO: Order History
       /* if (isOrderHistory) {
            OrderDetailRepository orderDetailRepository = new OrderDetailRepository(getApplicationContext());
            orderDetailRepository.findOrderDetailsByOrder(order.getId()).observe(this, new Observer<List<OrderDetail>>() {
                @Override
                public void onChanged(@Nullable List<OrderDetail> orderDetails) {
                    OrderDetailsView.this.orderDetails = (ArrayList<OrderDetail>) orderDetails;
                    setTotalPrice();
                    orderDetailAdapter.setOrderDetails(orderDetails);
                }
            });
            clCheckout.setClickable(false);
            Helper.hideComponentInConstraintLayout(clCheckout);
        } else {
            setTotalPrice();
            orderDetailAdapter.setOrderDetails(orderDetails);
        }*/

        clCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder deliveryPickup = new AlertDialog.Builder(OrderDetailView.this);
                final String titleText;
                final float totalPrice;
                final TextView txtTitleDialog = new TextView(OrderDetailView.this);
                txtTitleDialog.setPadding((int) getResources().getDimension(R.dimen.dialog_title_margin),
                        (int) getResources().getDimension(R.dimen.dialog_title_margin),
                        0,
                        (int) getResources().getDimension(R.dimen.dialog_title_margin_bottom)
                );
                txtTitleDialog.setTextSize(getResources().getDimensionPixelSize(R.dimen.dialog_title_text_size));
                txtTitleDialog.setTextColor(Color.BLACK);

                totalPrice = order.getTotal();

                titleText = getString(R.string.priceDialog, Commom.getCurrencyFormatted(totalPrice));


                txtTitleDialog.setText(titleText);
                deliveryPickup.setCustomTitle(txtTitleDialog);

                deliveryPickup.setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(OrderDetailView.this, Checkout.class);
                        i.putExtra("order", order);
                        i.putExtra("orderDetails", orderDetails);
                        i.putExtra("restaurant", restaurant);
                        startActivity(i);
                    }
                });
                deliveryPickup.setNegativeButton(getString(R.string.cancel), null);
                deliveryPickup.show();
            }
        });
    }

    public void deleteOrderDetail(int index) {
        orderDetails.remove(index);
        if (orderDetails.isEmpty())
            onBackPressed();
        else {
            setTotalPrice();
            orderDetailAdapter.setOrderDetails(orderDetails);
        }
    }

    public void setTotalPrice() {
        float totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails)
            totalPrice += orderDetail.getSubTotal();
        order.setTotal(totalPrice);
        txtSubtotal.setText(Commom.getCurrencyFormatted(totalPrice));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("orderDetails", orderDetails);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}