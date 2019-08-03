package ca.douglas.lsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ca.douglas.lsapp.DB.Restaurant;

class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context mContext;
    private int mResource;
    DecimalFormat decimalFormat;



    public RestaurantAdapter(Context context) {
        super(context, R.layout.adapter_restaurant_layout);
        mContext = context;
        mResource = R.layout.adapter_restaurant_layout;
        decimalFormat = new DecimalFormat("#,###.## km");
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        addAll(restaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgImage);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtType = convertView.findViewById(R.id.txtDescription);
        TextView txtDistance = convertView.findViewById(R.id.txtDistance);

        imageView.setImageResource(mContext.getResources().getIdentifier(restaurant.getLogoUrl(),"drawable", mContext.getPackageName()));
        txtName.setText(restaurant.getName());
        txtType.setText(restaurant.getPhone());
        txtDistance.setText(decimalFormat.format(restaurant.getDistanceFromUser()));

        return convertView;
    }
}