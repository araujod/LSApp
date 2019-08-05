package ca.douglas.lsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.DB.Restaurant;
import ca.douglas.lsapp.Shared.Commom;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private int resourceId;
    private HashMap<Integer, Restaurant> restaurantHashMap;

    public OrderAdapter(Context context) {
        super(context, R.layout.adapter_order_layout);
        this.context = context;
        resourceId = R.layout.adapter_order_layout;
    }

    public void setOrders(List<Order> orders, HashMap<Integer, Restaurant> restaurantHashMap) {
        this.restaurantHashMap = restaurantHashMap;
        clear();
        addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resourceId, parent, false);

        TextView txtRestaurant = convertView.findViewById(R.id.txtRestaurant);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);

        txtRestaurant.setText(restaurantHashMap.get(order.getStoreID()).getName());
        txtDate.setText(order.getDateTime());

        txtType.setText("Delivery");
        txtTotalPrice.setText(Commom.getCurrencyFormatted(order.getTotal() + Commom.DELIVERY_FEE));

        txtQuantity.setText(Integer.toString(order.getTotal_Items()));

        return convertView;
    }

}
