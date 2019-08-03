package ca.douglas.lsapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.Shared.Commom;

public class ProductDetailFragment extends Fragment {

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.activity_product_detail_fragment, null);
        String recvData = getArguments().getString("data");
        int pos = getArguments().getInt("position");
        String Categ = Commom.products.get(pos).getCategory() + " " + Commom.storeProducts.get(pos).getAvailable();


        TextView textV = (TextView) v.findViewById(R.id.txtSomething); // this is referring to the item inside fragment_detail
        String s = "Some data that you want to display";
        textV.setText("Welcome " + recvData + "\n" + Categ);
        return v;
    }
}
